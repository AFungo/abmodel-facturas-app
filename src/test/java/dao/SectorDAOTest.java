package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.SectorDAO;
import facturas.app.models.Sector;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Isolated
public class SectorDAOTest {
    
    private DAO<Sector> dao;
    private Sector s;

    @BeforeEach
    void setUp() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
        DBManager.initializeDB();
        dao = SectorDAO.getInstance();
        s = new Sector(Collections.singletonMap("name", "droga"));
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = SectorDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        DBManager.deleteDB();
        DBManager.closeConnection();
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