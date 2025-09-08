package factory;

import dao.impl.ClienteDAOImpl;
import dao.impl.FacturaDAOImpl;
import dao.impl.Factura_productoDAOImpl;
import dao.impl.ProductoDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory extends AbstractFactory {

    Connection conn = ConnectionHelper.getInstance();

    public DaoFactory(Connection conn) {
        this.conn = conn;
    }

    @Override
    public ClienteDAOImpl getClienteDao() {
        return new ClienteDAOImpl(conn);
    }

    @Override
    public Factura_productoDAOImpl getFactura_ProductoDao() {
        return new Factura_productoDAOImpl(conn);
    }

    @Override
    public FacturaDAOImpl getFacturaDAO() {
        return new FacturaDAOImpl(conn);
    }

    @Override
    public ProductoDAOImpl getProductoDao() {
        return new ProductoDAOImpl(conn);
    }

    public void closeConnection(){
        try{
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
