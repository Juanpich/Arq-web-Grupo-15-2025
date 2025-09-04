package entity;


import lombok.*;

@Data
public class Factura_producto {
    private int idFactura;
    private int idProducto;
    private int cantidad;
    
    public Factura_producto(int idFactura, int idProducto, int cantidad) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }
}
