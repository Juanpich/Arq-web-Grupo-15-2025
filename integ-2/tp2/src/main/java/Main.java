import DTO.CarreraConInscriptosDTO;
import DTO.CarreraReporteDTO;
import Repository.impl.CarreraRepositoryImpl;
import Repository.impl.EstudianteCarreraRepositoryImpl;
import Repository.impl.EstudianteRepositoryImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();
        //carreraRepository.insertarDesdeCSV();
        EstudianteRepositoryImpl  estudianteRepository = new EstudianteRepositoryImpl();
        //estudianteRepository.insertarDesdeCSV();
        EstudianteCarreraRepositoryImpl eCarreraRepository = new EstudianteCarreraRepositoryImpl();
        //33eCarreraRepository.insertarDesdeCSV();
        System.out.println("Buscar un estudiante segun su LU");
        System.out.println(estudianteRepository.obtenerEstudianteSegunLU(72976));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Obtener las carreras con la cantidad de inscriptos");
        List<CarreraConInscriptosDTO> lit= carreraRepository.consultarCarrerasConInscriptos();
        for (CarreraConInscriptosDTO c : lit) {
            System.out.println(c);
        }
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Reporte de las carreras");
        List<CarreraReporteDTO> carr=carreraRepository.consultarReporteCarrera();
        for(CarreraReporteDTO c:carr){
            System.out.println(c);
        }
        System.out.println("------------------------------------------------------------------------------------");


    }
}
