/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturas.app.models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class Provider {
    private String docNo;
    private String name;
    private String docType;
    private String direction;
    private String sector;

    public Provider(Map<String, String> values){
        docNo = values.get("docNo");
        name = values.get("name");
        docType = values.get("docType");
    }
    
    public String getDocType(){
        return docType;
    }
    
    public String getDocNo(){
        return docNo;
    }
    public String getName(){
        return name;
    }
    
    public void addDirection(String direction) {
        if (direction != null)
            this.direction = direction;
    }
    
    public void addSector(String sector) {
        if (sector != null)
            this.sector = sector;
    }
    
    public String getSector() {
        return sector;
    }
    
    @Override
    public String toString(){
        return "Nombre: " + name + ", Cuit: " + docNo + ", direccion: " + direction + ", sector: " + sector; 
    }          
    
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("docNo", docNo);
        values.put("name", name);
        values.put("docType", docType);
        if (direction != null) values.put("direction", direction);
        if (sector != null) values.put("sector", sector);
        return values;
    }
}