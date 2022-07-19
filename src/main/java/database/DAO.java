package database;

import java.util.Map;
import java.util.Set;

/**
 * Interface of DAO pattern design
 * 
 */
public interface DAO<T> {

    /**
     * This method return all the objects stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of T from the cache
    */
    Set<T> getAll();

    /**
     * Given object, this is saved in the database, and if
     * this could be saved then is added to the cache and
     * return true, else return false.
     *
     * @param t to be saved
     * @return true iff the object was saved
     */
    boolean save(T t);

    /**
     * Given a object and a set of values, this is updated
     * in the database, and if this could be updated, then is
     * updated in the cache and return true, else return false
     *
     * @param t to be updated
     * @param params column names with the new values to update
     * @return true iff the object was updated
     */
    boolean update(T t, Map<String, Object> params);

    /**
     * Given a object, this is deleted from the database,
     * and if this could be deleted, then is deleted in
     * the cache too and return true, else return false
     *
     * @param t to be deleted
     * @return true iff the object was deleted
     */
    boolean delete(T t);

}