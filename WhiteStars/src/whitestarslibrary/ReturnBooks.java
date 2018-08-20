package whitestarslibrary;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A public class
 */
public class ReturnBooks extends JInternalFrame implements ActionListener {

    private Globals global;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    /**
     * *************************************************************************
     *** declaration of the private variables used in the program ***
     * *************************************************************************
     */
    //for creating the North Panel
    private final JPanel northPanel = new JPanel();
    //for creating the label
    private final JLabel titleret = new JLabel("BOOK INFORMATION");

    //for creating the Center Panel
    private final JPanel centerPanel = new JPanel();
    //for creating an Internal Panel in the center panel
    private final JPanel informationPanel = new JPanel();
    //for creating an array of JLabel
    private final JLabel[] informationLabel = new JLabel[2];
    //for creating an array of String
    private final String[] informationString = {" Write the Book serial no:", " Write the Username:"};
    //for creating an array of JTextField
    private final JTextField[] informationTextField = new JTextField[2];
    //for creating an array of string to store the data
    private String[] data;
    private final JLabel lblFinePerDay = new JLabel("Fine per Day");
    private final JTextField txtFinePerDay = new JTextField();
    private final JLabel lblTotalFineAmt = new JLabel("Total fine amount");
    private final JTextField txtTotalFineAmt = new JTextField();
    //for creating an Internal Panel in the center panel
    private final JPanel returnButtonPanel = new JPanel();
    //for creating the buton
    private final JButton returnButton = new JButton("Return");

    //for creating the panel
    private final JPanel southPanel = new JPanel();
    //for creating the button
    private final JButton cancelButton = new JButton("Cancel");

    //for creating an object
    private Books book;
    private BooksBorrowed borrowed;
    private Members member;
    private Borrow borrow;

    //for checking the information from the text field
    public boolean isCorrect() {
        data = new String[2];
        for (int i = 0; i < informationLabel.length; i++) {
            if (!informationTextField[i].getText().equals("")) {
                data[i] = informationTextField[i].getText().trim();
            } else {
                return false;
            }
        }
        return true;
    }

    //for setting the array of JTextField to null
    public void clearTextField() {
        for (int i = 0; i < informationTextField.length; i++) {
            informationTextField[i].setText(null);
            txtFinePerDay.setText(null);
            txtTotalFineAmt.setText(null);
        }
    }

