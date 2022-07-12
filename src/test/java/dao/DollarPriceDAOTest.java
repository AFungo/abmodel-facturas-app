package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.DollarPriceDAO;
import facturas.app.models.DollarPrice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DollarPriceDAOTest {

    DAO<DollarPrice> dao;
    DollarPrice d;

    @BeforeEach
    void setUp() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
        DBManager.initializeDB();
        dao = DollarPriceDAO.getInstance();
        d = new DollarPrice(new HashMap<String, Object>() {{
            put("date", Date.valueOf("2022-7-4"));
            put("buy", 250.0f);
            put("sell", 200.0f);
        }});
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = DollarPriceDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @AfterEach
    void tearDown() {
        DBManager.deleteDB();
        DBManager.closeConnection();
    }

    @Test
    void saveTest() {
        assertThat(dao.save(d)).isTrue();
        assertThat(dao.getAll()).containsExactly(d);
        assertThat(dao.save(d)).isFalse();
    }

    @Test
    void deleteTest() {
        dao.save(d);
        assertThat(dao.getAll()).contains(d);
        assertThat(dao.delete(d)).isTrue();
        assertThat(dao.getAll()).doesNotContain(d);
        assertThat(dao.delete(d)).isFalse();
    }

    @Test
    void updateTest() {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("date", Date.valueOf("2018-8-13"));
            put("buy", 40.0f);
            put("sell", 30.0f);
        }};
        dao.save(d);
        assertThat(dao.getAll()).contains(d);
        Map<String, Object> oldValues = d.getValues();
        assertThat(dao.update(d, params)).isTrue();
        assertThat(oldValues.entrySet()).doesNotContainAnyElementsOf(params.entrySet());
        assertThat(d.getValues()).containsAllEntriesOf(params);
    }

}