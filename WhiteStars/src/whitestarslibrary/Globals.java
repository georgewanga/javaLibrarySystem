/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whitestarslibrary;

/**
 *
 * @author Eng Wanga
 */
public class Globals {

    private String dBTitle, UsersTable, DeletedUsersTable, AvailableBooksTable, BorrowedBooksTable, BorrowLogTable, fileExtension, URL, UserName, Password;

    public Globals() {
    }

    public String getdBTitle() {
        return dBTitle;
    }

    public String getfileExtension() {
        return fileExtension;
    }

    public String getUsersTable() {
        return UsersTable;
    }

    public String getDeletedUsersTable() {
        return DeletedUsersTable;
    }

    public String getAvailableBooksTable() {
        return AvailableBooksTable;
    }

    public String getBorrowedBooksTable() {
        return BorrowedBooksTable;
    }

    public String getBorrowLogTable() {
        return BorrowLogTable;
    }

    public String getURL() {
        return URL;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void Assign() {
        dBTitle = "wspslibrary";
        fileExtension = ".mdb";
        UsersTable = "Users";
        DeletedUsersTable = "DeletedUsers";
        AvailableBooksTable = "AvailableBooks";
        BorrowedBooksTable = "BorrowedBooks";
        BorrowLogTable = "BorrowLog";
        URL = "jdbc:ucanaccess://C:\\Users\\George Wanga\\Documents\\NetBeansProjects\\";
        UserName = "";
        Password = "";
    }
}