    //constructor of returnBooks
    public ReturnBooks() {
        //for setting the title for the internal frame
        super("Return books", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Import16.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        //for setting the layout
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //for setting the font
        titleret.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the label
        northPanel.add(titleret);
        //for adding the north panel to the container
        cp.add("North", northPanel);

        //for setting the layout
        centerPanel.setLayout(new BorderLayout());
        //for setting the layout for the internal panel
        informationPanel.setLayout(new GridLayout(4, 2, 1, 25));

        /**
         * *********************************************************************
         * for adding the strings to the labels, for setting the font * and
         * adding these labels to the panel.	* finally adding the panel to the
         * container	*
         * *********************************************************************
         */
        for (int i = 0; i < informationLabel.length; i++) {
            informationPanel.add(informationLabel[i] = new JLabel(informationString[i]));
            informationLabel[i].setFont(new Font("Cambria Math", Font.BOLD, 14));
            informationPanel.add(informationTextField[i] = new JTextField());
            informationTextField[i].setFont(new Font("Cambria Math", Font.PLAIN, 14));
        }
        informationPanel.add(lblFinePerDay);
        informationPanel.add(txtFinePerDay);
        informationPanel.add(lblTotalFineAmt);
        informationPanel.add(txtTotalFineAmt);
        txtTotalFineAmt.setEditable(false);
        txtFinePerDay.addKeyListener((KeyListener) new keyListener());
        centerPanel.add("Center", informationPanel);
        //for setting the layout
        returnButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //for adding the button
        returnButtonPanel.add(returnButton);
        //for setting the font to the button
        returnButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the internal panel to the panel
        centerPanel.add("South", returnButtonPanel);
        //for setting the border
        centerPanel.setBorder(BorderFactory.createTitledBorder("Return a book:"));
        //for adding the center panel to the container
        cp.add("Center", centerPanel);

        //for setting the layout
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //for adding the button

        southPanel.add(cancelButton);
        //for setting the font to the button
        cancelButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for setting the border
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        //for adding the south panel to the container
        cp.add("South", southPanel);

        /**
         * *********************************************************************
         * for adding the action listener to the button,first the text will be *
         * taken from the JTextField and make the connection for database, *
         * after that update the table in the database with the new value *
         * *********************************************************************
         */
        returnButton.addActionListener(this);
        //for adding the action listener for the button to dispose the frame
        cancelButton.addActionListener(this);
        //for setting the visible to true
        setVisible(true);
        //show the internal frame
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnButton) {
            //for checking if there is a missing information
            if (isCorrect()) {
                Thread runner = new Thread() {

                    @Override
                    public void run() {
                        book = new Books();
                        borrowed = new BooksBorrowed();
                        member = new Members();
                        borrow = new Borrow();
                        borrowed.connection("SELECT * FROM wspsLibrary.borrowedbooks WHERE Username = '" + data[1] + "' AND SerialNo = '" + data[0] + "'");
                        book.connection("SELECT * FROM wspslibrary.availablebooks WHERE SerialNo = '" + data[0] + "'");
                        member.connection("SELECT * FROM wspslibrary.users WHERE Username = '" + data[1] + "'");
                        String username;
                        username = member.getIsData();
                        String serialNo;
                        serialNo = book.getIsData();
                        String matchdata;
                        matchdata = borrowed.getIsData();
                        int numberOfAvailbleBooks = book.getNumberOfAvailbleBooks();
                        int numberOfBooks = member.getNumberOfBooks();
                        //for checking if there is no same information in the database
                        if (username.equals("available")) {
                            //for checking if there is no same information in the database
                            if (serialNo.equals("available")) {

                                if (numberOfAvailbleBooks == 0 && numberOfBooks > 0) {
                                    if (matchdata.equals("available")) {

                                        numberOfAvailbleBooks += 1;
                                        numberOfBooks -= 1;
                                        book.update("UPDATE  wspslibrary.availablebooks SET NumberOfAvailbleBooks =" + numberOfAvailbleBooks + ",Available = true WHERE SerialNo = '" + data[0] + "'");
                                        member.update("UPDATE wspslibrary.users SET NumberOfBooks = " + numberOfBooks + " WHERE Username =  '" + data[1] + "'");

                                        try {
                                            borrow.update("INSERT INTO wspslibrary.borrowLog SELECT * FROM wspslibrary.borrowedbooks WHERE serialNo = '" + data[0] + "' AND Username = '" + data[1] + "'");
                                            borrow.update("DELETE FROM wspslibrary.borrowedBooks WHERE SerialNo = '" + data[0] + "' AND Username = '" + data[1] + "'");
                                        } catch (SQLException ex) {
                                            JOptionPane.showMessageDialog(null, "problem in modifying data");
                                        }
                                        //for setting the array of JTextField to null
                                        JOptionPane.showMessageDialog(null, "The book is Successfully returned", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        clearTextField();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "borrower and book serial do not match !! ", "Information", JOptionPane.WARNING_MESSAGE);
                                        // informationTextField[0].setText(null);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "The book is not borrowed", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "There is no book with that serial number !! \n You can view the list of books to enter the correct serial", "Information", JOptionPane.WARNING_MESSAGE);
                                informationTextField[0].setText(null);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "The username is does not exist !! \n Create an account to borrow book", "Information", JOptionPane.WARNING_MESSAGE);
                            informationTextField[1].setText(null);
                        }
                    }
                };
                runner.start();
            } //if there is a missing data, then display Message Dialog
            else {
                JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (ae.getSource() == cancelButton) {
            dispose();
        }
    }

    class keyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent k) {
            global = new Globals();
            global.Assign();
            String databaseName = global.getdBTitle();
            String DB_URL = global.getURL();
            String USER = global.getUserName();
            String PASS = global.getPassword();

            java.sql.Date da;
            if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    int fineamt = Integer.parseInt(txtFinePerDay.getText().trim());
                    // Open a connection
                    System.out.println("4. Connecting to " + databaseName + " database...");
                    connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);

                    statement = connection.createStatement();
                    String serialNo = informationTextField[0].getText().trim();
                    String username = informationTextField[1].getText().trim();
                    try {
                        String sql = "SELECT ReturnDate from wspslibrary.borrowedBooks WHERE  Username =  '" + username + "' AND serialNo = '" + serialNo + "'";
                        resultSet = statement.executeQuery(sql);
                        if (resultSet.next()) {
                            da = resultSet.getDate(1);
                            java.util.Date today = new java.util.Date();
                            java.util.Date retdate = new java.util.Date(da.getYear(), da.getMonth(), da.getDate());
                            JOptionPane.showMessageDialog(null, "today=" + today + "\nRet date=" + retdate);

                            System.out.println(today.after(da));

                            if (today.after(da)) {
                                long finedays = today.getTime() - da.getTime();
                                int days = (int) (finedays / (1000 * 60 * 60 * 24));
                                System.out.println(days);
                                txtTotalFineAmt.setText(String.valueOf(fineamt * days));
                            } else {
                                txtTotalFineAmt.setText("0");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Member username entered not found on databse");
                        }

                    } catch (SQLException | HeadlessException ex1) {
                        JOptionPane.showMessageDialog(null, "Error, Cannot retrieve date value from table" + ex1.toString());
                    } finally {
                        //finally block used to close resources
                        try {
                            if (statement != null) {
                                statement.close();
                            }
                        } catch (SQLException e) {
                        }// nothing we can do
                        try {
                            if (connection != null) {
                                connection.close();
                            }
                        } catch (SQLException se) {
                        }//end finally try
                    }//end try

                } catch (NumberFormatException | SQLException | HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, "Error, cannot connect to database" + ex.toString());
                }
            }
        }
    }//inner class closed
}//class closed
