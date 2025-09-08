package factory;

import dao.impl.ClienteDAOImpl;
import dao.impl.FacturaDAOImpl;
import dao.impl.Factura_productoDAOImpl;
import dao.impl.ProductoDAOImpl;

import java.sql.Connection;

public abstract class AbstractFactory {
    public abstract ClienteDAOImpl getClienteDao();
    public abstract Factura_productoDAOImpl getFactura_ProductoDao();
    public abstract FacturaDAOImpl getFacturaDAO();
    public abstract ProductoDAOImpl getProductoDao();

    public static Connection getDAOFactory(DBType typeDB) {
        switch (typeDB) {
            case MYSQL : {
                return ConnectionHelper.getInstance();
            }
            case ORACLE: return null;
            case DERBY: return null;
            default: return null;
        }
    }
}

