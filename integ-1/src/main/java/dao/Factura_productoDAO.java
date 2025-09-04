package dao;

import entity.Factura_producto;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.sql.*;
import org.apache.commons.csv.*;

public class Factura_productoDAO {
    private Connection conn;
    public Factura_productoDAO(Connection conn){
        this.conn = conn;
    }

    public void insertarDesdeCsv() {
        try {
            ArrayList<Factura_producto> facturas_productos = new ArrayList<Factura_producto>();
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("src/main/resources/facturas-productos.csv"));

            for(CSVRecord row : parser){
                facturas_productos.add(new Factura_producto(Integer.parseInt(row.get("idFactura") ), Integer.parseInt(row.get("idProducto") ), Integer.parseInt(row.get("cantidad") ) ) );
            }
            insertarDatos(facturas_productos);

        } catch (IOException e) {
            System.err.println("Error al insertar datos" + e);
        }
    }

    public void insertarDatos(ArrayList<Factura_producto> elementos) {
        String insert = " INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?) ";


        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            for (Factura_producto facProd : elementos) {
                ps.setInt(1, facProd.getIdFactura());
                ps.setInt(2, facProd.getIdProducto());
                ps.setInt(3, facProd.getCantidad());
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
            System.out.println("datos de factura producto cargados");
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de factura producto" + e);

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println(e);

            }

        }

    }

}
