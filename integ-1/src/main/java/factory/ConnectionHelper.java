package factory;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class ConnectionHelper {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/integrador_1";
    public static Connection conn;

    public static Connection getInstance() {//Singletone
        if (conn == null) {
            conn = ConnectionHelper();
        }
        return conn;
    }

    private static Connection ConnectionHelper() {
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}


