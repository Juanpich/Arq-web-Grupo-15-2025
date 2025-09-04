package entity;

import lombok.*;

@Data
public class Factura {
    private int idFactura;
    private int idCliente;

    public Factura(int idFactura, int idCliente){
        this.idFactura=idFactura;
        this.idCliente=idCliente;
    }
}
