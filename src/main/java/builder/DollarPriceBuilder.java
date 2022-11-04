package builder;

import models.DollarPrice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DollarPriceBuilder implements Builder{
    @Override
    public Object build(Object... data) {
        List<String> attributes = DollarPrice.getAttributes();
        Map<String,Object> values = new HashMap<String,Object>();
        int i = 0;
        for(String attribute : attributes){
            values.put(attribute, data[i]);
            i++;
        }
        return new DollarPrice(values);
    }
}
