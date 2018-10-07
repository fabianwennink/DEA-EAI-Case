package nl.fabianwennink.dea.database;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {

    private static DatabaseProperties dbProperties;
    private Properties properties;

    private DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getDriver() {
        return getInstance().properties.getProperty("driver");
    }

    public static String getConnectionString() {
        return getInstance().properties.getProperty("connectionString");
    }

    public static DatabaseProperties getInstance() {
        if(dbProperties == null) {
            dbProperties = new DatabaseProperties();
        }

        return dbProperties;
    }
}