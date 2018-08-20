package whitestarslibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Borrow {

    private Globals global;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private String SerialNo, Title, Author, Publisher, Subject, Clas, ShelfNo, 
            DateAdded, DateBorrowed, borrowerSurname, borrowerUsername, returnDate;
    private int ID;

    public Borrow() {
    }

    public int getID() {
        return ID;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String Publisher() {
        return Publisher;
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

    public String getborrowerSurname() {
        return borrowerSurname;
    }

    public String getborrowerUsername() {
        return borrowerUsername;
    }

    public String getreturnDate() {
        return returnDate;
    }

    public void connection() throws SQLException {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();
        
        System.out.println("4. Connecting to " + databaseName + " database...");
        connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from wspslibrary.borrowedbooks");
        while (resultSet.next()) {
            ID = resultSet.getInt(1);
            SerialNo = resultSet.getString(2);
            Title = resultSet.getString(3);
            Author = resultSet.getString(4);
            Publisher = resultSet.getString(5);
            Subject = resultSet.getString(6);
            Clas = resultSet.getString(7);
            ShelfNo = resultSet.getString(8);
            DateAdded = resultSet.getString(9);
            DateBorrowed = resultSet.getString(10);
            borrowerSurname = resultSet.getString(11);
            borrowerUsername = resultSet.getString(12);
            returnDate = resultSet.getString(13);
        }
    }

    public void update(String Query) throws SQLException {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();
        
        System.out.println("4. Connecting to " + databaseName + " database...");
        connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
        statement = connection.createStatement();
        statement.executeUpdate(Query);
    }
}
