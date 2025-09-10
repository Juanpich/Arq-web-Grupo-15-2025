package dao.impl;

import dao.Factura_productoDAO;
import entity.Factura_producto;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.sql.*;
import org.apache.commons.csv.*;

public class Factura_productoDAOImpl implements Factura_productoDAO {
    private Connection conn;
    public Factura_productoDAOImpl(Connection conn){
        this.conn = conn;
    }
      public ArrayList<Factura_producto> getFacturas_productos(){
        ArrayList<Factura_producto> facturas = new ArrayList<>();
        String select = "SELECT * FROM Factura_Producto";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(select);
            rs = ps.executeQuery();
            while(rs.next()){
                facturas.add(new Factura_producto(rs.getInt("idFactura"), rs.getInt("idProducto"), rs.getInt("cantidad")));
            }
        }catch (Exception e){
            System.err.println("Error al consultar facturas");
        }finally {
            try {
                if(ps!=null)
                    ps.close();
                conn.commit();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion");
            }
        }
        return facturas;
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
            System.err.println("Error al insertar datos de la Factura producto" + e);
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
