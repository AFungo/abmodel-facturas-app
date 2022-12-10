package backup;

import builder.ModelBuilder;
import database.*;
import formatters.ModelToCSV;
import utils.csv.CSVUtils;

import java.io.*;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Reads and writes backups based on the data on db
 */
public class BackUpBuilder {

    //backup file headers
    private final static String[] backupTicketHeader = {"id","date","number","providerDoc","iva","profits","delivered","sector","type","numberTo","authCode","exchangeType","exchangeMoney","netAmountWI","netAmountWOI","amountImpEx","ivaTax","totalAmount","issuedByMe"};
    private final static String[] backupWithholdingHeader = {"id","date","number","providerDoc","iva","profits","sector","delivered"};
    private final static String[] backupProviderHeader = {"id","docNo","name","direction","sector","alias","documentType"};
    private final static String[] backupSectorHeader = {"id","name"};
    private final static String[] backupDollarPriceHeader = {"id","Fecha cotizacion","Compra","Venta"};

    /**
     * Takes a folder and a name for it and creates a backup of the current database in it
     * only the db tables with data will be backed up
     * @param folder the path to the folder
     * @param filename the name with which the folder will be created
     *                 this method adds "backup-" to the beginning of filename
     */
    public static void saveBackup(File folder, String filename) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        }

        if (filename == null || filename.equals("")) {  //if no filename was given we set a default name
            LocalTime currentTime = LocalTime.now();
            filename = "\\backup--" + currentTime + "--" + currentTime.getHour() + "-" + currentTime.getMinute() + "\\";
        } else {    //else add backup to the name
            filename = "backup-" + filename;
        }
        //create backup folder
        File backupFolder = new File(folder, filename);
        if (!backupFolder.mkdir()) {
            throw new IllegalStateException("The backup could not be created in path " + backupFolder.getAbsolutePath());
        }

        //tickets backup
        backupData(backupFolder, TicketDAO.getInstance(), ModelToCSV::toCSV, backupTicketHeader, "tickets.csv");
        //withholdings backup
        backupData(backupFolder, WithholdingDAO.getInstance(), ModelToCSV::toCSV, backupWithholdingHeader, "withholdings.csv");
        //providers backup
        backupData(backupFolder, ProviderDAO.getInstance(), ModelToCSV::toCSV, backupProviderHeader, "providers.csv");
        //sectors backup
        backupData(backupFolder, SectorDAO.getInstance(), ModelToCSV::toCSV, backupSectorHeader, "sectors.csv");
        //dollar prices backup
        backupData(backupFolder, DollarPriceDAO.getInstance(), ModelToCSV::toCSV, backupDollarPriceHeader, "prices.csv");
    }

    /**
     * Takes a folder where the backup files are, reads and loads them in the database
     * In case any file has an invalid header or throws an exception while reading the load is cancelled
     * and no data is loaded in db
     * @param folder the path to the folder containing the backups
     */
    public static void loadBackup(File folder) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        } else if (!folder.getName().contains("backup-")) {
            throw new IllegalArgumentException("Folder " + folder.getPath() + " is not a valid backup folder");
        }

        //read backup files, if any is invalid an exception is thrown breaking the method before loading anything
        List<String[]> dollarPriceData = getBackupData(folder, "prices.csv", backupDollarPriceHeader);
        List<String[]> sectorData = getBackupData(folder, "sectors.csv", backupSectorHeader);
        List<String[]> providerData = getBackupData(folder, "providers.csv", backupProviderHeader);
        List<String[]> ticketData = getBackupData(folder, "tickets.csv", backupTicketHeader);
        List<String[]> withholdingData = getBackupData(folder, "withholdings.csv", backupWithholdingHeader);

        //load backups in db in the following order to avoid foreign key conflicts:
        //dollarPrice -> sector -> provider -> ticket -> withholding
        loadBackupData(dollarPriceData, DollarPriceDAO.getInstance(), ModelBuilder::buildDollarPrice);
        loadBackupData(sectorData, SectorDAO.getInstance(), ModelBuilder::buildSector);
        loadBackupData(providerData, ProviderDAO.getInstance(), ModelBuilder::buildProvider);
        loadBackupData(ticketData, TicketDAO.getInstance(), ModelBuilder::buildTicket);
        loadBackupData(withholdingData, WithholdingDAO.getInstance(), ModelBuilder::buildWithholding);
    }

    /**
     * Given a folder, filename and header, builds the path to the file, reads and returns the data as
     * a list of String[]
     * in case the file doesn't exist returns an empty list
     * in case the header is invalid an exception is thrown
     * @param parentFolder the folder where the backups are
     * @param filename the name of the backup file
     * @param header the header of the backup file
     * @return a List of String[] containing the data of the backup file
     */
    private static List<String[]> getBackupData(File parentFolder, String filename, String[] header) {
        File fileCsv = new File(parentFolder, filename);
        if (!fileCsv.exists()) { //if no file exists we return an empty list
            return new LinkedList<>();
        }
        //readCSV and return
        return CSVUtils.readCSV(fileCsv, header);
    }

    /**
     * Given a file data, a dao object and a builder creates a model with the data file and tries to save it in
     * the database
     * @param data the data of a backup file
     * @param dao the dao instance that will be used to load the models
     * @param builder a builder function to transform data into a model
     * @param <E> is the model that will be built and saved
     */
    private static <E> void loadBackupData(List<String[]> data, DAO<E> dao, Function<String[],E> builder) {
        for (String[] item : data) {
            E model = builder.apply(item);    //build model
            dao.save(model);
        }
    }

    /**
     * Given a folder for backup, a dao object, a function to turn into csv format, a header and a filename
     * creates a file and loads all the data of a table on it
     * @param backupFolder the folder where the backups are written
     * @param dao the dao instance that will be used to read
     * @param toCSV the function to turn models into csv format
     * @param header the header that will be written at the beginning of the file
     * @param filename the name of the backup file
     * @param <E> the model that will be read and saved
     */
    private static <E> void backupData(File backupFolder, DAO<E> dao, Function<E,String[]> toCSV, String[] header,
                                       String filename) {
        Set<E> items = dao.getAll();
        if (items.isEmpty()) {   //if there is nothing to save we just return
            return;
        }
        //turn all data into a csv format
        List<String[]> data = new LinkedList<>();
        for (E item : items) {
            data.add(toCSV.apply(item));
        }
        //write data
        CSVUtils.writeCSV(backupFolder.getPath() + filename, data, header);
    }

}