package dao;

import DTO.ClienteDTO;
import entity.Cliente;

import java.util.ArrayList;

public interface ClienteDAO {
    public ArrayList<Cliente> SelectClientes();

    public void insertarDatosCsv();

    public void insertarDatos( ArrayList<Cliente> clientes);

    public ArrayList<ClienteDTO> getClientesMasFacturados();
}


