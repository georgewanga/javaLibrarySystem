package whitestarslibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Eng Wanga
 */
public class createDatabase {

    private Globals global;
    static final String[] DataTitle1 = new String[10];
    private final String[] informationString1 = {"FirstName", "MiddleName", "Surname", "PostalAddress",
        "PhoneNumber", "AltPhoneNumber", "EmailAddress", "Username", "passwords", "DateAdded"};
    static final String[] DataTitle2 = new String[3];
    private final String[] informationString2 = {"NumberOfBooks", "duration", "maxbooks"};
    static final String[] DataTitle3 = new String[3];
    private final String[] informationString3 = {"identity", "state", "login"};

    static final String[] DataTitle4 = new String[8];
    private final String[] informationString4 = {"SerialNo", "title", "author",
        "publisher", "Subject", "Class", "ShelfNo", "DateAdded"};
    static final String[] DataTitle5 = new String[1];
    private final String[] informationString5 = {"Available"};
    static final String[] DataTitle6 = new String[1];
    private final String[] informationString6 = {"NumberOfAvailbleBooks"};
    static final String[] DataTitle7 = new String[1];
    private final String[] informationString7 = {"state"};
    static final String[] DataTitle8 = new String[4];
    private final String[] informationString8 = {"DateOfBorrow", "BorrowerName", "BorrowerUserName", "DateOfReturn"};
    static final String[] DataTitle9 = new String[4];
    private final String[] informationString9 = {"Identity", "DeleteDate", "DeletedBy", "RasonForDeletion"};

    public void newDatabase() {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String extension = global.getfileExtension();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("1. Connecting to database...");
            connection = DriverManager.getConnection(DB_URL + databaseName + extension, USER, PASS);

            // Execute a query
            System.out.println("2. Connection successful. Creating " + databaseName + " database...");
            statement = connection.createStatement();

            String sql = "CREATE database " + databaseName;
            statement.executeUpdate(sql);
            System.out.println("3a. " + databaseName + " database created successfully...");
            System.out.println("\n**********************************************************************\n");

        } catch (SQLException e) {
            System.out.println("3b. " + databaseName + " database exist!!!");
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
    }//end new database

    public void usersTable() {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String extension = global.getfileExtension();
        String tableName = global.getUsersTable();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName + extension, USER, PASS);

            System.out.println("5. Creating " + tableName + " table within " + databaseName + " database...");
            statement = connection.createStatement();

            // Declaring table sections

            String sql1 = "drop table " + tableName;
            statement.executeUpdate(sql1);
            
            String sql = "CREATE TABLE " + tableName + "( ID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1))";

            // Execute a query
            statement.execute(sql);
            System.out.println("6a. " + tableName + " table created successfully...");

            String PreviousData = "ID";
            System.out.print("\nColumns added :\n");

            for (int i = 0; i < DataTitle1.length; i++) {
                DataTitle1[i] = informationString1[i];
                String addColumns = "alter table " + tableName + " ADD column " + DataTitle1[i] + " TEXT(200)";// after " + PreviousData + ";";
                PreviousData = DataTitle1[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle1[i] + ", ");
            }

