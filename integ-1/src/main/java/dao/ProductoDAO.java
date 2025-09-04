package dao;
import java.sql.Connection;

import entity.Producto;

import org.apache.commons.csv.*;


import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

public class ProductoDAO {
    private Connection conn;
    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertarDatosCsv(){
        try{
            ArrayList<Producto> productos = new ArrayList<Producto>();
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("src/main/resources/productos.csv"));
            for(CSVRecord row: parser) {
                productos.add(new Producto( Integer.parseInt(row.get("idProducto")), (row.get("nombre")), Float.parseFloat(row.get("valor"))));
            }
            this.insertarDatos(productos);
            System.out.println("Datos Cargados con Exito! Producto");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void insertarDatos(ArrayList<Producto> productos){
        String sql = "INSERT INTO Producto (idProducto,nombre,valor) VALUES (?,?,?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            for (Producto p : productos) {
                ps.setInt(1, p.getId());
                ps.setString(2, p.getNombre());
                ps.setFloat(3, p.getValor());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }catch(Exception e){
            System.out.println(e);
        }finally{
            try {
                if (ps != null)
                    ps.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}