package backup;

import builder.Builder;
import builder.SectorBuilder;
import database.DAOFactory;
import formatters.ModelToCSV;
import models.ModelEnum;
import models.Sector;
import utils.csv.CSVUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SectorBackUp implements ModelBackUp {

    private final static String[] header = {"id","name"};
    private List<String[]> sectorRawData;

    public void createBackup(File folder) {
        Set<Sector> sectors = DAOFactory.get(ModelEnum.SECTOR).getAll();
        if (sectors.isEmpty()) {
            return;
        }

        List<String[]> data = new LinkedList<>();
        for (Sector sector : sectors) {
            data.add(ModelToCSV.toCSV(sector));
        }

        CSVUtils.writeCSV(folder.getPath() + "sectors.csv", data, header);
    }

    public void readBackup(File folder) {
        sectorRawData = getBackupData(folder);
    }

    public void loadBackup() {
        loadBackupData(sectorRawData);
    }

    private static List<String[]> getBackupData(File parentFolder) {
        File fileCsv = new File(parentFolder, "sectors.csv");
        if (!fileCsv.exists()) {
            return new LinkedList<>();
        }

        return CSVUtils.readCSV(fileCsv, header);
    }

    private static void loadBackupData(List<String[]> data) {
        for (String[] item : data) {
            Builder builder = new SectorBuilder();
            Sector sector = (Sector) builder.build(item);
            DAOFactory.get(ModelEnum.SECTOR).save(sector);
        }
    }

}
