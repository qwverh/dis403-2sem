import orm.EntityManager;
import orm.EntityManagerFactory;
import orm.model.City;
import orm.model.Country;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {


        EntityManagerFactory emf = new EntityManagerFactory();
        EntityManager em = emf.getEntityManager();

        Country country = new Country();
        country.setName("France");
        em.save(country);

        City city = new City();
        city.setName("Париж");
        city.setCountry(country);
        em.save(city);





    }
}
