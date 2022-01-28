/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Agustin
 */
public class DBManager {

    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL = "jdbc:derby:db;create=true";
    private static Connection connection = null;
    
    public static void createConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            System.out.println("Connection was established");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("the connection was not established");
        }
        return connection;
    }
    
    public static void closeConnection() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }catch (SQLException e) {
            System.out.println("Connection was closed");
        }
    }

    public static void initializeDB() {
        boolean providerTableCreated = createProviderTable();
        boolean ticketTableCreated = createTicketTable();
        boolean dollarPriceTableCreated = createDollarPriceTable();
        
        System.out.println("providerTable " + (providerTableCreated ? "was created" : "already exists"));
        System.out.println("ticketTable " + (ticketTableCreated ? "was created" : "already exists"));
        System.out.println("dollarPriceTable " + (dollarPriceTableCreated ? "was created" : "already exists"));
    }
    
    private static boolean createProviderTable() {
        try {
            connection = getConnection();
            if (tableAlreadyExists("PROVIDER"))
                return false;
            Statement stm = connection.createStatement();
            
            String tableProvider = "CREATE TABLE Provider ("
                    + "cuit VARCHAR(30) PRIMARY KEY,"
                    + "name VARCHAR(100),"
                    + "documentType VARCHAR(20)"
                    + ")";

            stm.executeUpdate(tableProvider);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean createTicketTable() {
        try {
            connection = getConnection();
            if (tableAlreadyExists("TICKET")) {
                return false;
            }
            Statement stm = connection.createStatement();
            
            String tableTicket = "CREATE TABLE Ticket ("
                    + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                    + "date DATE NOT NULL," //
                    + "type VARCHAR(50) NOT NULL," //
                    + "number INTEGER NOT NULL," //
                    + "numberTo INTEGER," //
                    + "authCode VARCHAR(30) NOT NULL," //
                    + "providerCuit VARCHAR(30) NOT NULL," //
                    + "exchangeType REAL NOT NULL," //
                    + "exchangeMoney VARCHAR(5) NOT NULL," //
                    + "netAmountWI REAL," //
                    + "netAmountWOI REAL," //
                    + "amountImpEx REAL,"
                    + "iva REAL," //
                    + "totalAmount REAL NOT NULL," //
                    + "PRIMARY KEY (date, number, providerCuit),"
                    + "CONSTRAINT fk_Provider FOREIGN KEY (providerCuit) REFERENCES Provider(cuit)"
                    + ")";    


            stm.executeUpdate(tableTicket);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean createDollarPriceTable() {
        try {
            connection = getConnection();
            if (tableAlreadyExists("DOLLARPRICE")) {
                return false;
            }
            Statement stm = connection.createStatement();
            
            String tableDollarPrice = "CREATE TABLE DollarPrice ("
                    + "date DATE PRIMARY KEY,"  //maybe ticket date should be fk to this, but dollar price may not exists for some days
                    + "buy REAL NOT NULL,"
                    + "sell REAL NOT NULL"
                    + ")";

            stm.executeUpdate(tableDollarPrice);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    public static void deleteDB() {
        boolean deletedTicketTable = dropTicketTable();
        boolean deletedProviderTable = dropProviderTable();
        boolean deletedDollarPriceTable = dropDollarPriceTable();
        
        System.out.println("providerTable " + (deletedProviderTable ? "was deleted" : "didn't exist"));
        System.out.println("ticketTable " + (deletedTicketTable ? "was deleted" : "didn't exist"));
        System.out.println("dollarPriceTable " + (deletedDollarPriceTable ? "was deleted" : "didn't exist"));
    }
    
    private static boolean dropTicketTable() {
        try {
            connection = getConnection();
            if (!tableAlreadyExists("TICKET")) {
                return false;
            }
            Statement stm = connection.createStatement();

            String tableTicket = "DROP TABLE Ticket";
            stm.executeUpdate(tableTicket);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean dropProviderTable() {
        try {
            connection = getConnection();
            if (!tableAlreadyExists("PROVIDER")) {
                return false;
            }
            Statement stm = connection.createStatement();
            
            String tableProvider = "DROP TABLE Provider";
            stm.executeUpdate(tableProvider);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean dropDollarPriceTable() {
        try {
            connection = getConnection();
            if (!tableAlreadyExists("DOLLARPRICE")) {
                return false;
            }
            Statement stm = connection.createStatement();

            String tableDollarPrice = "DROP TABLE DollarPrice";
            stm.executeUpdate(tableDollarPrice);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean tableAlreadyExists(String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
}
