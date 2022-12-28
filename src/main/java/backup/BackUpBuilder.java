package backup;

import java.io.*;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Reads loads and writes backups based on the database information
 */         //FIXME: i think there is a better name for this
public class BackUpBuilder {

    private List<ModelBackUp> modelsToBackup;

    public BackUpBuilder() {
        modelsToBackup = new LinkedList<>();
        modelsToBackup.add(new TicketBackUp());
        modelsToBackup.add(new WithholdingBackUp());
        modelsToBackup.add(new ProviderBackUp());
        modelsToBackup.add(new SectorBackUp());
        modelsToBackup.add(new DollarPriceBackUp());
    }

    /**
     * Takes a folder, a name for it and creates a backup of the current database tables
     * empty tables will not be backed up
     * @param folder the path to the folder
     * @param folderName the name with which the folder will be created
     * this method adds "backup-" to the beginning of folderName
     */
    public void createBackup(File folder, String folderName) {
        File backupFolder = prepareFolder(folder, folderName);

        for (ModelBackUp backup : modelsToBackup) {
            backup.createBackup(backupFolder);
        }
    }

    /**
     * Takes a folder where the backup files are, reads and loads them in the database
     * In case any file has an invalid header or throws an exception while reading the load is cancelled
     * and no data is loaded in db
     * @param folder the path to the folder containing the backups
     */
    public void loadBackup(File folder) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        } else if (!folder.getName().contains("backup-")) {
            throw new IllegalArgumentException("Folder " + folder.getPath() + " is not a valid backup folder");
        }

        for (ModelBackUp backup : modelsToBackup) {
            backup.readBackup(folder);
        }
        ListIterator<ModelBackUp> reverseIterator = modelsToBackup.listIterator(modelsToBackup.size());
        while (reverseIterator.hasPrevious()) {
            reverseIterator.previous().loadBackup();
        }
    }

    /**
     * Takes a path, a folder name and checks if it's valid for creating a backup
     * in case is not an exception is thrown, otherwise the folder is created
     * @param folder path to the folder where backups will be created
     * @param folderName name of the folder
     */
    private File prepareFolder(File folder, String folderName) {
        if (folder == null) {
            throw new IllegalArgumentException("Folder is null");
        }

        if (folderName == null || folderName.equals("")) {
            folderName = defaultFolderName();
        } else {
            folderName = customFolderName(folderName);
        }

        File backupFolder = new File(folder, folderName);
        if (!backupFolder.mkdir()) {
            throw new IllegalStateException("The backup could not be created in path " + backupFolder.getAbsolutePath());
        }

        return backupFolder;
    }

    private String defaultFolderName() {
        LocalTime currentTime = LocalTime.now();
        return "\\backup--" + currentTime + "--" + currentTime.getHour() + "-" + currentTime.getMinute() + "\\";
    }

    private String customFolderName(String folderName) {
        return "backup-" + folderName;
    }

}