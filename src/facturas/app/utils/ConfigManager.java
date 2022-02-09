/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

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
 *
 * @author nacho
 */
public class ConfigManager {
    
    private static boolean isMac = System.getProperty("os.name").contains("Mac OS");
    private static String configFolderPath = System.getenv("APPDATA") + "\\Facturas App\\";
    private static String configPath = isMac ? "config.txt" : System.getenv("APPDATA") + "\\Facturas App\\" + "config.txt";
    
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
    
    private static Map<String, Boolean> initialConfig() {
        Map<String, Boolean> configs = new HashMap<String, Boolean>();
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
        configs.put("ticketSector", true);
        configs.put("totalAmount", true);
        configs.put("docNo", true);
        configs.put("name", true);
        configs.put("docType", true);
        configs.put("direction", true);
        configs.put("providerSector", true);
        configs.put("alias", true);
        configs.put("purchaseNSales", true);
        configs.put("delivered", true);
        return configs;
    }
}