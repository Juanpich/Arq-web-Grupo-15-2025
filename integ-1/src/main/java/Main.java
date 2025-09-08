import dao.impl.ClienteDAOImpl;
import dao.impl.FacturaDAOImpl;
import dao.impl.ProductoDAOImpl;
import factory.AbstractFactory;
import factory.DBType;
import factory.DaoFactory;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection bd = AbstractFactory.getDAOFactory(DBType.MYSQL);
        DaoFactory df = new DaoFactory(bd);
        ClienteDAOImpl cliente = df.getClienteDao();
        FacturaDAOImpl factura = df.getFacturaDAO();
        ProductoDAOImpl producto = df.getProductoDao();
       // Factura_productoDAO fpd = bd.getFactura_ProductoDao();
        //System.out.println(cliente.SelectClientes());
        //System.out.println(factura.getFacturas());

        System.out.println("Ej 3");
        System.out.println(producto.getRecaudacion());
        System.out.println("Ej 4");
        System.out.println(cliente.getClientesMasFacturados());
        //System.out.println(producto.getProductos());


    }
}
