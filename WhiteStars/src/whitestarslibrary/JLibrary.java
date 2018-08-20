package whitestarslibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JLibrary extends JFrame implements ActionListener {

    private final JPanel searchPanel = new JPanel();
    private final JToolBar searchToolBar = new JToolBar();
    private final JLabel searchLabel = new JLabel("Book title: ");
    private final JTextField searchTextField = new JTextField(15);
    private final JButton goButton = new JButton("Go");
    private final JDesktopPane desktop = new JDesktopPane();
    private final Menubar menu;
    private final Toolbar toolbar;
    private final StatusBar statusbar = new StatusBar();
    private ListBooks listBooks;
    private AddBooks addBooks;
    private BorrowBooks borrowBooks;
    private ReturnBooks returnBooks;
    private AddMembers addMembers;
    private ListMembers listMembers;
    private DeleteMembers deleteMembers;
    private SearchBooksAndMembers search;
    private Members member;

    public JLibrary() {
        super("WHITE STARS PREPARATORY SCHOOL LIBRARY SYSTEM");
        //for setting the size
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width - 50, screenSize.height - 50);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(ClassLoader.getSystemResource("images/logo.gif"));
        setIconImage(image);

        menu = new Menubar();
        toolbar = new Toolbar();

        // menu bar Action.........
        setJMenuBar(menu);
        menu.exit.addActionListener(this);
        menu.addBook.addActionListener(this);
        menu.listBook.addActionListener(this);
        menu.addMember.addActionListener(this);
        menu.listMember.addActionListener(this);
        menu.deleteMember.addActionListener(this);
        menu.searchBooksAndMembers.addActionListener(this);
        menu.borrowBook.addActionListener(this);
        menu.returnBook.addActionListener(this);

        //get the graphical user interface components display the desktop
        Container cp = getContentPane();
        desktop.setOpaque(true);
        desktop.setBackground(Color.black);
        cp.add("Center", desktop);
        //for setting the font
        searchLabel.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for setting the font
        searchTextField.setFont(new Font("Cambria Math", Font.PLAIN, 14));
        goButton.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for adding the searchLable to the searchToolBar
        searchToolBar.add(searchLabel);
        //for adding the searchTextField to searchToolBar
        searchToolBar.add(searchTextField);
        //for adding the goButton to searchToolBar
        searchToolBar.add(goButton);
        //for adding listenerAction for the button
        goButton.addActionListener(this);
        //for setting the layout
        searchPanel.setLayout(new BorderLayout());
        //for adding the toolBar to the searchPanel
        searchPanel.add("Center", toolbar);
        //for adding the searchPanel to the Container
        cp.add("North", searchPanel);
        //for adding the statusbar to the Container
        cp.add("South", statusbar);

        for (int i = 0; i < toolbar.imageName24.length; i++) {
            //for adding the action to the button
            toolbar.button[i].addActionListener(this);
        }

        //for adding WindowListener to the program
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //show the program
        show();
    }

    /**
     * this method is invoked when an action occurs.
     *
     * @param ae the action event.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == menu.addBook || ae.getSource() == toolbar.button[0]) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    addBooks = new AddBooks();
                    desktop.add(addBooks);
                    try {
                        addBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.listBook || ae.getSource() == toolbar.button[1]) {
            Thread runner = new Thread() {

                @Override
                public void run() {
                    listBooks = new ListBooks();
                    desktop.add(listBooks);
                    try {
                        listBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.addMember || ae.getSource() == toolbar.button[2]) {
            Thread runner = new Thread() {

                @Override
                public void run() {
                    addMembers = new AddMembers();
                    desktop.add(addMembers);
                    try {
                        addMembers.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.listMember || ae.getSource() == toolbar.button[3]) {
            Thread runner = new Thread() {

                @Override
                public void run() {
                    listMembers = new ListMembers();
                    desktop.add(listMembers);
                    try {
                        listMembers.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.deleteMember || ae.getSource() == toolbar.button[4]) {
            Thread runner = new Thread() {

                @Override
                public void run() {

                    member = new Members();
                    member.connection("SELECT * FROM wspslibrary.users WHERE login = 'active'");
                    String Identity = member.getidentity();
                    if (Identity.equalsIgnoreCase("admin")) {
                        deleteMembers = new DeleteMembers();
                        desktop.add(deleteMembers);
                        try {
                            deleteMembers.setSelected(true);
                        } catch (java.beans.PropertyVetoException e) {
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Sorry! Only Administrators Can Delete Existing User !!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.searchBooksAndMembers || ae.getSource() == toolbar.button[5]) {
            Thread runner = new Thread() {

                @Override
                public void run() {
                    search = new SearchBooksAndMembers();
                    desktop.add(search);
                    try {
                        search.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.borrowBook || ae.getSource() == toolbar.button[6]) {
            Thread runner = new Thread() {

                @Override
                public void run() {
                    borrowBooks = new BorrowBooks();
                    desktop.add(borrowBooks);
                    try {
                        borrowBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.returnBook || ae.getSource() == toolbar.button[7]) {
            Thread runner;
            runner = new Thread() {
                
                @Override
                public void run() {
                    returnBooks = new ReturnBooks();
                    desktop.add(returnBooks);
                    try {
                        returnBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (ae.getSource() == menu.exit || ae.getSource() == toolbar.button[8]) {
            member = new Members();
            member.update("UPDATE wspslibrary.users SET login = 'inactive' where login = 'active'");
            JOptionPane.showMessageDialog(null, "THANK YOU FOR USING OUR LIBRARY @White_Stars_Pre_School", "BYE !!!", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            System.exit(0);
        }
    }
}
