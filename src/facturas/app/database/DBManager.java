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
    
    public static enum TypeDB {
        PRODUCTION, TESTING
    }

    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String JDBC_URL;
    private static Connection connection = null;
    
    public static void createConnection(TypeDB type) {
        if (type == TypeDB.TESTING) {
            JDBC_URL = "jdbc:derby:test-db;create=true";
        } else if (type == TypeDB.PRODUCTION) {
            JDBC_URL = "jdbc:derby:db;create=true";
        }
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
            System.out.println("Connection was established");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    
    private static void checkConnection() {
        if (connection == null) {
            throw new IllegalStateException("the connection was not established");
        }
    }
    
    public static Connection getConnection() {
        checkConnection();
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
        boolean sectorTableCreated = createTable("Sector");
        boolean providerTableCreated = createTable("Provider");
        boolean withholdingTableCreated = createTable("Withholding");
        boolean ticketTableCreated = createTable("Ticket");
        boolean dollarPriceTableCreated = createTable("DollarPrice");
        
        System.out.println("sectorTable " + (sectorTableCreated ? "was created" : "already exists"));
        System.out.println("providerTable " + (providerTableCreated ? "was created" : "already exists"));
        System.out.println("withholdingTable " + (withholdingTableCreated ? "was created" : "already exists"));
        System.out.println("ticketTable " + (ticketTableCreated ? "was created" : "already exists"));
        System.out.println("dollarPriceTable " + (dollarPriceTableCreated ? "was created" : "already exists"));
    }
    
    private static boolean createTable(String tableName) {
        try {
            if (tableAlreadyExists(tableName.toUpperCase()))
                return false;
            
            checkConnection();
            Statement stm = connection.createStatement();
            
            String tableQuery = getTableQuery(tableName);

            stm.executeUpdate(tableQuery);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static String getTableQuery(String tableName) {
        String query = "";
        switch (tableName) {
            case "Provider": query = "CREATE TABLE Provider ("
                                            + "docNo VARCHAR(30) PRIMARY KEY,"
                                            + "name VARCHAR(100),"
                                            + "documentType VARCHAR(20),"
                                            + "direction VARCHAR(50),"
                                            + "sector VARCHAR(50),"
                                            + "alias VARCHAR(100),"
                                            + "CONSTRAINT fk_Sector FOREIGN KEY (sector) REFERENCES Sector(name)"
                                            + "ON DELETE SET NULL"
                                            + ")";
                                            break;

            case "Ticket": query = "CREATE TABLE Ticket ("
                                        + "id INTEGER,"
                                        + "type VARCHAR(50) NOT NULL,"
                                        + "numberTo INTEGER," //
                                        + "authCode VARCHAR(30)," //
                                        + "exchangeType REAL NOT NULL," //
                                        + "exchangeMoney VARCHAR(5) NOT NULL," //
                                        + "netAmountWI REAL," //
                                        + "netAmountWOI REAL," //
                                        + "amountImpEx REAL,"
                                        + "ivaTax REAL," //
                                        + "totalAmount REAL NOT NULL," //
                                        + "issuedByMe BOOLEAN NOT NULL," //
                                        + "PRIMARY KEY (id),"
                                        + "CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES Withholding(id) ON DELETE CASCADE"
                                        + ")";
                                        break;
            
            case "DollarPrice": query = "CREATE TABLE DollarPrice ("
                                                + "date DATE PRIMARY KEY,"
                                                + "buy REAL NOT NULL,"
                                                + "sell REAL NOT NULL"
                                                + ")";
                                                break;
            
            case "Sector": query = "CREATE TABLE Sector ("
                                        + "name VARCHAR(50) PRIMARY KEY"
                                        + ")";
                                        break;
        
            case "Withholding": query = "CREATE TABLE Withholding ("
                                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY UNIQUE,"
                                        + "date DATE NOT NULL,"
                                        + "number VARCHAR(30) NOT NULL,"
                                        + "providerDoc VARCHAR(30) NOT NULL,"
                                        + "iva REAL,"
                                        + "profits REAL,"
                                        + "delivered BOOLEAN DEFAULT false,"
                                        + "sector VARCHAR(50),"
                                        + "CONSTRAINT fk_SectorWithholding FOREIGN KEY (sector) REFERENCES Sector(name)"
                                        + "ON DELETE SET NULL,"
                                        + "PRIMARY KEY (date, number, providerDoc),"
                                        + "CONSTRAINT fk_ProviderWithholding FOREIGN KEY (providerDoc) REFERENCES Provider(docNo)"
                                        + ")";
                                        break;
        
        }
        return query;
    }
    
    public static void deleteDB() {
        boolean deletedTicketTable = dropTable("Ticket");
        boolean deletedWithholdingTable = dropTable("Withholding");
        boolean deletedProviderTable = dropTable("Provider");
        boolean deletedDollarPriceTable = dropTable("DollarPrice");
        boolean deletedSectorTable = dropTable("Sector");
        
        System.out.println("withholding " + (deletedWithholdingTable ? "was deleted" : "didn't exist"));
        System.out.println("ticketTable " + (deletedTicketTable ? "was deleted" : "didn't exist"));
        System.out.println("providerTable " + (deletedProviderTable ? "was deleted" : "didn't exist"));
        System.out.println("dollarPriceTable " + (deletedDollarPriceTable ? "was deleted" : "didn't exist"));
        System.out.println("sectorTable " + (deletedSectorTable ? "was deleted" : "didn't exist"));
    }
    
    private static boolean dropTable(String table) {
        try {
            if (!tableAlreadyExists(table.toUpperCase())) {
                return false;
            }
            
            checkConnection();
            Statement stm = connection.createStatement();

            String tableDollarPrice = "DROP TABLE " + table;
            stm.executeUpdate(tableDollarPrice);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean tableAlreadyExists(String tableName) throws SQLException {
        checkConnection();
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
}
