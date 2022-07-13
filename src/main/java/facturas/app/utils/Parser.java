package facturas.app.utils;

public class Parser {

    public static Float parseFloat(String str) {
        if (str == null) {
            return null;
        }
        return Float.parseFloat(str);
    }

    public static Integer parseInt(String str) {
        if (str == null) {
            return null;
        }
        return Integer.parseInt(str);
    }

    public static Boolean parseBool(String str) {
        if (str == null) {
            return null;
        }
        return Boolean.parseBoolean(str);
    }

}
