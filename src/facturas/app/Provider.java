/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturas.app;

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
    
    public String toString(){
        return "Nombre" + name + "    " + "Cuit" + cuit; 
    }          
    
    public String [ ] getSQLValues() {
        String attributes = "", values = "";
        attributes += "cuit, name, documentType";
        values += "'" + cuit + "', '" + name + "', '" + documentType + "'";

        String [ ] result = {attributes, values};
        return result;
    }
}