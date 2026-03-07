package orm;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityManagerImpl implements EntityManager, Closeable {

    private Connection connection;
    private Map<Class<?>, EntityMetaData> metaDataMap;

    public EntityManagerImpl(Connection connection, Map<Class<?>, EntityMetaData> metaDataMap) {
        this.connection = connection;
        this.metaDataMap = metaDataMap;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T save(T entity) throws IllegalAccessException, SQLException {

        Class<?> clazz = entity.getClass();
        EntityMetaData metaData = metaDataMap.get(clazz);
        Field idField = metaData.getIdField();
        idField.setAccessible(true);
        Object id = idField.get(entity);
        if (id == null) {
            StringBuilder sql = new StringBuilder();
            sql.append("insert into ")
                    .append(metaData.getTableName())
                    .append("(");
            for (Field field : metaData.getColumns()) {
                sql.append(field.getName()).append(",");
            }
            for (Field field : metaData.getRelations()) {
                sql.append(field.getName()).append("_id").append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") values (");
            int columnsCount = metaData.getRelations().size() + metaData.getColumns().size();
            for (int i = 0; i < columnsCount; i++) {
                sql.append("?").append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");

            try (PreparedStatement ps = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS)) {
                int index = 1;
                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    ps.setObject(index++, o);
                }

                for (Field field : metaData.getRelations()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    if (o != null) {
                        EntityMetaData relData = metaDataMap.get(field.getType());
                        Field idRelField = relData.getIdField();
                        idRelField.setAccessible(true);
                        Object idRel = idRelField.get(o);
                        ps.setObject(index++, idRel);
                    } else {
                        ps.setObject(index++, null);
                    }
                }
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Object generatedId = rs.getObject(1);
                    idField.set(entity, ((Number) generatedId).longValue());
                }
            }
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append("update ").append(metaData.getTableName())
                    .append(" set ");
            for (Field field : metaData.getColumns()) {
                sql.append(field.getName())
                        .append("=?,");
            }

            for (Field field : metaData.getRelations()) {
                sql.append(field.getName())
                        .append("_id=?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where ").append(metaData.getIdField().getName()).append("=?");

            try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
                int index = 1;
                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    ps.setObject(index++, o);
                }

                for (Field field : metaData.getRelations()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    if (o != null) {
                        EntityMetaData relData = metaDataMap.get(field.getType());
                        Field idRelField = relData.getIdField();
                        idRelField.setAccessible(true);
                        Object idRel = idRelField.get(o);
                        ps.setObject(index++, idRel);
                    } else {
                        ps.setObject(index++, null);
                    }
                }
                ps.setObject(index, id);
                ps.executeUpdate();
            }

        }

        return entity;
    }

    @Override
    public void remove(Object entity) throws IllegalAccessException, SQLException {

        EntityMetaData metaData = metaDataMap.get(entity.getClass());
        String tableName = metaData.getTableName();
        Field idField = metaData.getIdField();
        String idFieldName = idField.getName();
        idField.setAccessible(true);
        Object idValue = idField.get(entity);
        String sql = "delete from " + tableName + " where " + idFieldName + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, idValue);
            ps.executeUpdate();
        }
    }

    @Override
    public <T> T find(Class<T> entityType, Object key) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        EntityMetaData metaData = metaDataMap.get(entityType);
        String tableName = metaData.getTableName();
        String idName = metaData.getIdField().getName();
        String sql = "select * from " + tableName + " where " + idName  + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, key);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                T entity = entityType.getDeclaredConstructor().newInstance();
                Field idField = metaData.getIdField();
                idField.setAccessible(true);
                Object idValue = resultSet.getObject(idField.getName());
                idField.set(entity, ((Number)idValue).longValue());

                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object o = resultSet.getObject(field.getName());
                    field.set(entity, o);
                }

                for (Field field : metaData.getRelations()) {
                    field.setAccessible(true);
                    Object o = resultSet.getObject(field.getName() + "_id");
                    if (o != null) {
                        Object relEntity = find(field.getType(), o);
                        field.set(entity, relEntity);
                    } else {
                        field.set(entity, null);
                    }
                }
                return entity;
            }
        }

        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<>();
        EntityMetaData metaData = metaDataMap.get(entityType);
        String tableName = metaData.getTableName();
        String sql = "select * from " + tableName;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                T entity = entityType.getDeclaredConstructor().newInstance();
                Field idField = metaData.getIdField();
                idField.setAccessible(true);
                Object id = resultSet.getObject(idField.getName());
                idField.set(entity, ((Number)id).longValue());

                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object o = resultSet.getObject(field.getName());
                    field.set(entity, o);
                }

                for (Field field : metaData.getRelations()) {
                    field.setAccessible(true);

                    Object relationId = resultSet.getObject(field.getName() + "_id");

                    if (relationId != null) {
                        Object relationEntity = find(field.getType(), relationId);
                        field.set(entity, relationEntity);
                    } else {
                        field.set(entity, null);
                    }

                }
                result.add(entity);

            }
        }
        return result;
    }

}