package dao;

import entity.Factura_producto;

import java.util.ArrayList;

public interface Factura_productoDAO {

    public ArrayList<Factura_producto> getFacturas_productos();

    public void insertarDesdeCsv();

    public void insertarDatos(ArrayList<Factura_producto> elementos);

}
