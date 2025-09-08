package dao;

import DTO.ProductoDTO;
import entity.Producto;

import java.util.ArrayList;

public interface ProductoDAO {

    public ProductoDTO getRecaudacion();

    public void insertarDatos(ArrayList<Producto> productos);

    public void insertarDatosCsv();

    public ArrayList<Producto> getProductos();

}
