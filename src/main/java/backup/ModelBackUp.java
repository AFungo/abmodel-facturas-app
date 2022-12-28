package backup;

import java.io.File;

public interface ModelBackUp {

    /**
     * Takes a folder and creates a backup of the current database table
     * if the table is empty no backup is created
     * @param folder the path to the folder
     */
    public void createBackup(File folder);

    /**
     * Takes a folder where the backup file is, reads and stores it in an attribute
     * In case the file has an invalid header or throws an exception while reading, an exception is thrown
     */
    public void readBackup(File folder);

    /**
     * loads the data stored in an attribute it in the database
     */
    public void loadBackup();


}
