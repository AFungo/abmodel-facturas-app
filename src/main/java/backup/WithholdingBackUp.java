package backup;

import builder.Builder;
import builder.WithholdingBuilder;
import models.ModelEnum;
import database.DAOFactory;
import formatters.ModelToCSV;
import models.Withholding;
import utils.csv.CSVUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class WithholdingBackUp implements ModelBackUp {

    private final static String[] header = {"id","date","number","providerDoc","iva","profits","sector","delivered"};
    private List<String[]> whithholdingRawData;

    public void createBackup(File folder) {
        Set<Withholding> withholdings = DAOFactory.get(ModelEnum.WITHHOLDING).getAll();
        if (withholdings.isEmpty()) {
            return;
        }

        List<String[]> data = new LinkedList<>();
        for (Withholding withholding : withholdings) {
            data.add(ModelToCSV.toCSV(withholding));
        }

        CSVUtils.writeCSV(folder.getPath() + "withholdings.csv", data, header);
    }

    public void readBackup(File folder) {
        whithholdingRawData = getBackupData(folder);
    }

    public void loadBackup() {
        loadBackupData(whithholdingRawData);
    }

    private static List<String[]> getBackupData(File parentFolder) {
        File fileCsv = new File(parentFolder, "withholdings.csv");
        if (!fileCsv.exists()) {
            return new LinkedList<>();
        }

        return CSVUtils.readCSV(fileCsv, header);
    }

    private static void loadBackupData(List<String[]> data) {
        for (String[] item : data) {
            Builder builder = new WithholdingBuilder();
            Withholding withholding = (Withholding) builder.build(item);
            DAOFactory.get(ModelEnum.WITHHOLDING).save(withholding);
        }
    }

}
