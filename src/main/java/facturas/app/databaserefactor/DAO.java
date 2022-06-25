package facturas.app.databaserefactor;

import java.util.Map;
import java.util.Set;

/**
 * Interface of DAO pattern design
 * 
 */
public interface DAO<T> {

    Set<T> getAll();

    boolean save(T t);

    boolean update(T t, Map<String, Object> params);

    boolean delete(T t);

}