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

public class DeleteMembers extends JInternalFrame {

    //for creating the North Panel
    private final JPanel northPanel = new JPanel();
    //for creaing the North Label
    private final JLabel northLabel = new JLabel("DELETING MEMBER INFORMATION");
    //for creating the Center Panel
    private final JPanel centerPanel = new JPanel();
    //for creating an Internal Panel in the center panel
    private final JPanel informationLabelPanel = new JPanel();
    //for creating an array of JLabel
    private final JLabel[] informationLabel = new JLabel[3];
    //for creating an array of String
    private final String[] informaionString = {"Username of member to be removed: ", "Reason for Removing user: ", "Administrator Pasword: "};
    //for creating an Internal Panel in the center panel
    private final JPanel informationTextFieldPanel = new JPanel();
    //for creating an array of JTextField
    private final JTextField[] informationTextField = new JTextField[2];
    //for creating an array of JPasswordField
    private final JPasswordField[] informationPasswordField = new JPasswordField[1];

    //for creating an Internal Panel in the center panel
    private final JPanel insertInformationButtonPanel = new JPanel();
    //for creating a button
    private final JButton insertInformationButton = new JButton("Delete User");

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
        data = new String[3];
        for (int i = 0; i < informationLabel.length; i++) {
            if (i == 0 || i == 1) {
                if (!informationTextField[i].getText().equals("")) {
                    data[i] = informationTextField[i].getText().trim();
                } else {
                    return false;
                }
            }
            if (i == 2) {
                if (informationPasswordField[i - 2].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    //for setting the array of JTextField & JPasswordField to null
    public void clearTextField() {
        for (int i = 0; i < informationLabel.length; i++) {

            if (i == 0 || i == 1) {
                informationTextField[i].setText(null);
            }
            if (i == 2) {
                informationPasswordField[i - 2].setText(null);
            }
        }
    }

    //constructor of addMembers
    public DeleteMembers() {
        //for setting the title for the internal frame
        super("Delete Members", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Delete16.gif")));
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
        centerPanel.setBorder(BorderFactory.createTitledBorder("Delete Existing member:"));
        //for setting the layout
        informationLabelPanel.setLayout(new GridLayout(3, 1, 1, 25));
        //for setting the layout
        informationTextFieldPanel.setLayout(new GridLayout(3, 1, 1, 15));

        for (int i = 0; i < informationLabel.length; i++) {
            informationLabelPanel.add(informationLabel[i] = new JLabel(informaionString[i]));
            informationLabel[i].setFont(new Font("Cambria Math", Font.BOLD, 14));
        }
        //for adding the panel to the centerPanel
        centerPanel.add("West", informationLabelPanel);

        for (int i = 0; i < informationLabel.length; i++) {
            if (i == 0 || i == 1) {
                informationTextFieldPanel.add(informationTextField[i] = new JTextField(60));
                informationTextField[i].setFont(new Font("Cambria Math", Font.PLAIN, 14));
            }
            if (i == 2) {
                informationTextFieldPanel.add(informationPasswordField[i - 2] = new JPasswordField(30));
                informationPasswordField[i - 2].setFont(new Font("Cambria Math", Font.PLAIN, 14));
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
                    
                    data[2] = informationPasswordField[0].getText().trim();
                    //for checking the password
                    member = new Members();
                    member.connection("SELECT * FROM wspslibrary.users WHERE login = 'active' and Passwords =  '" + data[2] + "'");
                    String Password = member.getIsData();

                    if (Password.trim().equalsIgnoreCase("Available")) {
                        member.connection("SELECT * FROM wspslibrary.users  WHERE Username = '" + data[0] + "'");
                        String Username = member.getUsername();
                        if (Username.equals(data[0].trim())) {
                            Thread runner = new Thread() {
                                @Override
                                public void run() {
                                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                                    String DATE_FORMAT = "dd-MM-yyyy";
                                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
                                    sdf.setTimeZone(TimeZone.getDefault());
                                    String deleteDate = sdf.format(cal.getTime());

                                    member.connection("SELECT * FROM wspslibrary.users WHERE login = 'active'");
                                    String nom = member.getFirstName() + " " + member.getSurame() + " " + member.getMiddleName();

                                    member.update("INSERT INTO wspslibrary.deletedUsers SELECT FirstName, MiddleName, Surname, PostalAddress, PhoneNumber, AltPhoneNumber, EmailAddress, "
                                            + "Username, Passwords, DateAdded, identity, '" + deleteDate + "', '" + nom + "', '" + data[1] + "' FROM wspslibrary.users WHERE Username = '" + data[0] + "'");

                                    member.update("DELETE FROM wspslibrary.users WHERE Username = '" + data[0] + "'");

                                    JOptionPane.showMessageDialog(null, "Member deleted sucessfully");
                                    clearTextField();
                                }
                            };
                            runner.start();
                            //if the password is wrong
                        } else {
                            JOptionPane.showMessageDialog(null, "The Username does not exist in library!!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Password !!", "Error", JOptionPane.ERROR_MESSAGE);
                        informationPasswordField[0].setText(null);
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
