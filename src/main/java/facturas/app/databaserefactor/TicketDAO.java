package facturas.app.databaserefactor;

import facturas.app.models.Ticket;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TicketDAO implements DAO<Ticket> {

    private static TicketDAO instance;

    private final Set<Ticket> cache;
    private final boolean cacheWasLoaded;

    public static TicketDAO getInstance() {
        if (instance == null) {
            instance = new TicketDAO();
        }
        return instance;
    }

    private TicketDAO() {
        cache = new HashSet<>();
        cacheWasLoaded = false;
    }

    /**
     * This method return all the tickets stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of ticket from the cache
     */
    @Override
    public Set<Ticket> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
        return cache;
    }

    /**
     * Given a ticket, this is saved in the database, and if
     * this could be saved then is added to the cache and
     * return true, else return false.
     *
     * @param ticket to be saved
     * @return true iff the ticket was saved
     */
    @Override
    public boolean save(Ticket ticket) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * Given a ticket and a set of values, this is updated
     * in the database, and if this could be updated, then is
     * updated in the cache and return true, else return false
     *
     * @param ticket to be updated
     * @param params to be used in the update process
     * @return true iff the ticket was updated
     */
    @Override
    public boolean update(Ticket ticket, Map<String, Object> params) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * Given a ticket, this is deleted from the database,
     * and if this could be deleted, then is deleted in
     * the cache too and return true, else return false
     *
     * @param ticket to be deleted
     * @return true iff the ticket was deleted
     */
    @Override
    public boolean delete(Ticket ticket) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }

}
