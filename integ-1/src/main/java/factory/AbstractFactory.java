package factory;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.Factura_productoDAO;
import dao.ProductoDAO;

public abstract class AbstractFactory {
    public abstract ClienteDAO getClienteDao();
    public abstract Factura_productoDAO getFactura_ProductoDao();
    public abstract FacturaDAO getFacturaDAO();
    public abstract ProductoDAO getProductoDao();

    public static AbstractFactory getDAOFactory(DBType typeDB) {
        switch (typeDB) {
            case MYSQL : {
                return MySQLDAOFactory.getInstance();
            }
            case ORACLE: return null;
            case DERBY: return null;
            default: return null;
        }
    }
}

