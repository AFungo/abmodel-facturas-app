package loader;

import builder.Builder;
import builder.WithholdingBuilder;
import database.DBManager;
import database.WithholdingDAO;
import models.Provider;
import models.Sector;
import models.Withholding;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Date;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithholdingLoaderTest {
    @BeforeEach
    void setUp(){
        DBManager.createConnection(DBManager.TypeDB.PRODUCTION);
        DBManager.initializeDB();
    }
    @AfterEach
    void afterEach(){
        DBManager.deleteDB();
        DBManager.closeConnection();
    }
    @ParameterizedTest
    @MethodSource("withholdingDataGenerator")
    void buildWithholdingTest(Withholding withholding, int expectedWithholding) {



        WithholdingLoader withholdingLoader = new WithholdingLoader();
        withholdingLoader.load(withholding);
        assertEquals(expectedWithholding, WithholdingDAO.getInstance().getAll().size());
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
                Arguments.of(
                        new Withholding(
                            new HashMap<String, Object>(){{
                                put("date", Date.valueOf("2022-03-10"));
                                put("number", "1234");
                                put("provider", provider);
                                put("iva", (float) 1234.0);
                                put("profits", (float) 12456.0);
                                put("delivered", true);
//                                put("sector", new Sector(new HashMap<String, Object>(){{put("name", "agricultura");}}));
                            }}
                    ), 1
                )
        );
    }
}
