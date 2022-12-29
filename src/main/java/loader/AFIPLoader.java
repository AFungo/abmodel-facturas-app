package loader;

import java.io.File;
import java.util.*;

import utils.csv.*;

public class AFIPLoader {

    public final static String[] AFIPTransmitterHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Emisor","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    public final static String[] AFIPReceiverHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Receptor","Nro. Doc. Receptor","Denominación Receptor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};

    /**
     * Crates the models from the info given from a AFIP file and saves them in database
     * @param file tickets csv AFIP file
     */
    public static void loadFromAFIPFile(File file) {

        boolean issuedByMe = false;
        List<String[]> files;

        try {
            files = CSVUtils.readCSV(file, AFIPTransmitterHeader);
        } catch (IllegalArgumentException e) {  //in case the header didn't match, we assume the receiver
            issuedByMe = true;                  //header will match
            files = CSVUtils.readCSV(file, AFIPReceiverHeader);
            //in case this file doesn't match either the exception will be raised further in the program
        }
        LoadFromAFIPLine loader = new LoadFromAFIPLine(issuedByMe);
        //for each string[] get the values create the models and put it in the values
        for(String[] line : files) {
            loader.load(line);
        }
    }

}