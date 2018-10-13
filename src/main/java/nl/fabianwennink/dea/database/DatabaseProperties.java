package nl.fabianwennink.dea.database;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {

    private static DatabaseProperties dbProperties;
    private Properties properties;

    /**
     * Creates a new instance.
     */
    private DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the SQL driver property.
     *
     * @return The SQL driver.
     */
    public static String getDriver() {
        return getInstance().properties.getProperty("driver");
    }

    /**
     * Returns the SQL connection string.
     *
     * @return The SQL connection string.
     */
    public static String getConnectionString() {
        return getInstance().properties.getProperty("connectionString");
    }

    /**
     * Singleton for calling the DatabaseProperties class.
     *
     * @return A DatabaseProperties instance.
     */
    public static DatabaseProperties getInstance() {
        if(dbProperties == null) {
            dbProperties = new DatabaseProperties();
        }

        return dbProperties;
    }
}