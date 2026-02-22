package orm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class EntityManagerFactory {

    private HikariDataSource dataSource;

    public EntityManagerFactory() throws ClassNotFoundException{
        Class.forName("org.postgresql.Driver");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/dis403-2-lab2");
        config.setUsername("postgres");
        config.setPassword("artem_2006");
        config.setConnectionTimeout(50_000);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }
}
