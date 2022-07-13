package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.ProviderDAO;
import facturas.app.databaserefactor.SectorDAO;
import facturas.app.models.Provider;
import facturas.app.models.Sector;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SectorDAOTest {
    
    private DAO<Sector> dao;
    private Sector s;

    @BeforeAll
    static void createConnection() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
    }

    @BeforeEach
    void setUp() {
        DBManager.initializeDB();
        dao = SectorDAO.getInstance();
        s = new Sector(Collections.singletonMap("name", "droga"));
    }

    @AfterAll
    static void closeConnection() {
        DBManager.closeConnection();
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = SectorDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        DBManager.deleteDB();
    }

    @Test
    void saveTest() {
        assertThat(dao.save(s)).isTrue();
        assertThat(dao.getAll()).containsExactly(s);
        assertThat(dao.save(s)).isFalse();
    }

    @Test
    void deleteTest() {
        assertThat(dao.getAll()).doesNotContain(s);
        assertThat(dao.save(s)).isTrue();
        assertThat(dao.getAll()).contains(s);
        assertThat(dao.delete(s)).isTrue();
        assertThat(dao.getAll()).doesNotContain(s);
        assertThat(dao.delete(s)).isFalse();
    }

    @Test
    void updateTest() {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("name", "Quimicos");
        }};
        assertThat(dao.save(s)).isTrue();
        assertThat(dao.getAll()).contains(s);
        Map<String, Object> oldValues = s.getValues();
        assertThat(dao.update(s, params)).isTrue();
        assertThat(oldValues.entrySet()).doesNotContainAnyElementsOf(params.entrySet());
        assertThat(s.getValues()).containsAllEntriesOf(params);
    }
    
}