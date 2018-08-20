package whitestarslibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Members {

    private Globals global;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private String FirstName, MiddleName, Surname, PostalAddress, PhoneNumber,
            AltPhoneNumber, EmailAddress, Username, DateAdded, passwords, identity,
            state, login, IsData = "notAvailable";
    private int ID, NumberOfBooks, duration, maxbooks, rowCount;

    public Members() {
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getSurame() {
        return Surname;
    }

    public String getPostalAddress() {
        return PostalAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getAltPhoneNumber() {
        return AltPhoneNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getUsername() {
        return Username;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public String getPasswords() {
        return passwords;
    }

    public int getNumberOfBooks() {
        return NumberOfBooks;
    }

    public int getduration() {
        return duration;
    }

    public int getmaxbooks() {
        return maxbooks;
    }

    public String getidentity() {
        return identity;
    }

    public String getstate() {
        return state;
    }

    public String getlogin() {
        return login;
    }

    public int getrowCount() {
        return rowCount;
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
        
        try {
            // Open a connection
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
            // Execute a query
            System.out.println("members2.executing quuery...");
            statement = connection.createStatement();

            resultSet = statement.executeQuery(Query);
            System.out.println("members3a.query executed successfully...");

            while (resultSet.next()) {
                ID = resultSet.getInt(1);
                FirstName = resultSet.getString(2);
                MiddleName = resultSet.getString(3);
                Surname = resultSet.getString(4);
                PostalAddress = resultSet.getString(5);
                PhoneNumber = resultSet.getString(6);
                AltPhoneNumber = resultSet.getString(7);
                EmailAddress = resultSet.getString(8);
                Username = resultSet.getString(9);
                passwords = resultSet.getString(10);
                DateAdded = resultSet.getString(11);
                NumberOfBooks = resultSet.getInt(12);
                duration = resultSet.getInt(13);
                maxbooks = resultSet.getInt(14);
                identity = resultSet.getString(15);
                state = resultSet.getString(16);
                login = resultSet.getString(17);
                IsData = "available";
                rowCount = resultSet.last() ? resultSet.getRow() : 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "members4: problem in reading database");
            System.out.println("members4: problem in reading database");
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
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
            // Execute a query
            System.out.println("members8. Updating database...");
            statement = connection.createStatement();
            statement.executeUpdate(Query);

            System.out.println("members9: Database updated successfully...");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Can't update members class");
            System.out.println("members10: Can't update members class");
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
