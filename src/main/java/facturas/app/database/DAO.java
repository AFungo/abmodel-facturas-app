package facturas.app.database;

import java.util.List;

/**
 * Interface of DAO pattern dessign
 * 
 */
public interface DAO<T> {

    T get(long id);
    List<T> get();
    boolean save(T t);
    boolean update(T t);
    boolean delete(T t);    
}