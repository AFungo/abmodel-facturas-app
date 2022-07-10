package dao;

import facturas.app.databaserefactor.TicketDAO;
import facturas.app.models.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketDAOTest {

    TicketDAO dao;

    @BeforeEach
    void setUp() {
        dao = TicketDAO.getInstance();
    }

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = TicketDAO.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void saveTest() {
        Ticket t = new Ticket(new HashMap<>());
        assertThat(dao.save(t)).isTrue();
        assertThat(dao.getAll()).containsExactly(t);
        assertThat(dao.save(t)).isFalse();
    }

    @Test
    void deleteTest() {
        Ticket t = new Ticket(new HashMap<>());
        dao.save(t);
        assertThat(dao.getAll()).contains(t);
        assertThat(dao.delete(t)).isTrue();
        assertThat(dao.getAll()).doesNotContain(t);
        assertThat(dao.delete(t)).isFalse();
    }

    @Test
    void updateTest() {
        Ticket t1 = new Ticket(new HashMap<>());
        Map<String, Object> params = new HashMap<>();
        dao.save(t1);
        assertThat(dao.getAll()).contains(t1);
        assertThat(dao.update(t1, params)).isTrue();
        // Make some comparison for check if the update was successful
    }

}
