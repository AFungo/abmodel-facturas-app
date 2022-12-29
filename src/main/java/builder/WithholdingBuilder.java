package builder;

import database.ProviderDAO;
import models.Provider;
import models.Sector;
import models.Withholding;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class WithholdingBuilder implements Builder{

    @Override
    public Object build(Object... data) {
        Sector sector = (Sector) ProviderDAO.getInstance().getAll().stream()
                .filter(p -> p.getID().equals(((Provider)data[0]).getID()))
                .findFirst().get().getValues().get("sector");

        Map<String, Object> withholdingValues = new HashMap<String, Object>(){{
            put("provider", data[0]);
            put("date", Date.valueOf((String)data[1]));
            put("number", data[2]);

            put("sector", sector);
            if(data.length >= 7){
                put("iva",Float.valueOf((String)data[3]));
                put("profits", Float.valueOf((String) data[4]));
                put("delivered", data[5]);
                if(data[6] != null) put("sector", data[6]);
                if(data.length == 8) put("id", data[7]);
            }
        }};

        return new Withholding(withholdingValues);
    }
}
