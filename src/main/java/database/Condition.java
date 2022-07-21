package database;

/**
 * Class used for represent a condition of a SQL query
 */
public class Condition {
    
    private final String attr;
    private final String comparison;
    private final Object val;
    private final Class<?> valClass;
    
    /**
     * Constructor
     * 
     * @param attr attribute of the table
     * @param comparison comparison operator that will be used
     * @param val value to which the attribute will be compared
     * @param valClass class of the value/attribute
     */
    public Condition(String attr, String comparison, Object val, Class<?> valClass) {
        this.attr = attr;
        this.comparison = comparison;
        this.val = val;
        this.valClass = valClass;
    }
    
    /**
     * Attribute getter
     * 
     * @return the attribute
     */
    public String getAttr() {
        return attr;
    }
    
    /**
     * Comparison operator getter
     * 
     * @return the comparison operator
     */
    public String getComparison() {
        return comparison;
    }
    
    /**
     * Value getter
     * 
     * @return the value
     */
    public Object getVal() {
        return val;
    }
    
    /**
     * Value class getter
     * 
     * @return the class of the value
     */
    public Class<?> getValClass() {
        return valClass;
    }
    
}
