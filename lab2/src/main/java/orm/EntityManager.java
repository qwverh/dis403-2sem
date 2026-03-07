package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface EntityManager {
    <T> T save(T entity) throws IllegalAccessException, SQLException; // insert, update
    void remove(Object entity) throws IllegalAccessException, SQLException; // delete ... where id =
    <T> T find(Class<T> entityType, Object key) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException; // select ... where id =
    <T> List<T> findAll(Class<T> entityType) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException; // select ...
}