package backup;

import database.*;
import models.*;
import utils.FixedData;
import utils.FormatUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BackUpBuilder {

    public static void createBackup(File folder, String filename) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        }

        if (filename == null || filename.equals("")) {
            filename = FixedData.getBackupFolderName("backup");
        }
        //create backup folder (could be several backups in the folder)
        File backupFolder = new File(folder, filename);
        backupFolder.mkdir();

        //tickets backup
        backupData(backupFolder, () -> TicketDAO.get(), t -> FormatUtils.ticketToCsv(t),
                FixedData.getTicketAppFormat(), "tickets");
        //withholdings backup
        backupData(backupFolder, () -> WithholdingDAO.getWithholdingsWithNoTicket(),
                w -> FormatUtils.withholdingToCsv(w), FixedData.getWithholdingAppFormat(), "withholdings");
        //providers backup
        backupData(backupFolder, () -> ProviderDAO.get(), p -> FormatUtils.providerToCsv(p),
                FixedData.getProviderAppFormat(), "providers");
        //sectors backup
        backupData(backupFolder, () -> SectorDAO.get(), s -> s + ";", FixedData.getSectorAppFormat(), "sectors");
        //dollar prices backup
        backupData(backupFolder, () -> DollarPriceDAO.get(), p -> FormatUtils.dollarPriceToCsv(p),
                FixedData.getDollarPriceFileFormat(), "prices");
    }

    public static void loadBackup(File folder) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        } else if (!folder.getName().contains("backup-")) {
            throw new IllegalArgumentException("Folder " + folder.getPath() + " is not a valid backup folder");
        }
        //load dollar prices
        loadBackupData(folder,"prices.csv", "price", e -> DollarPriceDAO.getInstance().save(e),
                s -> new DollarPrice(FormatUtils.dollarPriceCsvBackupToDict(s)));  //function removes the ; at the end of the sector name
        //load sectors
        loadBackupData(folder,"sectors.csv", "sectorBackup", e -> SectorDAO.getInstance().save(e),
                s -> new Sector(Collections.singletonMap("name", "AMONGAS")));  //function removes the ; at the end of the sector name
        //load providers
        loadBackupData(folder,"providers.csv", "providerBackup", e -> ProviderDAO.getInstance().save(e),
                s -> new Provider(FormatUtils.providerCsvBackupToDict(s)));
        //load tickets
        loadBackupData(folder,"tickets.csv", "ticketBackup", e -> TicketDAO.getInstance().save(e),
                s -> new Ticket(FormatUtils.ticketCsvBackupToDict(s)));
        //load withholdings
        loadBackupData(folder,"withholdings.csv", "withholdingBackup", e -> WithholdingDAO.getInstance().save(e),
                s -> new Withholding(FormatUtils.withholdingCsvBackupToDict(s)));
    }

    private <E> void loadBackupData(File parentFolder, String filename, String formatId, Consumer<E> loadDAO,
                                    Function<String,E> formater) {
        File fileCsv = new File(parentFolder, filename);
        if (fileCsv.exists()) {
            List<String> itemData = readCsv(fileCsv, formatId).getFst();
            for (String s : itemData) {
                E e = formater.apply(s);
                try {
                    loadDAO.accept(e);  //we don't care about repeated item exceptions
                } catch (IllegalStateException ex) {//if the exception is not for repeated item throw it
                    if (!ex.getMessage().contains("<23505> duplicate item")) {
                        throw ex;
                    }
                }
            }
        }
    }

    private static <E> void backupData(File backupFolder, Supplier<List<E>> dao, Function<E,String> formater,
                                String format, String itemName) {
        List<E> items = dao.get();
        if (items.isEmpty()) {   //if there is no items we don't save anything
            System.out.println("no " + itemName + " to backup");
            return ;
        }
        File dataBackup = createFile(backupFolder, itemName + ".csv");
        writeToFile(dataBackup, formater, format, items, itemName);
    }

    private static File createFile(File parentFolder, String filename) {
        File file = new File(parentFolder, filename);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new IllegalStateException("failed to create file at : " + file.getAbsolutePath() + "\n" + ex);
        }
        return file;
    }

    private static <E> void writeToFile(File fileToWrite, Function<E,String> formater, String format, List<E> items,
                                 String itemName) {
        Writer writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(fileToWrite), StandardCharsets.UTF_8);
            writer.write(format);    //first line gives format to be identified at loading
            for (E s : items) {
                writer.append("\n" + formater.apply(s));
            }
            writer.close();
            System.out.println(itemName + " backup done succesfully");

        } catch (IOException ex) {
            throw new IllegalStateException("failed to write on file: " + fileToWrite.getAbsolutePath() + "\n" + ex.toString());
        }
    }

}