package factory;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.Factura_productoDAO;
import dao.ProductoDAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class MySQLDAOFactory extends AbstractFactory {
    private static MySQLDAOFactory instance = null;
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/integrador_1";
    public static Connection conn;

    public static synchronized AbstractFactory getInstance(){//Singletone
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }
    public static Connection getConnection() {
        if( conn != null){
            return conn;
        }
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

    @Override
    public ClienteDAO getClienteDao() {
        return new ClienteDAO(getConnection());
    }

    @Override
    public Factura_productoDAO getFactura_ProductoDao() {
        return new Factura_productoDAO(getConnection());
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return new FacturaDAO(getConnection());
    }

    @Override
    public ProductoDAO getProductoDao() {
        return new ProductoDAO(getConnection());
    }

    public void closeConnection(){
        try{
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
