package orm;
import orm.annotation.Column;
import orm.annotation.Id;
import orm.annotation.ManyToOne;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class EntityMetaData {
    private Class<?> entityClass;
    private String tableName;
    private Field idField;
    private List<Field> columns = new ArrayList<>();
    private List<Field> relations = new ArrayList<>();

    public static EntityMetaData parseEntity(Class<?> entity) {
        EntityMetaData entityMetaData = new EntityMetaData();

        String tableName = entity.getSimpleName().toLowerCase();
        entityMetaData.setTableName(tableName);

        entityMetaData.setEntityClass(entity);


        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                entityMetaData.setIdField(field);
            }

            if (field.isAnnotationPresent(Column.class)) {
                entityMetaData.getColumns().add(field);
            }

            if (field.isAnnotationPresent(ManyToOne.class)) {
                entityMetaData.getRelations().add(field);
            }
        }


        return entityMetaData;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Field getIdField() {
        return idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
    }

    public List<Field> getColumns() {
        return columns;
    }

    public void setColumns(List<Field> columns) {
        this.columns = columns;
    }

    public List<Field> getRelations() {
        return relations;
    }

    public void setRelations(List<Field> relations) {
        this.relations = relations;
    }
}
