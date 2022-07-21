package dao;

import database.DBManager;
import databaserefactor.DAO;
import databaserefactor.DollarPriceDAO;
import models.DollarPrice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Isolated
public class DollarPriceDAOTest {

    private DAO<DollarPrice> dao;
    private DollarPrice d;

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
        try {
            DBManager.deleteDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
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