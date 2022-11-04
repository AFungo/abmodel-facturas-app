package builder;

import models.Provider;
import models.Ticket;
import models.Withholding;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Date;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderBuilderTest {
    @ParameterizedTest
    @MethodSource("providerDataGenerator")
    void buildProviderTest(String docNo, String name, String docType, String address, String provSector, String alias) {


        Provider expectedProvider = new Provider(
                new HashMap<String, Object>() {{
                    put("docNo", docNo);
                    put("name", name);
                    put("docType", docType);
                    put("address", address);
                    put("provSector", provSector);
                    put("alias", alias);
                }}
        );

        Builder pb = new ProviderBuilder();
        Provider provider = (Provider) pb.build(docType, docNo, name, address, provSector, alias);

        assertEquals(expectedProvider.getValues() ,provider.getValues());
    }

    static Stream<Arguments> providerDataGenerator() {

        return Stream.of(
                Arguments.of("321213","Carlo", "CUIT", "Calle falsa 123", "Agricultura", "Carlito el pillo")
        );
    }
}
