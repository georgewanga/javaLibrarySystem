package whitestarslibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public final class ResultSetTableModel extends AbstractTableModel {

    private Globals global;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    // keep track of database connection status
    private boolean connectedToDatabase = false;

    // initialize resultSet and obtain its meta data object;
    // determine number of rows
    public ResultSetTableModel(String query) throws SQLException, ClassNotFoundException {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();
        
        // Open a connection
        System.out.println("4. Connecting to " + databaseName + " database...");
        connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
        // create Statement to query database
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true; // update database connection status
        setQuery(query); // set query and execute it
    }

    ResultSetTableModel(String JDBC_DRIVER, String jdbcmysqllocalhost3306wspslibrary, String root, String george, String DEFAULT_QUERY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // get class that represents column type
    @Override
    public Class getColumnClass(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // determine Java class of column
        try {
            String className = metaData.getColumnClassName(column + 1);
            return Class.forName(className); // return Class object that represents className
        } // catch SQLExceptions and ClassNotFoundExceptions
        catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // if problems occur above, assume type Object
        return Object.class;
    }

    // get number of columns in ResultSet
    @Override
    public int getColumnCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // determine number of columns
        try {
            return metaData.getColumnCount();
        } // catch SQLExceptions and print error message
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // if problems occur above, return 0 for number of columns
        return 0;
    }

    // get name of a particular column in ResultSet
    @Override
    public String getColumnName(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // determine column name
        try {
            return metaData.getColumnName(column + 1);
        } // catch SQLExceptions and print error message
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // if problems, return empty string for column name
        return "";
    }

    // return number of rows in ResultSet

    @Override
    public int getRowCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        return numberOfRows;
    }

    // obtain value in particular row and column
    @Override
    public Object getValueAt(int row, int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } // catch SQLExceptions and print error message
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // if problems, return empty string object
        return "";
    }

    // set new database query string

    public void setQuery(String query) throws SQLException, IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }
        // specify query and execute it
        resultSet = statement.executeQuery(query);
        // obtain meta data for ResultSet
        metaData = resultSet.getMetaData();
        // determine number of rows in ResultSet
        resultSet.last(); // move to last row
        numberOfRows = resultSet.getRow(); // get row number
        fireTableStructureChanged(); // notify JTable that model has changed
    }

    // close Statement and Connection
    public void disconnectFromDatabase() {
        // close Statement and Connection
        try {
            statement.close();
            connection.close();
        } // catch SQLExceptions and print error message
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } // update database connection status
        finally {
            connectedToDatabase = false;
        }
    }
} // end class ResultSetTableModel
