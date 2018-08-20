package whitestarslibrary;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ListSearchBooks extends JInternalFrame {
    //for creating the North Panel
    private JPanel northPanel = new JPanel();
    //for creating the Center Panel
    private JPanel centerPanel = new JPanel();
    //for creating the label
    private JLabel label = new JLabel("THE LIST FOR THE SEARCHED BOOKS");
    //for creating the button
    private final JButton printButton;
    //for creating the table
    private final JTable table;
    //for creating the TableColumn
    private TableColumn column = null;
    //for creating the JScrollPane
    private final JScrollPane scrollPane;

    //for creating an object for the ResultSetTableModel class
    private ResultSetTableModel tableModel;

    //constructor of listSearchBooks
    public ListSearchBooks(String query) {
        //for setting the title for the internal frame
        super("Searched Books", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/List16.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        final String DEFAULT_QUERY = query;
        try {
            tableModel = new ResultSetTableModel(DEFAULT_QUERY);
            tableModel.setQuery(DEFAULT_QUERY);
        } catch (SQLException | ClassNotFoundException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
//for setting the table with the information
        table = new JTable(tableModel);
        //for setting the size for the table
        table.setPreferredScrollableViewportSize(new Dimension(1280, 450));
        //for setting the font
        table.setFont(new Font("Cambria Math", Font.PLAIN, 14));
        //for setting the scrollpane to the table
        scrollPane = new JScrollPane(table);

        //for setting the size for the table columns
        for (int i = 0; i < 10; i++) {
            column = table.getColumnModel().getColumn(i);
             if (i == 0) //SerialNo
            {
                column.setPreferredWidth(70);
            }
            if (i == 1) //Title
            {
                column.setPreferredWidth(150);
            }
            if (i == 2) //Author
            {
                column.setPreferredWidth(120);
            }
            if (i == 3) //Publisher
            {
                column.setPreferredWidth(80);
            }
            if (i == 4) //Subject
            {
                column.setPreferredWidth(80);
            }
            if (i == 5) //class
            {
                column.setPreferredWidth(50);
            }
            if (i == 6) //shelfNo
            {
                column.setPreferredWidth(60);
            }
            if (i == 7) //date added
            {
                column.setPreferredWidth(70);
            }
            if (i == 8) //Availble
            {
                column.setPreferredWidth(40);
            }
            if (i == 9) //no of available books
            {
                column.setPreferredWidth(40);
            }
        }
        //for setting the font to the label
        label.setFont(new Font("Cambria Math", Font.BOLD, 14));
        //for setting the layout to the panel
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //for adding the label to the panel
        northPanel.add(label);
        //for adding the panel to the container
        cp.add("North", northPanel);

        //for setting the layout to the panel
        centerPanel.setLayout(new BorderLayout());
        //for creating an image for the button
        ImageIcon printIcon = new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif"));
        //for adding the button to the panel
        printButton = new JButton("print the books", printIcon);
        //for setting the tip text
        printButton.setToolTipText("Print");
        //for setting the font to the button
        printButton.setFont(new Font("Cambria Math", Font.PLAIN, 14));
        //for adding the button to the panel
        centerPanel.add(printButton, BorderLayout.NORTH);
        //for adding the scrollpane to the panel
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        //for setting the border to the panel
        centerPanel.setBorder(BorderFactory.createTitledBorder("Books:"));
        //for adding the panel to the container
        cp.add("Center", centerPanel);

        //for adding the actionListener to the button
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Thread runner = new Thread() {
                    @Override
                    public void run() {
                        try {
                            PrinterJob prnJob = PrinterJob.getPrinterJob();
                            prnJob.setPrintable(new PrintingBooks(DEFAULT_QUERY));
                            if (!prnJob.printDialog()) {
                                return;
                            }
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            prnJob.print();
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        } catch (PrinterException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }
                };
                runner.start();
            }
        });
        //for setting the visible to true
        setVisible(true);
        //to show the frame
        pack();
    }
}
