package backup;

import builder.Builder;
import builder.BuilderFactory;
import models.ModelEnum;
import database.DAOFactory;
import formatters.ModelToCSV;
import models.Ticket;
import utils.csv.CSVUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TicketBackUp implements ModelBackUp {

    private final static String[] header = {"id","date","number","providerDoc","iva","profits","delivered","sector","type","numberTo","authCode","exchangeType","exchangeMoney","netAmountWI","netAmountWOI","amountImpEx","ivaTax","totalAmount","issuedByMe"};
    private List<String[]> ticketRawData;

    public void createBackup(File folder) {
        Set<Ticket> tickets = DAOFactory.get(ModelEnum.TICKET).getAll();
        if (tickets.isEmpty()) {
            return;
        }

        List<String[]> data = new LinkedList<>();
        for (Ticket ticket : tickets) {
            data.add(ModelToCSV.toCSV(ticket));
        }

        CSVUtils.writeCSV(folder.getPath() + "tickets.csv", data, header);
    }

    public void readBackup(File folder) {
        ticketRawData = getBackupData(folder);
    }

    public void loadBackup() {
        loadBackupData(ticketRawData);
    }

    private static List<String[]> getBackupData(File parentFolder) {
        File fileCsv = new File(parentFolder, "tickets.csv");
        if (!fileCsv.exists()) {
            return new LinkedList<>();
        }

        return CSVUtils.readCSV(fileCsv, header);
    }

    private static void loadBackupData(List<String[]> data) {
        for (String[] item : data) {
            Builder builder = BuilderFactory.create(ModelEnum.TICKET);
            Ticket ticket = (Ticket) builder.build(item);
            DAOFactory.get(ModelEnum.TICKET).save(ticket);
        }
    }

}