            for (int i = 0; i < DataTitle2.length; i++) {
                DataTitle2[i] = informationString2[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle2[i] + " INT(4) NOT NULL DEFAULT 0 after "
                        + PreviousData + ";";
                PreviousData = DataTitle2[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle2[i] + ", ");
            }

            for (int i = 0; i < DataTitle3.length; i++) {
                DataTitle3[i] = informationString3[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle3[i] + " CHAR(20) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle3[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle3[i] + ", ");
            }
            System.out.println("\n7. Columns added to " + tableName + " table successfully...");

            String DefaultData = "INSERT INTO " + databaseName + "." + tableName
                    + " (FirstName, MiddleName, Surname, PostalAddress, PhoneNumber, "
                    + "AltPhoneNumber, EmailAddress, Username, passwords, DateAdded, "
                    + "NumberOfBooks, duration, maxbooks, identity, state, login) "
                    + "VALUES ('George', '-', 'Wanga', 'P. O. Box 32 - 40110 Songhor', "
                    + "'0729646982', '0787445147', 'george.wanga@gmail.com', 'admin', "
                    + "'admin', '02/02/2016', 0, 0, 10, "
                    + "'admin', 'state', 'inactive')";
            statement.executeUpdate(DefaultData);
            System.out.println("8. " + tableName + " table defaults added successfully...");

        } catch (SQLException e) {
            System.out.println("6b. " + tableName + " table exist!!!");
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
        System.out.println("\n**********************************************************************\n");
    }//end users table

    public void DeletedUsersTable() {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String extension = global.getfileExtension();
        String tableName = global.getDeletedUsersTable();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("4. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName + extension, USER, PASS);

            System.out.println("5. Creating " + tableName + " table within " + databaseName + " database...");
            statement = connection.createStatement();

            // Declaring table sections
            String title = "CREATE TABLE " + tableName + "( "
                    + "ID int(10) auto_increment not null, PRIMARY KEY(ID) )";
            String engine = "ENGINE=MyISAM DEFAULT CHARSET=latin1;";

            // Execute a query
            String sql = title + engine;
            statement.executeUpdate(sql);
            System.out.println("6a. " + tableName + " table created successfully...");

            String PreviousData = "ID";
            System.out.print("\nColumns added :\n");

            for (int i = 0; i < DataTitle1.length; i++) {
                DataTitle1[i] = informationString1[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle1[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle1[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle1[i] + ", ");
            }
            
            for (int i = 0; i < DataTitle9.length; i++) {
                DataTitle9[i] = informationString9[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle9[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle9[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle9[i] + ", ");
            }
            System.out.println("\n7. Columns added to " + tableName + " table successfully...");

            String DefaultData = "INSERT INTO " + databaseName + "." + tableName
                    + " (FirstName, MiddleName, Surname, PostalAddress, PhoneNumber, "
                    + "AltPhoneNumber, EmailAddress, Username, passwords, DateAdded, "
                    + "Identity, DeleteDate, DeletedBy, RasonForDeletion) "
                    + "VALUES ('George', '-', 'Wanga', 'P. O. Box 32 - 40110 Songhor', "
                    + "'0729646982', '0787445147', 'george.wanga@gmail.com', 'admins', "
                    + "'admins', '02/02/2016', 'identity', '02/02/2016', 'George', 'administrative')";
            statement.executeUpdate(DefaultData);
            System.out.println("8. " + tableName + " table defaults added successfully...");

        } catch (SQLException e) {
            System.out.println("6b. " + tableName + " table exist!!!");
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
        System.out.println("\n**********************************************************************\n");
    }//end users table

    public void AvailableBooksTable() {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String extension = global.getfileExtension();
        String tableName = global.getAvailableBooksTable();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("9. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName + extension, USER, PASS);
            System.out.println("10.Creating " + tableName + " table within the " + databaseName + " database...");
            statement = connection.createStatement();

            // Declaring table sections
            String title = "CREATE TABLE " + databaseName + "." + tableName + "( "
                    + "ID int(10) auto_increment not null, PRIMARY KEY(ID) )";
            String engine = "ENGINE=MyISAM DEFAULT CHARSET=latin1;";

            // Execute a query
            String sql = title + engine;
            statement.executeUpdate(sql);
            System.out.println("11a. " + tableName + " table created successfully...");

            String PreviousData = "ID";
            System.out.print("\nColumns added :\n");

            for (int i = 0; i < DataTitle4.length; i++) {
                DataTitle4[i] = informationString4[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle4[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle4[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle4[i] + ", ");
            }

            for (int i = 0; i < DataTitle5.length; i++) {
                DataTitle5[i] = informationString5[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle5[i] + " bool not null default 1 after "
                        + PreviousData + ";";
                PreviousData = DataTitle5[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle5[i] + ", ");
            }

            for (int i = 0; i < DataTitle6.length; i++) {
                DataTitle6[i] = informationString6[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle6[i] + " INT(4) NOT NULL DEFAULT 0 after "
                        + PreviousData + ";";
                PreviousData = DataTitle6[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle6[i] + ", ");
            }

            for (int i = 0; i < DataTitle7.length; i++) {
                DataTitle7[i] = informationString7[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle7[i] + " CHAR(20) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle7[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle7[i] + ", ");
            }
            System.out.println("\n12. Columns added to " + tableName + " table successfully...");

            String DefaultData = "INSERT INTO " + databaseName + "." + tableName
                    + " (SerialNo, title, author, publisher, Subject, Class, ShelfNo, "
                    + "DateAdded, Available, NumberOfAvailbleBooks, state) "
                    + "VALUES ('WSPS002', 'Java Programming', 'George Wanga', 'publisher', "
                    + "'Computer', 'Three', 'ws01', '02/01/2016', 1, 3, 'state')";
            statement.executeUpdate(DefaultData);
            System.out.println("13. " + tableName + " table defaults added successfully...");

        } catch (SQLException e) {
            System.out.println("11b. " + tableName + " table exist!!!");
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
        System.out.println("\n**********************************************************************\n");
    }//end available books table

    public void BorrowedBooksTable() {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String extension = global.getfileExtension();
        String tableName = global.getBorrowedBooksTable();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("14.Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName + extension, USER, PASS);

            System.out.println("15.Creating " + tableName + " table within the " + databaseName + " database...");
            statement = connection.createStatement();

            // Declaring table sections
            String title = "CREATE TABLE " + databaseName + "." + tableName + "( "
                    + "ID int(10) auto_increment not null, PRIMARY KEY(ID) )";
            String engine = "ENGINE=MyISAM DEFAULT CHARSET=latin1;";

            // Execute a query
            String sql = title + engine;
            statement.executeUpdate(sql);
            System.out.println("16a. " + tableName + " table created successfully...");

            String PreviousData = "ID";
            System.out.print("\nColumns added :\n");

            for (int i = 0; i < DataTitle4.length; i++) {
                DataTitle4[i] = informationString4[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle4[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle4[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle4[i] + ", ");
            }

            for (int i = 0; i < DataTitle8.length; i++) {
                DataTitle8[i] = informationString8[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle8[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle8[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle8[i] + ", ");
            }
            System.out.println("\n17. Columns added to " + tableName + " table successfully...");

            String DefaultData = "INSERT INTO " + databaseName + "." + tableName
                    + " (SerialNo, title, author, publisher, Subject, Class, ShelfNo, "
                    + "DateAdded, DateOfBorrow, BorrowerName, BorrowerUserName, DateOfReturn) "
                    + "VALUES ('WSPS003', 'Java Programming', 'Chapman', 'KLB', "
                    + "'Computer', 'Three', 'ws01', '01-01-2016', '02-01-2016', 'George Wanga', 'admin', '02-03-2016')";
            statement.executeUpdate(DefaultData);
            System.out.println("18. Defaults added to " + tableName + " table successfully...");

        } catch (SQLException e) {
            System.out.println("16b. " + tableName + " table exist!!!");
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
        System.out.println("\n**********************************************************************\n");
    }//end new table

    public void BorrowLogTable() {
        global = new Globals();
        global.Assign();
        String databaseName = global.getdBTitle();
        String extension = global.getfileExtension();
        String tableName = global.getBorrowLogTable();
        String DB_URL = global.getURL();
        String USER = global.getUserName();
        String PASS = global.getPassword();

        // Initializing varriables
        Connection connection = null;
        Statement statement = null;
        try {
            // Open a connection
            System.out.println("19. Connecting to " + databaseName + " database...");
            connection = DriverManager.getConnection(DB_URL + databaseName + extension, USER, PASS);

            System.out.println("20. Creating " + tableName + " table within the " + databaseName + " database...");
            statement = connection.createStatement();

            // Declaring table sections
            String title = "CREATE TABLE " + databaseName + "." + tableName + "( "
                    + "ID int(10) auto_increment not null, PRIMARY KEY(ID) )";
            String engine = "ENGINE=MyISAM DEFAULT CHARSET=latin1;";

            // Execute a query
            String sql = title + engine;
            statement.executeUpdate(sql);
            System.out.println("21a. " + tableName + " table created successfully...");

            String PreviousData = "ID";
            System.out.print("\nColumns added :\n");

            for (int i = 0; i < DataTitle4.length; i++) {
                DataTitle4[i] = informationString4[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle4[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle4[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle4[i] + ", ");
            }

            for (int i = 0; i < DataTitle8.length; i++) {
                DataTitle8[i] = informationString8[i];
                String addColumns = "alter table " + databaseName + "." + tableName + " "
                        + "ADD " + DataTitle8[i] + " CHAR(100) NOT NULL DEFAULT '' after "
                        + PreviousData + ";";
                PreviousData = DataTitle8[i];
                // Execute a query
                statement.executeUpdate(addColumns);
                System.out.print(DataTitle8[i] + ", ");
            }
            System.out.println("\n22. Columns added to " + tableName + " table successfully...");

            String DefaultData = "INSERT INTO " + databaseName + "." + tableName
                    + " (SerialNo, title, author, publisher, Subject, Class, ShelfNo, "
                    + "DateAdded, DateOfBorrow, BorrowerName, BorrowerUserName, DateOfReturn) "
                    + "VALUES ('WSPS004', 'Java Programming', 'Chapman', 'KLB', "
                    + "'Computer', 'Three', 'ws01', '01-01-2016', '02-01-2016', 'George Wanga', 'admin', '02-03-2016')";
            statement.executeUpdate(DefaultData);
            System.out.println("23. Defaults added to " + tableName + " table successfully...");

        } catch (SQLException e) {
            System.out.println("21b. " + tableName + " table exist!!!");
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
        System.out.println("\n**********************************************************************\n");
    }//end new table

}//end JcreateDatabase Class
