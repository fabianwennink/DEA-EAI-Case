package nl.fabianwennink.dea.database.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory(final String unit) {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(unit);
        }

        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
