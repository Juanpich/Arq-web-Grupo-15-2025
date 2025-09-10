package dao.impl;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

import DTO.ClienteDTO;
import dao.ClienteDAO;
import entity.Cliente;
import org.apache.commons.csv.*;

public class ClienteDAOImpl implements ClienteDAO {
    private Connection conn;

    public ClienteDAOImpl(Connection connection) {
        this.conn = connection;
    }
    //Traer todos los clientes
    public ArrayList<Cliente> SelectClientes(){
         String query = "SELECT * " +
                "FROM Cliente ";
        Integer id = null;
        String nombre = null;
        String email = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultados=null;
        ArrayList<Cliente> clientes = null;

        try{
            prepareStatement = conn.prepareStatement(query);
            resultados = prepareStatement.executeQuery();
            clientes = new ArrayList<>();
            while(resultados.next()){
                id = resultados.getInt("idCliente");
                nombre = resultados.getString("nombre");
                email = resultados.getString("email");

                //Por cada resultado, instanciamos un cliente
                Cliente cliente = new Cliente (id,nombre,email);
                clientes.add(cliente);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultados != null) {
                    resultados.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
    //Leer datos desde el CSV
    public void insertarDatosCsv(){
        try{
            ArrayList<Cliente> clientes = new ArrayList<Cliente>();
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new
                    FileReader("src/main/resources/clientes.csv"));

            for(CSVRecord linea: parser){
                clientes.add(new Cliente(Integer.parseInt(linea.get("idCliente")), linea.get("nombre"), linea.get("email")));

            
            }
            insertarDatos(clientes);

        }catch(Exception e){//idCliente,nombre,email
            e.printStackTrace();
        }
    }
    public void insertarDatos( ArrayList<Cliente> clientes) {
        String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";


        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            for (Cliente cl : clientes) {
                ps.setInt(1, cl.getId());
                ps.setString(2, cl.getNombre());
                ps.setString(3, cl.getEmail());
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
            System.out.println("Clientes cargados");
        } catch (SQLException e) {
            System.out.println(e);;

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
    public ArrayList<ClienteDTO> getClientesMasFacturados(){
        String sql = "SELECT c.nombre, c.email, SUM(p.valor*fp.cantidad) AS importeFacturado " +
                "FROM Cliente c JOIN Factura f ON c.idCliente = f.idCliente " +
                "JOIN Factura_Producto fp ON f.idFactura = fp.idFactura " +
                "JOIN Producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente " +
                "ORDER BY importeFacturado DESC " +
                "LIMIT 5;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<ClienteDTO> clientesDTO = null;
        
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            clientesDTO = new ArrayList<>();
            while(rs.next()){
                clientesDTO.add(new ClienteDTO (rs.getString("nombre"), rs.getString("email"), rs.getFloat("importeFacturado")));
            }
            
    } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                    ps.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return clientesDTO;
    }
}
