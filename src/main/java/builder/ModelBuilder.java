package builder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelBuilder {

    public static Map<String, List<Object>> buildFromFile() {
        Map<String, List<Object>> values = new HashMap<String, List<Object>>() {{
                put("tickets", new LinkedList<>());
                put("withholding", new LinkedList<>());
                put("providers", new LinkedList<>());
        }};

        throw new UnsupportedOperationException();
    }

}