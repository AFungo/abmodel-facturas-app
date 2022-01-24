/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturas.app.models;

import facturas.app.utils.Pair;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class Provider {
    private String cuit;
    private String name;
    private String documentType;

    public Provider(String documentType, String cuit, String name){
        this.documentType = documentType;
        this.cuit = cuit;
        this.name = name;
    }
    
    public String getDocType(){
        return documentType;
    }
    
    public String getCuit(){
        return cuit;
    }
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return "Nombre" + name + "    " + "Cuit" + cuit; 
    }          
    
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("cuit", cuit);
        values.put("name", name);
        values.put("documentType", documentType);
        return values;
    }
}