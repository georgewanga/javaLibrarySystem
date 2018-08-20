package whitestarslibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class BooksBorrowed {

    private Globals global;
    private ResultSet resultSet = null;

    private String SerialNo, title, author, publisher, Subject, Clas, ShelfNo,
            DateAdded, DateBorrowed, borrowerName, Username, ReturnDate, IsData = "notAvailable";
    private int ID;

    public BooksBorrowed() {
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

    public String getDateBorrowed() {
        return DateBorrowed;
    }

    public String getborrowerName() {
        return borrowerName;
    }

    public String getUsername() {
        return Username;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public String getIsData() {
        return IsData;
    }

    public void connection(String Query) {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);

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
                DateBorrowed = resultSet.getString(10);
                borrowerName = resultSet.getString(11);
                Username = resultSet.getString(12);
                ReturnDate = resultSet.getString(13);
                IsData = "available";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "There was a problem with books borrowed class !!");
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

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {// Open a connection
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
            statement = connection.createStatement();
            statement.executeUpdate(Query);
        } catch (SQLException ex) {
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
