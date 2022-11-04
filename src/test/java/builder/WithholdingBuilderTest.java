package builder;

import models.Provider;
import models.Sector;
import models.Ticket;
import models.Withholding;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Date;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithholdingBuilderTest {
    @ParameterizedTest
    @MethodSource("withholdingDataGenerator")
    void buildWithholdingTest(Date date, String number, Provider provider, Float iva, Float profits,
                         boolean delivered, Sector sector) {


        Withholding expectedWithholding = new Withholding(
                new HashMap<String, Object>(){{
                    put("date", date);
                    put("number", number);
                    put("provider", provider);
                    put("iva", iva);
                    put("profits", profits);
                    put("delivered", delivered);
                    put("sector", sector);
                }}
        );

        Builder wb = new WithholdingBuilder();
        Withholding withholding = (Withholding) wb.build(provider, date.toString(), number, iva.toString(), profits.toString(), delivered, sector);

        assertEquals(expectedWithholding.getValues() ,withholding.getValues());
    }

    static Stream<Arguments> withholdingDataGenerator() {

        Provider provider = new Provider(
                new HashMap<String, Object>(){{
                    put("id", 2);
                    put("docNo", "321213");
                    put("name", "Carlo");
                    put("docType", "CUIT");
                    put("address", "Calle falsa 123");
                    put("provSector", "Agricultura");
                    put("alias", "Carlito el pillo");
                }}
        );
        return Stream.of(
                Arguments.of(Date.valueOf("2022-03-10"), "1234", provider, (float)200.33,
                        (float) 23000.43, true, new Sector(new HashMap<String, Object>(){{put("name", "agricultura");}}))
        );
    }
}
