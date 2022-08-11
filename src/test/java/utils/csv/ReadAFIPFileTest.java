package utils.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class ReadAFIPFileTest {

    private final String[] transmitterHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Emisor","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    private final String[] receiverHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Receptor","Nro. Doc. Receptor","Denominación Receptor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    File transmitterFile;
    File receiverFile;
    File invalidFile;
    String [][] result;

    @BeforeEach
    void setUp() {
        transmitterFile = new File("src/test/resources/AFIPTransmitterFile.csv");
        receiverFile = new File("src/test/resources/AFIPReceiverFile.csv");
        invalidFile = new File("src/test/resources/AFIPInvalidFile.csv");
        result = new String[0][];
    }

    @Test
    void readAFIPTransmitterFileTest() {
        assertThatNoException().isThrownBy(() -> result = CSVUtils.readCSV(transmitterFile, transmitterHeader).toArray(result));
        assertThat(result.length).isNotZero();
        assertThat(result[0]).isNotEqualTo(transmitterHeader);
    }

    @Test
    void readAFIPReceiverFileTest() {
        assertThatNoException().isThrownBy(() -> result = CSVUtils.readCSV(receiverFile, receiverHeader).toArray(result));
        assertThat(result.length).isNotZero();
        assertThat(result[0]).isNotEqualTo(receiverHeader);
    }

    @Test
    void readAFIPInvalidHeaderFileTest() {
        assertThatThrownBy(() -> CSVUtils.readCSV(invalidFile, transmitterHeader))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The given file is invalid for header: \n" + Arrays.toString(transmitterHeader));
    }

}
