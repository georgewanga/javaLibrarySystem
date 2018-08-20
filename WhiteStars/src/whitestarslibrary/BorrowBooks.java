package whitestarslibrary;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BorrowBooks extends JInternalFrame {

    private final JPanel northPanel = new JPanel();
    //for creating the label
    private final JLabel tabtitle = new JLabel("BOOK INFORMATION");

    //for creating the Center Panel
    private final JPanel centerPanel = new JPanel();
    //for creating an Internal Panel in the center panel
    private final JPanel informationPanel = new JPanel();
    //for creating an array of JLabel
    private final JLabel[] informationLabel = new JLabel[3];
    //for creating an array of String
    private final String[] informationString = {"Write the Book Serial Number:", "Write your password:",
        "The Current Date:"};
    //for creating an array of JTextField
    private final JTextField[] informationTextField = new JTextField[4];
    //for creating the date in the String
    private final String date = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new java.util.Date());
    //for creating an array of string to store the data
    private String[] data;

    //for creating an Internal Panel in the center panel
    private final JPanel borrowButtonPanel = new JPanel();
    //for creating the button
    private final JButton borrowButton = new JButton("Borrow");

    //for creating South Panel
    private final JPanel southPanel = new JPanel();
    //for creating the button
    private final JButton cancelButton = new JButton("Cancel");

    //for creating an object
    private Books book;
    private Members member;
    private Borrow borrow;

    //for checking the information from the text field
    public boolean isCorrect() {
        data = new String[4];
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
            if (i != 2) {
                informationTextField[i].setText(null);
            }
        }
    }

    //constructor of borrowBooks
    public BorrowBooks() {
        //for setting the title for the internal frame
        super("Borrow Books", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        //for setting the layout
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //for setting the font
        tabtitle.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the label to the panel
        northPanel.add(tabtitle);
        //for adding the panel to the container
        cp.add("North", northPanel);

        //for setting the layout
        centerPanel.setLayout(new BorderLayout());
        //for setting the layout for the internal panel
        informationPanel.setLayout(new GridLayout(3, 1, 1, 25));

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
            if (i == 2) {
                informationPanel.add(informationTextField[i] = new JTextField(date));
                informationTextField[i].setFont(new Font("Cambria Math", Font.PLAIN, 14));
                informationTextField[i].setEnabled(false);
            } else {
                informationPanel.add(informationTextField[i] = new JTextField());
                informationTextField[i].setFont(new Font("Cambria Math", Font.PLAIN, 14));
            }
        }
        centerPanel.add("Center", informationPanel);

        //for setting the layout
        borrowButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //for setting the font to the button
        borrowButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the button to the panel
        borrowButtonPanel.add(borrowButton);
        //for adding the panel to the center panel
        centerPanel.add("South", borrowButtonPanel);
        //for setting the border to the panel
        centerPanel.setBorder(BorderFactory.createTitledBorder("Borrow a book:"));
        //for adding the panel to the container
        cp.add("Center", centerPanel);

        //for adding the layout
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //for setting the font to the button
        cancelButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the button to the panel
        southPanel.add(cancelButton);
        //for setting the border to the panel
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        //for adding the panel to the container
        cp.add("South", southPanel);

        /**
         * *********************************************************************
         * for adding the action listener to the button,first the text will be *
         * taken from the JTextField[] and make the connection for database, *
         * after that update the table in the database with the new value *
         * *********************************************************************
         */
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //for checking if there is a missing information
                if (isCorrect()) {
                    Thread runner = new Thread() {
                        @Override
                        public void run() {
                            book = new Books();
                            member = new Members();
                            borrow = new Borrow();
                            book.connection("SELECT * FROM wspslibrary.availablebooks WHERE SerialNo = '" + data[0] + "'");
                            member.connection("SELECT * FROM wspslibrary.users WHERE login = 'active' and Passwords = '" + data[1] + "'");
                            String username = null;
                            username = member.getIsData();
                            String serialNo = null;
                            serialNo = book.getIsData();
                            int numberOfAvailbleBooks = book.getNumberOfAvailbleBooks();
                            int numberOfBooks = 1 + member.getNumberOfBooks();
                            String login = member.getlogin();
                            String status = member.getstate();
                            boolean AvailableBooks = book.getAvailble();
                            if (username.equals("available")) {
                                //for checking if there is no same information in the database
                                if (status.equals("valid")) {
                                    if (serialNo.equals("available")) {
                                        if (AvailableBooks == true) {
                                            if (login.equalsIgnoreCase("active")) {
                                                Date date1 = new Date();
                                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                                String borrDat = dateFormat.format(date1);

                                                Calendar cal = Calendar.getInstance();
                                                cal.getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                                                String dd = borrDat.substring(0, 2);
                                                String mm = borrDat.substring(3, 5);
                                                String yyyy = borrDat.substring(6, 10);

                                                int duration = 100;
                                                int days = 0;
                                                int dat = Integer.parseInt(dd) + duration;
                                                int mon = Integer.parseInt(mm);
                                                int yr = Integer.parseInt(yyyy);

                                                if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
                                                    days = 31;
                                                }
                                                if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
                                                    days = 30;
                                                }
                                                if (mon == 2) {
                                                    int remainder = yr % 4;
                                                    if (remainder == 0) {     //29 days
                                                        days = 29;
                                                    } else if (remainder != 0) {       //28 days
                                                        days = 28;
                                                    }
                                                }

                                                if (dat > days) {
                                                    while (dat > days) {
                                                        mon = mon + 1;
                                                        dat = dat - days;
                                                        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
                                                            days = 31;
                                                        }
                                                        if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
                                                            days = 30;
                                                        }
                                                        if (mon == 2) {
                                                            int remainder = yr % 4;
                                                            if (remainder == 0) {     //29 days
                                                                days = 29;
                                                            } else if (remainder != 0) {       //28 days
                                                                days = 28;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (mon > 12) {
                                                    mon = mon - 12;
                                                    yr = yr + 1;
                                                }
                                                String borrowDay = borrDat + "  " + sdf.format(cal.getTime());
                                                String returnDay = dat + "-" + mon + "-" + yr;
                                                String nom = member.getFirstName() + " " + member.getSurame() + " " + member.getMiddleName();

                                                numberOfAvailbleBooks -= 1;
                                                book.update("UPDATE  wspslibrary.availablebooks SET NumberOfAvailbleBooks =" + numberOfAvailbleBooks + ",Available = false WHERE SerialNo = '" + data[0] + "'");
                                                member.update("UPDATE wspslibrary.users SET NumberOfBooks = " + numberOfBooks + " WHERE Passwords =  '" + data[1] + "'");
                                                try {
                                                    borrow.update("INSERT INTO wspslibrary.borrowedBooks "
                                                            + "SELECT SerialNo, Title, Author, Publisher, Subject, Class, ShelfNo, DateAdded, '" + borrowDay + "', '" + nom + "', '" + member.getUsername() + "', '" + returnDay + "' "
                                                            + "FROM wspslibrary.availableBooks WHERE serialNo = '" + data[0] + "'");
                                                } catch (SQLException ex) {
                                                    JOptionPane.showMessageDialog(null, "Error 1 in BorrowBooks");
                                                }
                                                //for setting the array of JTextField to null
                                                JOptionPane.showMessageDialog(null, "The book is Successfully borrowed", "Success", JOptionPane.INFORMATION_MESSAGE);
                                                JOptionPane.showMessageDialog(null, "Return this book by:" + returnDay + " at " + sdf.format(cal.getTime()) + " \nThere will be Cash penalty if not returned in time", "Information:", JOptionPane.INFORMATION_MESSAGE);
                                                clearTextField();

                                            } else {
                                                JOptionPane.showMessageDialog(null, "You must login to borrow book !! \nExit the application and start it again to login", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "The book is not available (Is out of library) !!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            informationTextField[0].setText(null);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "There is no book with that serial number !! \n You can view the list of books for the correct serial", "Warning", JOptionPane.WARNING_MESSAGE);
                                        informationTextField[0].setText(null);
                                    }
                                } else if (status.equals("new")){
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Wrong password !! \n Ensure you are logged into your account", "Warning", JOptionPane.WARNING_MESSAGE);
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
        });
        //for adding the action listener for the button to dispose the frame
        cancelButton.addActionListener(new ActionListener() {
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
