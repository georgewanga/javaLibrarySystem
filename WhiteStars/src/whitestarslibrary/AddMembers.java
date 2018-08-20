package whitestarslibrary;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddMembers extends JInternalFrame {

    static final int[] DataTitle1 = new int[3];
    private final int[] informationString1 = {0, 0, 0};
    //"NumberOfBooks", "duration", "maxbooks"

    static final String[] DataTitle2 = new String[3];
    private final String[] informationString2 = {"regular", "active", "inactive"};
    //"identity", "state", "login"

    //for creating the North Panel
    private final JPanel northPanel = new JPanel();
    //for creaing the North Label
    private final JLabel northLabel = new JLabel("MEMBER INFORMATION");
    //for creating the Center Panel
    private final JPanel centerPanel = new JPanel();
    //for creating an Internal Panel in the center panel
    private final JPanel informationLabelPanel = new JPanel();
    //for creating an array of JLabel
    private final JLabel[] informationLabel = new JLabel[10];
    //for creating an array of String
    private final String[] informaionString = {"First Name: ", "Middle Name: ", "Surname: ", "Postal Address: ",
        "Phone Number: ", "Alternative Phone Number: ", "E-mail Address: ", "Username: ", "Password: ", "Confirm Pasword: "};
    //for creating an Internal Panel in the center panel
    private final JPanel informationTextFieldPanel = new JPanel();
    //for creating an array of JTextField
    private final JTextField[] informationTextField = new JTextField[8];
    //for creating an array of JPasswordField
    private final JPasswordField[] informationPasswordField = new JPasswordField[2];

    //for creating an Internal Panel in the center panel
    private final JPanel insertInformationButtonPanel = new JPanel();
    //for creating a button
    private final JButton insertInformationButton = new JButton("Insert the Information");

    //for creating the South Panel
    private final JPanel southPanel = new JPanel();
    //for creating a button
    private final JButton OKButton = new JButton("Exit");

    //create objects from another classes for using them in the ActionListener
    private Members member;
    //for creating an array of string to store the data
    private String[] data;

    //for checking the information from the text field
    public boolean isCorrect() {
        data = new String[9];
        for (int i = 0; i < informationLabel.length; i++) {
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
                if (!informationTextField[i].getText().equals("")) {
                    data[i] = informationTextField[i].getText().trim();
                } else {
                    return false;
                }
            }
            if (i == 8 || i == 9) {
                if (informationPasswordField[i - 8].getText().equals("")) {
                    return false;
                }
            }
        }

        for (int j = 0; j < DataTitle1.length; j++) {
            if (j == 0 || j == 1 || j == 2) {
                DataTitle1[j] = informationString1[j];
            }
        }
        for (int k = 0; k < DataTitle2.length; k++) {
            if (k == 0 || k == 1 || k == 2) {
                DataTitle2[k] = informationString2[k];
            }
        }
        return true;
    }

    //for setting the array of JTextField & JPasswordField to null
    public void clearTextField() {
        for (int i = 0; i < informationLabel.length; i++) {

            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
                informationTextField[i].setText(null);
            }
            if (i == 8 || i == 9) {
                informationPasswordField[i - 8].setText(null);
            }
        }
    }

    //constructor of addMembers
    public AddMembers() {
        //for setting the title for the internal frame
        super("Add Members", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        //for setting the layout
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //for setting the font
        northLabel.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the label to the panel
        northPanel.add(northLabel);
        //for adding the panel to the container
        cp.add("North", northPanel);

        //for setting the layout
        centerPanel.setLayout(new BorderLayout());
        //for setting the border to the panel
        centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new member:"));
        //for setting the layout
        informationLabelPanel.setLayout(new GridLayout(10, 1, 1, 25));
        //for setting the layout
        informationTextFieldPanel.setLayout(new GridLayout(10, 1, 1, 15));

        for (int i = 0; i < informationLabel.length; i++) {
            informationLabelPanel.add(informationLabel[i] = new JLabel(informaionString[i]));
            informationLabel[i].setFont(new Font("Cambria Math", Font.BOLD, 14));
        }
        //for adding the panel to the centerPanel
        centerPanel.add("West", informationLabelPanel);

        for (int i = 0; i < informationLabel.length; i++) {
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
                informationTextFieldPanel.add(informationTextField[i] = new JTextField(30));
                informationTextField[i].setFont(new Font("Cambria Math", Font.PLAIN, 14));
            }
            if (i == 8 || i == 9) {
                informationTextFieldPanel.add(informationPasswordField[i - 8] = new JPasswordField(30));
                informationPasswordField[i - 8].setFont(new Font("Cambria Math", Font.PLAIN, 14));
            }
        }
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
                //for checking if there is a missing information
                if (isCorrect()) {
                    //for checking the password
                    if (informationPasswordField[0].getText().trim().equals(informationPasswordField[1].getText().trim())) {
                        data[8] = informationPasswordField[0].getText();
                        Thread runner = new Thread() {
                            @Override
                            public void run() {
                                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                                String DATE_FORMAT = "dd-MM-yyyy";
                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
                                sdf.setTimeZone(TimeZone.getDefault());
                                String addDate = sdf.format(cal.getTime());
                                member = new Members();
                                //for checking if there is no same information in the database
                                member.connection("SELECT * FROM wspslibrary.users, wspslibrary.deletedusers WHERE users.Username =  '" + data[7] + "' OR deletedusers.Username =  '" + data[7] + "'");
                                String Username = member.getUsername();
                                if (data[6].contains("@")) {
                                    if (data[6].contains(".")) {
                                        if (!(data[7]).equalsIgnoreCase(Username)) {
                                            member.update("INSERT INTO wspslibrary.users (FirstName, MiddleName, "
                                                    + "Surname, PostalAddress, PhoneNumber, AltPhoneNumber, EmailAddress, "
                                                    + "Username, Passwords, DateAdded, NumberOfBooks, duration, maxbooks, "
                                                    + "identity, state, login) VALUES ('" + data[0] + "', '" + data[1] + "', '"
                                                    + data[2] + "', '" + data[3] + "', '" + data[4] + "', '" + data[5] + "', '"
                                                    + data[6] + "', '" + data[7] + "', '" + data[8] + "', '" + addDate + "', "
                                                    + DataTitle1[0] + ", " + DataTitle1[1] + ", " + DataTitle1[2] + ", '"
                                                    + DataTitle2[0] + "', '" + DataTitle2[1] + "', '" + DataTitle2[2] + "')");

                                            JOptionPane.showMessageDialog(null, data[0] + " " + data[2] + " " + data[1] + " added sucessfully");
                                            clearTextField();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Sorry " + data[0] + " " + data[2] + " " + data[1] 
                                                    + " the usermame: " + data[7] + " is not available, try a different username", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Please provide a valid E-mail Address", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Please provide a valid E-mail Address", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        };
                        runner.start();
                        //if the password is wrong
                    } else {
                        JOptionPane.showMessageDialog(null, "passowrds do not match !!", "Error", JOptionPane.ERROR_MESSAGE);
                        informationPasswordField[0].setText(null);
                        informationPasswordField[1].setText(null);
                    }
//if there is a missing data, then display Message Dialog
                } else {
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
