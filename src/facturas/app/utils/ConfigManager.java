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
            System.out.println(line.getKey() + "=" + line.getValue());
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
        configs.put("date", Boolean.TRUE);
        configs.put("type", Boolean.TRUE);
        configs.put("noTicket", Boolean.TRUE);
        configs.put("numberTo", Boolean.TRUE);
        configs.put("authCode", Boolean.TRUE);
        configs.put("providerDoc", Boolean.TRUE);
        configs.put("providerName", Boolean.TRUE);
        configs.put("changeType", Boolean.TRUE);
        configs.put("netAmountWI", Boolean.TRUE);
        configs.put("netAmountWOI", Boolean.TRUE);
        configs.put("amountImpEx", Boolean.TRUE);
        configs.put("iva", Boolean.TRUE);
        configs.put("ticketSector", Boolean.TRUE);
        configs.put("totalAmount", Boolean.TRUE);
        configs.put("docNo", Boolean.TRUE);
        configs.put("name", Boolean.TRUE);
        configs.put("docType", Boolean.TRUE);
        configs.put("direction", Boolean.TRUE);
        configs.put("providerSector", Boolean.TRUE);
        configs.put("alias", Boolean.TRUE);
        configs.put("purchaseNSales", Boolean.TRUE);
        configs.put("delivered", Boolean.TRUE);
        return configs;
    }
}