package whitestarslibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Books {

    private Globals global;
    // Initializing varriables
    Connection connection = null;
    Statement statement = null;
    private ResultSet resultSet = null;

    private String SerialNo, title, author, publisher, Subject, Clas, ShelfNo,
            DateAdded, state, IsData = "notAvailable";
    private boolean availbility;
    private int ID, NumberOfAvailbleBooks;

    public Books() {
    }

    public int getID() {
        return ID;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSubject() {
        return Subject;
    }

    public String getClas() {
        return Clas;
    }

    public String getShelfNo() {
        return ShelfNo;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public boolean getAvailble() {
        return availbility;
    }

    public int getNumberOfAvailbleBooks() {
        return NumberOfAvailbleBooks;
    }

    public String getstate() {
        return state;
    }

    public String getIsData() {
        return IsData;
    }

    public void connection(String Query) {
        global = new Globals();
        global.Assign();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        try {
            // Open a connection
            System.out.println("1. Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Query);

            while (resultSet.next()) {
                ID = resultSet.getInt(1);
                SerialNo = resultSet.getString(2);
                title = resultSet.getString(3);
                author = resultSet.getString(4);
                publisher = resultSet.getString(5);
                Subject = resultSet.getString(6);
                Clas = resultSet.getString(7);
                ShelfNo = resultSet.getString(8);
                DateAdded = resultSet.getString(9);
                availbility = resultSet.getBoolean(10);
                NumberOfAvailbleBooks = resultSet.getInt(11);
                state = resultSet.getString(12);
                IsData = "available";
            }
        } catch (SQLException e) {
            // JOptionPane.showMessageDialog(null, "error at books class", "Error", JOptionPane.ERROR_MESSAGE);
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
    }

    public void update(String Query) {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        try {
// Open a connection
            System.out.println("9. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);

            statement = connection.createStatement();
            statement.executeUpdate(Query);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
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
    }
}
