package orm;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EntityManagerImpl implements EntityManager, Closeable {
    private Connection connection;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
    }

    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T save(T entity) {
        // Определяем имя таблицы по имени класса
        // Ищем в классе поля (Id, Column, ManyToOne)
        // Стоим SQL запрос: Insert (id = null), Update (id != null)
        // Выполняем через JDBC запрос

        return null;
    }

    @Override
    public void remove(Object entity) {


    }

    @Override
    public <T> T find(Class<T> entityType, Object key) {
        // По имени класса получаем имя таблицы, фиксируем id
        // select * from tableName where id = key
        // Если результат не пустой - создаем объект
        // Ищем в классе поля (Id, Column, ManyToOne)
        // Для каждого поля пытаемся получить значение из ResultSet по имени
        // Задаем значение
        // Возвращаем созданный объект
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) {
        return List.of();
    }
}
