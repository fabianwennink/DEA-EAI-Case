package nl.fabianwennink.dea.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultInterface {

    void process(ResultSet resultSet) throws SQLException;

}