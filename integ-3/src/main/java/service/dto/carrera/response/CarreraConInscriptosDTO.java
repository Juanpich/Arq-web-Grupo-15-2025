package service.dto.carrera.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.Carrera;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarreraConInscriptosDTO {
    private int cant;
    private String nombreCarrera;
    private int duracion;

    public CarreraConInscriptosDTO(Carrera carr) {
        this.nombreCarrera = carr.getCarrera();
        this.duracion = carr.getDuracion();
        this.cant = carr.getInscriptos().size(); //hay que revisar esto JUANPIIIII
    }
}