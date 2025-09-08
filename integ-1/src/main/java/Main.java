import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.Factura_productoDAO;
import dao.ProductoDAO;
import factory.AbstractFactory;
import factory.DBType;

public class Main {
    public static void main(String[] args) {
        AbstractFactory bd = AbstractFactory.getDAOFactory(DBType.MYSQL);
        System.out.println("Error en el abstract");
        ClienteDAO cliente = bd.getClienteDao();
        FacturaDAO factura = bd.getFacturaDAO();
        ProductoDAO producto = bd.getProductoDao();
        Factura_productoDAO fpd = bd.getFactura_ProductoDao();
        System.out.println(cliente.SelectClientes());
        System.out.println(factura.getFacturas());
        /*cliente.leerDatos();
        System.out.println("Clientes cargados");
        producto.insertarDatosCsv();
        System.out.println("Productos cargados");
        factura.insertarDatosCsv();
        System.out.println("Facturas cargados");
        fpd.insertarDesdeCsv();
        System.out.println("Facturas productos cargados");*/

        //System.out.println(producto.getRecaudacion());
        //System.out.println(cliente.getClientesMasFacturados());
        System.out.println(producto.getProductos());


    }
}
