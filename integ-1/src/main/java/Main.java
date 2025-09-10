import dao.impl.ClienteDAOImpl;
import dao.impl.FacturaDAOImpl;
import dao.impl.Factura_productoDAOImpl;
import dao.impl.ProductoDAOImpl;
import factory.AbstractFactory;
import factory.DBType;
import factory.DaoFactory;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = AbstractFactory.getDAOFactory(DBType.MYSQL);
        DaoFactory df = new DaoFactory(conn);
        ClienteDAOImpl cliente = df.getClienteDao();
        FacturaDAOImpl factura = df.getFacturaDAO();
        ProductoDAOImpl producto = df.getProductoDao();
        Factura_productoDAOImpl fpd = df.getFactura_ProductoDao();
        System.out.println("Insertar Datos desde CSV");
        cliente.insertarDatosCsv();
        factura.insertarDatosCsv();
        producto.insertarDatosCsv();
        fpd.insertarDesdeCsv();
        System.out.println("Mostrar datos cargados");
        System.out.println("Datos Cliente");
        System.out.println(cliente.SelectClientes());
        System.out.println("-------------------------------------");
        System.out.println("Mostrar datos factura");
        System.out.println(factura.getFacturas());
        System.out.println("-------------------------------------");
        System.out.println("Mostrar datos producto");
        System.out.println(producto.getProductos());
        System.out.println("-------------------------------------");
        System.out.println("Mostrar datos factura producto");
        System.out.println(fpd.getFacturas_productos());
        System.out.println("-------------------------------------");
        System.out.println("Datos del ej 3 ( Producto que mas recaudo )");
        System.out.println(producto.getRecaudacion());
        System.out.println("-------------------------------------");
        System.out.println("Datos del ej 4 ( Top 5 de clientes a los que mas se le facturó )");
        System.out.println(cliente.getClientesMasFacturados());




    }
}
