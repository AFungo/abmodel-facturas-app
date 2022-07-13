package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.DollarPriceDAO;
import facturas.app.models.DollarPrice;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DollarPriceDAOTest {

    DAO<DollarPrice> dao;
    DollarPrice d;

    @BeforeAll
    static void createConnection() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
    }

    @BeforeEach
    void setUp() {
        DBManager.initializeDB();
        dao = DollarPriceDAO.getInstance();
        d = new DollarPrice(new HashMap<String, Object>() {{
            put("date", Date.valueOf("2022-7-4"));
            put("buy", 250.0f);
            put("sell", 200.0f);
        }});
    }

    @AfterAll
    static void closeConnection() {
        DBManager.closeConnection();
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = DollarPriceDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        DBManager.deleteDB();
    }

    @Test
    void saveTest() {
        assertThat(dao.save(d)).isTrue();
        assertThat(dao.getAll()).containsExactly(d);
        assertThat(dao.save(d)).isFalse();
    }

    @Test
    void deleteTest() {
        assertThat(dao.getAll()).doesNotContain(d);
        assertThat(dao.save(d)).isTrue();
        assertThat(dao.getAll()).contains(d);
        assertThat(dao.delete(d)).isTrue();
        assertThat(dao.getAll()).doesNotContain(d);
        assertThat(dao.delete(d)).isFalse();
    }

    @Test
    void updateTest() {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("buy", 280.0f);
            put("sell", 233.0f);
        }};
        assertThat(dao.save(d)).isTrue();
        assertThat(dao.getAll()).contains(d);
        Map<String, Object> oldValues = d.getValues();
        assertThat(dao.update(d, params)).isTrue();
        assertThat(oldValues.entrySet()).doesNotContainAnyElementsOf(params.entrySet());
        assertThat(d.getValues()).containsAllEntriesOf(params);
    }

}