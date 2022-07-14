package dao;

import database.DBManager;
import databaserefactor.DAO;
import databaserefactor.ProviderDAO;
import models.Provider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Isolated
public class ProviderDAOTest {

    private DAO<Provider> dao;
    private Provider p;

    @BeforeEach
    void setUp() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
        DBManager.initializeDB();
        dao = ProviderDAO.getInstance();
        p = new Provider(new HashMap<String, Object>() {{
            put("docNo", "45123123");
            put("name", "Manolito");
            put("docType", "CUIT");
        }});
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = ProviderDAO.class.getDeclaredField("instance");
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
        assertThat(dao.save(p)).isTrue();
        assertThat(dao.getAll()).containsExactly(p);
        assertThat(dao.save(p)).isFalse();
    }

    @Test
    void deleteTest() {
        assertThat(dao.getAll()).doesNotContain(p);
        assertThat(dao.save(p)).isTrue();
        assertThat(dao.getAll()).contains(p);
        assertThat(dao.delete(p)).isTrue();
        assertThat(dao.getAll()).doesNotContain(p);
        assertThat(dao.delete(p)).isFalse();
    }

    @Test
    void updateTest() {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("docType", "CUIL");
            put("docNo", "27123147");
        }};
        assertThat(dao.save(p)).isTrue();
        assertThat(dao.getAll()).contains(p);
        Map<String, Object> oldValues = p.getValues();
        assertThat(dao.update(p, params)).isTrue();
        assertThat(oldValues.entrySet()).doesNotContainAnyElementsOf(params.entrySet());
        assertThat(p.getValues()).containsAllEntriesOf(params);
    }

}