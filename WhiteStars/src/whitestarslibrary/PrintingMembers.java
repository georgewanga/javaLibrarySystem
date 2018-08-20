package whitestarslibrary;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

public class PrintingMembers extends JInternalFrame implements Printable {
    
    private Globals global;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultset = null;
    //for creating the text area
    private JTextArea textArea = new JTextArea();
    //for creating the vector to use it in the print
    private Vector lines;
    public static final int TAB_SIZE = 5;

    //constructor of JLibrary
    public PrintingMembers(String query) {
        super("Printing Members", false, true, false, true);
        //for getting the graphical user interface components display area
        Container cp = getContentPane();
        //for setting the font
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        //for adding the textarea to the container
        cp.add(textArea);
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();
        
        try {
            // Open a connection
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
            
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);
            textArea.append("=============== Members Information ===============\n\n");
            while (resultset.next()) {
                String name =  resultset.getString("FirstName")+ " " + resultset.getString("Surname") + " " +resultset.getString("MiddleName");
                textArea.append("Name: " + name + "\n"
                        + "Postal Address: " + resultset.getString("PostalAddress") + "\n"
                        + "Phone Number: " + resultset.getString("PhoneNumber") + "\n"
                        + "Email Address: " + resultset.getString("EmailAddress") + "\n\n");
            }
            textArea.append("=============== Members Information ===============");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }finally {
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
            }// nothing we can do
            try {
                if (resultset != null) {
                    resultset.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try
        //for setting the visible to true
        setVisible(true);
        //to show the frame
        pack();
    }

    @Override
    public int print(Graphics pg, PageFormat pageFormat, int pageIndex) throws PrinterException {
        pg.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        int wPage = (int) pageFormat.getImageableWidth();
        int hPage = (int) pageFormat.getImageableHeight();
        pg.setClip(0, 0, wPage, hPage);

        pg.setColor(textArea.getBackground());
        pg.fillRect(0, 0, wPage, hPage);
        pg.setColor(textArea.getForeground());

        Font font = textArea.getFont();
        pg.setFont(font);
        FontMetrics fm = pg.getFontMetrics();
        int hLine = fm.getHeight();

        if (lines == null) {
            lines = getLines(fm, wPage);
        }

        int numLines = lines.size();
        int linesPerPage = Math.max(hPage / hLine, 1);
        int numPages = (int) Math.ceil((double) numLines / (double) linesPerPage);
        if (pageIndex >= numPages) {
            lines = null;
            return NO_SUCH_PAGE;
        }
        int x = 0;
        int y = fm.getAscent();
        int lineIndex = linesPerPage * pageIndex;
        while (lineIndex < lines.size() && y < hPage) {
            String str = (String) lines.get(lineIndex);
            pg.drawString(str, x, y);
            y += hLine;
            lineIndex++;
        }
        return PAGE_EXISTS;
    }

    protected Vector getLines(FontMetrics fm, int wPage) {
        Vector v = new Vector();

        String text = textArea.getText();
        String prevToken = "";
        StringTokenizer st = new StringTokenizer(text, "\n\r", true);
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            if (line.equals("\r")) {
                continue;
            }
            // StringTokenizer will ignore empty lines, so it's a bit tricky to get them...
            if (line.equals("\n") && prevToken.equals("\n")) {
                v.add("");
            }
            prevToken = line;
            if (line.equals("\n")) {
                continue;
            }

            StringTokenizer st2 = new StringTokenizer(line, " \t", true);
            String line2 = "";
            while (st2.hasMoreTokens()) {
                String token = st2.nextToken();
                if (token.equals("\t")) {
                    int numSpaces = TAB_SIZE - line2.length() % TAB_SIZE;
                    token = "";
                    for (int k = 0; k < numSpaces; k++) {
                        token += " ";
                    }
                }
                int lineLength = fm.stringWidth(line2 + token);
                if (lineLength > wPage && line2.length() > 0) {
                    v.add(line2);
                    line2 = token.trim();
                    continue;
                }
                line2 += token;
            }
            v.add(line2);
        }
        return v;
    }
}
