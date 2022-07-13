package dao;

import facturas.app.database.DBManager;
import facturas.app.databaserefactor.DAO;
import facturas.app.databaserefactor.ProviderDAO;
import facturas.app.databaserefactor.WithholdingDAO;
import facturas.app.models.Provider;
import facturas.app.models.Withholding;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Isolated
public class WithholdingDAOTest {

    private DAO<Withholding> dao;
    private Withholding w;

    @BeforeEach
    void setUp() {
        DBManager.createConnection(DBManager.TypeDB.TESTING);
        DBManager.initializeDB();
        dao = WithholdingDAO.getInstance();
        Provider provider = new Provider(new HashMap<String, Object>() {{
            put("docNo", "45123123");
            put("name", "Manolito");
            put("docType", "CUIT");
        }});
        ProviderDAO.getInstance().save(provider);
        w = new Withholding(new HashMap<String, Object>() {{
            put("date", Date.valueOf("2018-12-25"));
            put("number", "0123456789");
            put("provider", provider);
        }});
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = WithholdingDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        DBManager.deleteDB();
        DBManager.closeConnection();
    }

    @Test
    void saveTest() {
        assertThat(dao.save(w)).isTrue();
        assertThat(dao.getAll()).containsExactly(w);
        assertThat(dao.save(w)).isFalse();
    }

    @Test
    void deleteTest() {
        dao.save(w);
        assertThat(dao.getAll()).contains(w);
        assertThat(dao.delete(w)).isTrue();
        assertThat(dao.getAll()).doesNotContain(w);
        assertThat(dao.delete(w)).isFalse();
    }

    @Test
    void updateTest() {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("date", Date.valueOf("2021-6-20"));
            put("number", "1234");
        }};
        dao.save(w);
        assertThat(dao.getAll()).contains(w);
        Map<String, Object> oldValues = w.getValues();
        assertThat(dao.update(w, params)).isTrue();
        assertThat(oldValues.entrySet()).doesNotContainAnyElementsOf(params.entrySet());
        assertThat(w.getValues()).containsAllEntriesOf(params);
    }

}