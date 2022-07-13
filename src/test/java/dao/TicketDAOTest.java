package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.ProviderDAO;
import facturas.app.databaserefactor.TicketDAO;
import facturas.app.databaserefactor.WithholdingDAO;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Isolated
public class TicketDAOTest {

    private DAO<Ticket> dao;
    private Ticket t;

    @BeforeAll
    static void createConnection() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
    }

    @BeforeEach
    void setUp() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
        DBManager.initializeDB();
        dao = TicketDAO.getInstance();
        Provider provider = new Provider(new HashMap<String, Object>() {{
            put("docNo", "45123123");
            put("name", "Manolito");
            put("docType", "CUIT");
        }});
        ProviderDAO.getInstance().save(provider);

        Withholding withholding = new Withholding(new HashMap<String, Object>() {{
            put("date", Date.valueOf("2018-12-25"));
            put("number", "0123456789");
            put("provider", provider);
        }});
        WithholdingDAO.getInstance().save(withholding);

        t = new Ticket(new HashMap<String, Object>() {{
            put("id", withholding.getValues().get("id"));
            put("date", Date.valueOf("2022-5-25"));
            put("number", "0123456789");
            put("provider", provider);
            put("type", "6 - Factura B");
            put("exchangeType", 1.5f);
            put("exchangeMoney", "$");
            put("totalAmount", 1000.0f);
            put("issuedByMe", true);
        }});
    }

    @AfterAll
    static void closeConnection() {
        DBManager.closeConnection();
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = TicketDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        DBManager.deleteDB();
    }

    @Test
    void saveTest() {
        assertThat(dao.save(t)).isTrue();
        assertThat(dao.getAll()).containsExactly(t);
        assertThat(dao.save(t)).isFalse();
    }

    @Test
    void deleteTest() {
        dao.save(t);
        assertThat(dao.getAll()).contains(t);
        assertThat(dao.delete(t)).isTrue();
        assertThat(dao.getAll()).doesNotContain(t);
        assertThat(dao.delete(t)).isFalse();
    }

    @Test
    void updateTest() {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("totalAmount", 2535.6f);
            put("type", "3 - Factura C");
        }};
        dao.save(t);
        assertThat(dao.getAll()).contains(t);
        Map<String, Object> oldValues = t.getValues();
        assertThat(dao.update(t, params)).isTrue();
        assertThat(oldValues.entrySet()).doesNotContainAnyElementsOf(params.entrySet());
        assertThat(t.getValues()).containsAllEntriesOf(params);
    }

}