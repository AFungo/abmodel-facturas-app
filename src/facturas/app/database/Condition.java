/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturas.app.database;

/**
 *
 * @author agustinnolasco
 */
public class Condition {
    private String attr;
    private String comparison;
    private Object val;
    private Class<?> valClass;
    
    public Condition(String attr, String comparison, Object val, Class<?> valClass) {
        this.attr = attr;
        this.comparison = comparison;
        this.val = val;
        this.valClass = valClass;
    }
    
    public String getAttr() {
        return attr;
    }
    
    public String getComparison() {
        return comparison;
    }
        
    public Object getVal() {
        return val;
    }
            
    public Class<?> getValClass() {
        return valClass;
    }
    
}
