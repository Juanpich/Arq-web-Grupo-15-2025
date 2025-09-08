package dao;

import entity.Factura;

import org.apache.commons.csv.*;


import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
public class FacturaDAO {
    private Connection conn;
    public FacturaDAO(Connection conn){
        this.conn=conn;
    }
    public ArrayList<Factura> getFacturas(){
        ArrayList<Factura> facturas = new ArrayList<>();
        String select = "SELECT * FROM Factura";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(select);
            rs = ps.executeQuery();
            while(rs.next()){
                facturas.add(new Factura(rs.getInt("idFactura"), rs.getInt("idCliente")));
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

    public void insertarDatosCsv(){
        try{
            ArrayList<Factura> facturas=new ArrayList<Factura>();
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new
                    FileReader("src/main/resources/facturas.csv"));
            for(CSVRecord columnas: parser) {
                facturas.add(new Factura( Integer.parseInt(columnas.get("idFactura")), Integer.parseInt(columnas.get("idCliente"))));
            }
            this.insertarDatos(facturas);
            System.out.println("datos cargados facturas ");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void insertarDatos(ArrayList<Factura> facturas)  {
        String sql = "INSERT INTO Factura (idFactura,idCliente) VALUES (?,?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            for (Factura f : facturas) {
                ps.setInt(1, f.getIdFactura());
                ps.setInt(2, f.getIdCliente());
                ps.addBatch();//acumula las operaciones sql
            }
            ps.executeBatch();//ejecuta todas las operaciones almacenadas
            conn.commit();
            
        }catch(Exception e){
            System.out.println(e);
        }finally{//cuando finaliza
            try {
                if (ps != null)//si la consulta se ejecuto la cierra
                    ps.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}
