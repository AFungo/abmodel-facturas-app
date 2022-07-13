package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.ProviderDAO;
import facturas.app.models.Provider;
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

    @BeforeAll
    static void createConnection() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
    }

    @BeforeEach
    void setUp() {
        DBManager.initializeDB();
        dao = ProviderDAO.getInstance();
        p = new Provider(new HashMap<String, Object>() {{
            put("docNo", "45123123");
            put("name", "Manolito");
            put("docType", "CUIT");
        }});
    }

    @AfterAll
    static void closeConnection() {
        DBManager.closeConnection();
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = ProviderDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        DBManager.deleteDB();
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