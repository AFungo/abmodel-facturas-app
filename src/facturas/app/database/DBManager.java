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
            connection = getConnection();
            if (tableAlreadyExists(tableName.toUpperCase()))
                return false;
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
                                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                                        + "date DATE NOT NULL," //
                                        + "type VARCHAR(50) NOT NULL," //
                                        + "number VARCHAR(30) NOT NULL," //
                                        + "numberTo INTEGER," //
                                        + "authCode VARCHAR(30)," //
                                        + "providerDoc VARCHAR(30) NOT NULL," //
                                        + "exchangeType REAL NOT NULL," //
                                        + "exchangeMoney VARCHAR(5) NOT NULL," //
                                        + "netAmountWI REAL," //
                                        + "netAmountWOI REAL," //
                                        + "amountImpEx REAL,"
                                        + "iva REAL," //
                                        + "totalAmount REAL NOT NULL," //
                                        + "issuedByMe BOOLEAN NOT NULL," //
                                        + "sector VARCHAR(50)," //
                                        + "delivered BOOLEAN DEFAULT false," //
                                        + "PRIMARY KEY (date, number, providerDoc),"
                                        + "CONSTRAINT fk_SectorTicket FOREIGN KEY (sector) REFERENCES Sector(name)"
                                        + "ON DELETE SET NULL,"
                                        + "CONSTRAINT fk_Provider FOREIGN KEY (providerDoc) REFERENCES Provider(docNo)"
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
                                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                                        + "date DATE NOT NULL,"
                                        + "type VARCHAR(50) NOT NULL,"
                                        + "number VARCHAR(30) NOT NULL,"
                                        + "providerDoc VARCHAR(30) NOT NULL,"
                                        + "delivered BOOLEAN DEFAULT false,"
                                        + "totalAmount REAL NOT NULL,"
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
            connection = getConnection();
            if (!tableAlreadyExists(table.toUpperCase())) {
                return false;
            }
            Statement stm = connection.createStatement();

            String tableDollarPrice = "DROP TABLE " + table;
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
