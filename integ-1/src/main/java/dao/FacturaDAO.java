package dao;

import entity.Factura;

import java.util.ArrayList;

public interface FacturaDAO {

    public void insertarDatos(ArrayList<Factura> facturas);

    public void insertarDatosCsv();

    public ArrayList<Factura> getFacturas();


}
