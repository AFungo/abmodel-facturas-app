package backup;

import builder.Builder;
import builder.DollarPriceBuilder;
import database.DAOFactory;
import formatters.ModelToCSV;
import models.ModelEnum;
import models.DollarPrice;
import utils.csv.CSVUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DollarPriceBackUp implements ModelBackUp {

    private final static String[] header = {"id","Fecha cotizacion","Compra","Venta"};
    private List<String[]> dollarPriceRawData;

    public void createBackup(File folder) {
        Set<DollarPrice> dollarPrices = DAOFactory.get(ModelEnum.DOLLARPRICE).getAll();
        if (dollarPrices.isEmpty()) {
            return;
        }

        List<String[]> data = new LinkedList<>();
        for (DollarPrice dollarPrice : dollarPrices) {
            data.add(ModelToCSV.toCSV(dollarPrice));
        }

        CSVUtils.writeCSV(folder.getPath() + "dollarPrices.csv", data, header);
    }

    public void readBackup(File folder) {
        dollarPriceRawData = getBackupData(folder);
    }

    public void loadBackup() {
        loadBackupData(dollarPriceRawData);
    }

    private static List<String[]> getBackupData(File parentFolder) {
        File fileCsv = new File(parentFolder, "dollarPrices.csv");
        if (!fileCsv.exists()) {
            return new LinkedList<>();
        }

        return CSVUtils.readCSV(fileCsv, header);
    }

    private static void loadBackupData(List<String[]> data) {
        for (String[] item : data) {
            Builder builder = new DollarPriceBuilder();
            DollarPrice dollarPrice = (DollarPrice) builder.build(item);
            DAOFactory.get(ModelEnum.DOLLARPRICE).save(dollarPrice);
        }
    }

}
