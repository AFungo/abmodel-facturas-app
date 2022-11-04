package builder;

import models.Provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProviderBuilder implements Builder{
    @Override
    public Object build(Object... data) {
//        String[] values = (String[]) Arrays.stream(data).toArray();

        Map<String, Object> providerValues = new HashMap<String, Object>(){{
            put("docType", data[0]);
            put("docNo", data[1]);
            put("name", data[2]);
            if (data.length == 6) {
                put("address", data[3]);
                put("provSector", data[4]);
                put("alias", data[5]);
            }
        }};

        return new Provider(providerValues);
    }
}
