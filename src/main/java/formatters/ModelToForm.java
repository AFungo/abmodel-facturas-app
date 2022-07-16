package formatters;

import models.*;

import java.util.Map;

public class ModelToForm {

    /**
     * Takes a ticket and returns its representation for a view
     * @param ticket contains the data to be returned
     * @return Object[] containing all the useful data of the ticket
     */
    public static Object[] toForm(Ticket ticket) {
        Map<String, Object> dict = ticket.getValues();
        Withholding withholding = (Withholding)dict.get("withholding");
        dict.putAll(withholding.getValues());

        Provider provider = (Provider)dict.get("provider");

        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = (String) provider.getValues().get("sector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));
        String buyNSell = (boolean)dict.get("issuedByMe") ? "VENTA" : "COMPRA";

        return new Object[] {dict.get("id"), dict.get("date"), dict.get("type"), dict.get("number"), dict.get("numberTo"),
                dict.get("authCode"), provider.getValues().get("docNo"), provider.getValues().get("name"),
                dict.get("exchangeType"), dict.get("netAmountWI"), dict.get("netAmountWOI"), dict.get("amountImpEx"),
                dict.get("ivaTax"), dict.get("totalAmount"), sector, buyNSell, delivered ? "SI" : "NO"};
    }

    /**
     * Takes a withholding and returns its representation for a view as
     * a profit withholding, in case there is no profit value an empty
     * array is returned
     * @param withholding contains the data to be returned
     * @return Object[] containing all the useful data of the withholding
     * in case there is no profit value an empty array is returned
     */
    public static Object[] profitWithholdingToForm(Withholding withholding) {
        Map<String, Object> dict = withholding.getValues();
        Object iva = dict.get("iva");
        if (iva == null || (Float) iva == 0.0f) {   //in case there is no iva withholding return empty array
            return new Object[0];
        }

        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't have a modified sector, we use provider sector
            sector = (String) provider.getValues().get("provSector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));

        return new Object[] {dict.get("id"), dict.get("date"), "Retencion Iva", dict.get("number"), null, null,
                provider.getValues().get("docNo"), provider.getValues().get("name"), null, null, null, null, null, iva,
                sector, null, delivered ? "SI" : "NO"};
    }

    /**
     * Takes a withholding and returns its representation for a view as
     * an iva withholding, in case there is no iva value an empty
     * array is returned
     * @param withholding contains the data to be returned
     * @return Object[] containing all the useful data of the withholding
     * in case there is no iva value an empty array is returned
     */
    public static Object[] IVAWithholdingToForm(Withholding withholding) {
        Map<String, Object> dict = withholding.getValues();
        Object profits = dict.get("profits");
        if (profits == null || (Float) profits == 0.0f) {   //in case there is no iva withholding return empty array
            return new Object[0];
        }

        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't have a modified sector, we use provider sector
            sector = (String) provider.getValues().get("provSector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));

        return new Object[] {dict.get("id"), dict.get("date"), "Retencion Ganancias", dict.get("number"), null, null,
                provider.getValues().get("docNo"), provider.getValues().get("name"), null, null, null, null, null,
                profits, sector, null, delivered ? "SI" : "NO"};
    }

    /**
     * Takes a provider and returns its representation for a view
     * @param provider contains the data to be returned
     * @return Object[] containing all the useful data of the provider
     */
    public static Object[] toForm(Provider provider) {
        Map<String, Object> dict = provider.getValues();
        return new Object[] {dict.get("docNo"), dict.get("name"), dict.get("docType"), dict.get("address"),
                dict.get("provSector"), dict.get("alias")};
    }

}
