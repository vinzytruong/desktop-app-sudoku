package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

    /*Ket noi co so du lieu MySQL*/
    public static Connection connectMySQL() {
        String hostName = "localhost";
        String database = "sudoku";
        String userName = "root";
        String password = "tpv2001";
        String connectionURL = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connectionURL = "jdbc:mysql://" + hostName + ":3306/" + database;

            try {
                conn = DriverManager.getConnection(connectionURL, userName, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnect(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*Ket noi co so du lieu SQL server*/
    public static Connection connectSQLServer() {
        String hostName = "localhost";
        String instance = "MSSQLSERVER";
        String database = "sudoku";
        String userName = "root";
        String password = "3g4@ZVPaWd";
        String connectionURL = null;
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + instance + ";databaseName=" + database;
            try {
                conn = DriverManager.getConnection(connectionURL, userName, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
