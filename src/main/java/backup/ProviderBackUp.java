package backup;

import builder.Builder;
import builder.ProviderBuilder;
import database.DAOFactory;
import formatters.ModelToCSV;
import models.ModelEnum;
import models.Provider;
import utils.csv.CSVUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ProviderBackUp implements ModelBackUp {

    private final static String[] header = {"id","docNo","name","direction","sector","alias","documentType"};
    private List<String[]> providerRawData;

    public void createBackup(File folder) {
        Set<Provider> providers = DAOFactory.get(ModelEnum.PROVIDER).getAll();
        if (providers.isEmpty()) {
            return;
        }

        List<String[]> data = new LinkedList<>();
        for (Provider provider : providers) {
            data.add(ModelToCSV.toCSV(provider));
        }

        CSVUtils.writeCSV(folder.getPath() + "providers.csv", data, header);
    }

    public void readBackup(File folder) {
        providerRawData = getBackupData(folder);
    }

    public void loadBackup() {
        loadBackupData(providerRawData);
    }

    private static List<String[]> getBackupData(File parentFolder) {
        File fileCsv = new File(parentFolder, "providers.csv");
        if (!fileCsv.exists()) {
            return new LinkedList<>();
        }

        return CSVUtils.readCSV(fileCsv, header);
    }

    private static void loadBackupData(List<String[]> data) {
        for (String[] item : data) {
            Builder builder = new ProviderBuilder();
            Provider provider = (Provider) builder.build(item);
            DAOFactory.get(ModelEnum.PROVIDER).save(provider);
        }
    }

}
