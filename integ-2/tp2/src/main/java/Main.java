import DTO.CarreraConInscriptosDTO;
import DTO.CarreraReporteDTO;
import DTO.EstudianteFiltradoDTO;
import Modelo.Carrera;
import Repository.impl.CarreraRepositoryImpl;
import Repository.impl.EstudianteCarreraRepositoryImpl;
import Repository.impl.EstudianteRepositoryImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //LEER DATOS DE LOS CSV
        CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();
        //carreraRepository.insertarDesdeCSV();
        EstudianteRepositoryImpl estudianteRepository = new EstudianteRepositoryImpl();
        //estudianteRepository.insertarDesdeCSV();
        EstudianteCarreraRepositoryImpl eCarreraRepository = new EstudianteCarreraRepositoryImpl();
        //eCarreraRepository.insertarDesdeCSV();
        //EJERCICIOS
        System.out.println("Ejercicio A:");
        //Estudiante estudiante = new Estudiante(12345678, "pepe", "perez", "M", 16, "Tandil", 12347, new ArrayList<>());
        //System.out.println(estudianteRepository.insertarEstudiante(estudiante));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Ejercicio B:");
        //EstudianteCarrera ec = new EstudianteCarrera(700,2, 5, 4,estudiante, carrera );
        //System.out.println(eCarreraRepository.insertarInscripcion());
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Ejercicio C:");
        System.out.println(estudianteRepository.obtenerEstudiantes());
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Ejercicio D:");
        System.out.println(estudianteRepository.obtenerEstudianteSegunLU(72976));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Ejercicio E:");
        System.out.println(estudianteRepository.estudiantesByGenero("female"));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Ejercicio F:");
        //System.out.println(carreraRepository.consultarCarrerasConInscriptos());
        List<CarreraConInscriptosDTO> lit = carreraRepository.consultarCarrerasConInscriptos();
        for (CarreraConInscriptosDTO c : lit) {
            System.out.println(c);
        }
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Ejercicio G:");
        Carrera c1 = carreraRepository.carreraByID(1);
        System.out.println(estudianteRepository.estudiantesByCarrera(c1, "Rauch"));

        List<EstudianteFiltradoDTO> est = estudianteRepository.estudiantesByCarrera(c1, "Rauch");
        for (EstudianteFiltradoDTO estudiante : est) {
            System.out.println(estudiante);
        }
        System.out.println("------------------------------------------------------------------------------------");
        //EJERCICIO 3
        List<CarreraReporteDTO> carr = carreraRepository.consultarReporteCarrera();
        for (CarreraReporteDTO c : carr) {
            System.out.println(c);
        }
        System.out.println("------------------------------------------------------------------------------------");

    }
}