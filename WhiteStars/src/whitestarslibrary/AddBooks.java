package whitestarslibrary;
//import the packages for using the classes in them into the program

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddBooks extends JInternalFrame {

    private Globals global;
    // Initializing varriables
    Connection connection = null;

    //for creating the North Panel
    private final JPanel northPanel = new JPanel();
    //for creaing the North Label
    private final JLabel northLabel = new JLabel("BOOK INFORMATION");

    //for creating the Center Panel
    private final JPanel centerPanel = new JPanel();
    //for creating an Internal Panel in the center panel
    private final JPanel informationLabelPanel = new JPanel();

    //for creating an array of JLabel
    private final JLabel[] informationLabel = new JLabel[6];
    private final JLabel lblShelfNo = new JLabel(" Shelf No");
    private JTextField txtShelfNo = new JTextField();
    //for creating an array of String
    private final String[] informationString = {
        " The book serial: ", " The book title: ",
        " The name of the Author(s): ", " The name of the Publisher: ", " The book subject: ",
        " Class: "
    };
    //for creating an Internal Panel in the center panel
    private final JPanel informationTextFieldPanel = new JPanel();
    //for creating an array of JTextField
    private final JTextField[] informationTextField = new JTextField[6];

    //for creating an Internal Panel in the center panel
    private final JPanel insertInformationButtonPanel = new JPanel();
    //for creating a button
    private final JButton insertInformationButton = new JButton("Insert the Information");

    //for creating South Panel
    private final JPanel southPanel = new JPanel();
    //for creating a button
    private final JButton OKButton = new JButton("Exit");

    //create objects from another classes for using them in the ActionListener
    private Books book;
    //for creating an array of string to store the data
    private String[] data;
    //for setting availble option to true
    private boolean availble = true;

    //for checking the information from the text field
    public boolean isCorrect() {
        data = new String[6];
        for (int i = 0; i < informationLabel.length; i++) {
            if (!informationTextField[i].getText().equals("")) {
                data[i] = informationTextField[i].getText().trim();
            } else {
                return false;
            }
        }
        return true;
    }

    //for setting the array of JTextField to empty
    public void clearTextField() {
        for (int i = 0; i < informationTextField.length; i++) {
            informationTextField[i].setText(null);
        }
        txtShelfNo.setText(null);
    }

    //constructor of addBooks
    public AddBooks() {
        //for setting the title for the internal frame
        super("Add Books", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        //for setting the layout
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //for setting the font for the North Panel
        northLabel.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the label in the North Panel
        northPanel.add(northLabel);
        //for adding the north panel to the container
        cp.add("North", northPanel);

        //for setting the layout
        centerPanel.setLayout(new BorderLayout());
        //for setting the border to the panel
        centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new book:"));
        //for setting the layout
        informationLabelPanel.setLayout(new GridLayout(7, 1, 1, 40));
        for (int i = 0; i < informationLabel.length; i++) {
            informationLabelPanel.add(informationLabel[i] = new JLabel(informationString[i]));
            informationLabel[i].setFont(new Font("Cambria Math", Font.BOLD, 14));
        }
        centerPanel.add("West", informationLabelPanel);

        //for setting the layout
        informationTextFieldPanel.setLayout(new GridLayout(7, 1, 1, 30));

        for (int i = 0; i < informationTextField.length; i++) {
            informationTextFieldPanel.add(informationTextField[i] = new JTextField(40));
            informationTextField[i].setFont(new Font("Cambria Math", Font.PLAIN, 14));
        }
        lblShelfNo.setFont(new Font("Tahoma", Font.BOLD, 14));
        informationLabelPanel.add(lblShelfNo);
        txtShelfNo.setFont(new Font("Cambria Math", Font.PLAIN, 14));
        informationTextFieldPanel.add(txtShelfNo);
        centerPanel.add("East", informationTextFieldPanel);

        insertInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        insertInformationButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        insertInformationButtonPanel.add(insertInformationButton);
        centerPanel.add("South", insertInformationButtonPanel);
        cp.add("Center", centerPanel);

        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        OKButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        southPanel.add(OKButton);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);

        insertInformationButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //auto retrieve the system date
                //for checking if there is a missing information
                if (isCorrect()) {
                    Thread runner = new Thread() {

                        @Override
                        public void run() {
                            global = new Globals();
                            global.Assign();
                            String DB_URL = global.getURL();
                            String USER = global.getUserName();
                            String PASS = global.getPassword();

                            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                            String DATE_FORMAT = "dd-MM-yyyy";
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
                            sdf.setTimeZone(TimeZone.getDefault());
                            String addDate = sdf.format(cal.getTime());
                            book = new Books();
                            //for checking if there is no double information in the database
                            book.connection("SELECT Title FROM wspslibrary.availablebooks WHERE SerialNo = '" + data[0] + "'");
                            String Serial = book.getSerialNo();
                            if (!(data[0]).equalsIgnoreCase(Serial)) {
                                try {
                                    String sql = "INSERT INTO wspslibrary.availablebooks (SerialNo, Title, Author, "
                                            + "Publisher, Subject, Class, ShelfNo, DateAdded, Available) "
                                            + "VALUES (?,?,?,?,?,?,?,'" + addDate + "',?)";

                                    // Open a connection
                                    System.out.println("1. Connecting to database...");
                                    connection = DriverManager.getConnection(DB_URL, USER, PASS);
                                    PreparedStatement ps = connection.prepareStatement(sql);
                                    ps.setString(1, data[0]);
                                    ps.setString(2, data[1]);
                                    ps.setString(3, data[2]);
                                    ps.setString(4, data[3]);
                                    ps.setString(5, data[4]);
                                    ps.setString(6, data[5]);
                                    ps.setString(7, txtShelfNo.getText().trim());
                                    ps.setBoolean(8, availble);
                                    ps.executeUpdate();

                                    JOptionPane.showMessageDialog(null, "Book added sucessfully");
                                    clearTextField();

                                } catch (SQLException | HeadlessException ex) {
                                    JOptionPane.showMessageDialog(null, "The book serial is already in the library", "Error Message !!", JOptionPane.ERROR_MESSAGE);
                                    informationTextField[0].setText(null);
                                }
                            } else {
                            }
                        }
                    };
                    runner.start();
                } //if there is a missing data, then display Message Dialog
                else {
                    JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        //for adding the action listener for the button to dispose the frame
        OKButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        //for setting the visible to true
        setVisible(true);
        //show the internal frame
        pack();
    }
}
