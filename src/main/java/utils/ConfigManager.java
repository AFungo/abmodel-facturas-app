/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Create and modify a file.cfg with the configuration of the app
 */
public class ConfigManager {
    
    private static boolean isMac = System.getProperty("os.name").contains("Mac OS");
    private static String configFolderPath = System.getenv("APPDATA") + "\\Facturas App\\";
    private static String configPath = isMac ? "config.txt" : System.getenv("APPDATA") + "\\Facturas App\\" + "config.txt";
    
    /**
     * read the current config file of the app
     * @return a map with the configuration
     */
    public static Map<String, Boolean> readConfig() {
        File configsFile = new File(configPath);
        if ((!configsFile.exists()) || configsFile.length() == 0) {
            initializeConfig();
            saveConfig(initialConfig());
        }
        
        List<String> stringItems = new LinkedList<>();
        try {
            stringItems = Files.readAllLines(configsFile.toPath(), Charset.defaultCharset());
            stringItems.remove(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Map<String, Boolean> configs = new HashMap<String, Boolean>();
        for (String line : stringItems) {
            if (line.equals("[selected columns]")) continue;
            
            String[] values = line.split("=");
            configs.put(values[0], Boolean.valueOf(values[1]));
        }
        return configs;
    }

    /**
     * Save the changes in thefile of configurations of the app
     * @param configs is a map with the newest configurations of the app
     */
    public static void saveConfig(Map<String, Boolean> configs) {
        String fileContent = "\n[selected columns]";
        for (Map.Entry<String, Boolean> line : configs.entrySet()) {
            fileContent += "\n" + line.getKey() + "=" + line.getValue();
        }
        
        try {
            FileWriter writer = new FileWriter(configPath);
            writer.write(fileContent);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
     *Create a new file.cfg with default configurations of the app  
     */
    private static void initializeConfig() {
        File configsFolder = new File(configFolderPath);
        if (!configsFolder.exists() && !isMac) {    //creating directory if not exists
            configsFolder.mkdir();
        }
        
        File configsFile = new File(configPath);
        try {
            configsFile.createNewFile();    //creating file
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
    * returns a map with the default configurations of the app
    */
    private static Map<String, Boolean> initialConfig() {
        Map<String, Boolean> configs = new HashMap<String, Boolean>();
        //ticket
        configs.put("id", true);
        configs.put("date", true);
        configs.put("type", true);
        configs.put("noTicket", true);
        configs.put("numberTo", true);
        configs.put("authCode", true);
        configs.put("providerDoc", true);
        configs.put("providerName", true);
        configs.put("changeType", true);
        configs.put("netAmountWI", true);
        configs.put("netAmountWOI", true);
        configs.put("amountImpEx", true);
        configs.put("iva", true);
        configs.put("totalAmount", true);
        configs.put("ticketSector", true);
        configs.put("purchaseNSales", true);
        configs.put("delivered", true);
        //provider
        configs.put("docNo", true);
        configs.put("name", true);
        configs.put("alias", true);
        configs.put("docType", true);
        configs.put("direction", true);
        configs.put("providerSector", true);
        configs.put("alias", true);
 
        return configs;
    }
}