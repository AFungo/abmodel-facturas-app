/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import java.sql.Connection;
import java.sql.DriverManager;
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
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
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
        
        if (providerTableCreated && ticketTableCreated) {
            System.out.println("Tables were created");
        } else if (providerTableCreated && !ticketTableCreated) {
            System.out.println("providerTable was created and ticketTable already exists");
        } else if (!providerTableCreated && ticketTableCreated) {
            System.out.println("ticketTable was created and providerTable already exists");
        } else {
            System.out.println("Tables already exist");
        }
    }
    
    private static boolean createProviderTable() {
        try {
            connection = getConnection();
            Statement stm = connection.createStatement();

            String tableProvider = "CREATE TABLE Provider ("
                    + "cuit VARCHAR(30) PRIMARY KEY,"
                    + "name VARCHAR(100),"
                    + "documentType VARCHAR(20)"
                    + ")";

            stm.executeUpdate(tableProvider);
            return true;
        } catch (SQLException e) {
            if (tableAlreadyExists(e)) {
                return false;
            }
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean createTicketTable() {
        try {
            connection = getConnection();
            Statement stm = connection.createStatement();
            
            String tableTicket = "CREATE TABLE Ticket ("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "date DATE NOT NULL," //
                    + "ticketType VARCHAR(50) NOT NULL," //
                    + "noTicket INTEGER NOT NULL," //
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
                    + "CONSTRAINT fk_Provider FOREIGN KEY (providerCuit) REFERENCES Provider(cuit)"
                    + ")";    


            stm.executeUpdate(tableTicket);
            return true;
        } catch (SQLException e) {
            if (tableAlreadyExists(e)) {
                return false;
            }
            throw new IllegalStateException(e.toString());
        }
    }
    
    public static void deleteDB() {
        boolean deletedTicketTable = dropTicketTable();
        boolean deletedProviderTable = dropProviderTable();
        
        if (deletedTicketTable && deletedProviderTable) {
            System.out.println("Tables were deleted");
        } else if (deletedTicketTable && !deletedProviderTable) {
            System.out.println("ticketTable was deleted and providerTable does not exist");
        } else if (!deletedTicketTable && deletedProviderTable) {
            System.out.println("providerTable was deleted and ticketTable does not exist");
        } else {
            System.out.println("Tables do not exist");
        }
    }
    
    private static boolean dropTicketTable() {
        try {
            connection = getConnection();
            Statement stm = connection.createStatement();

            String tableTicket = "DROP TABLE Ticket";
            stm.executeUpdate(tableTicket);
            return true;
        } catch (SQLException e) {
            if (!tableAlreadyExists(e)) {
                return false;
            }
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean dropProviderTable() {
        try {
            connection = getConnection();
            Statement stm = connection.createStatement();
            
            String tableProvider = "DROP TABLE Provider";
            stm.executeUpdate(tableProvider);
            return true;
        } catch (SQLException e) {
            if (!tableAlreadyExists(e)) {
                return false;
            }
            throw new IllegalStateException(e.toString());
        }
    }
    
    private static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }

}
