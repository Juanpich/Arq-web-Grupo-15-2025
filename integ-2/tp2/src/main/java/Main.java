import DTO.EstudianteFiltradioDTO;
import Modelo.Carrera;
import Modelo.Estudiante;
import Repository.impl.CarreraRepositoryImpl;
import Repository.impl.EstudianteCarreraRepositoryImpl;
import Repository.impl.EstudianteRepositoryImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //LEER DATOS DE LOS CSV
        CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();
        //carreraRepository.insertarDesdeCSV();
        EstudianteRepositoryImpl  estudianteRepository = new EstudianteRepositoryImpl();
        //estudianteRepository.insertarDesdeCSV();
        EstudianteCarreraRepositoryImpl eCarreraRepository = new EstudianteCarreraRepositoryImpl();
        //eCarreraRepository.insertarDesdeCSV();

        //EJERCICIOS
        System.out.println("Ejercicio A:");
        //System.out.println(estudianteRepository.insertarEstudiante());

        System.out.println("Ejercicio B:");
        //System.out.println(eCarreraRepository.insertarInscripcion());

        System.out.println("Ejercicio C:");
        //System.out.println(estudianteRepository.obtenerEstudiantes());

        System.out.println("Ejercicio D:");
        //INSERTALO JUAN

        System.out.println("Ejercicio E:");
        //System.out.println(estudianteRepository.estudiantesByGenero("female"));

        System.out.println("Ejercicio F:");
        //INSERTALO JUAN

        System.out.println("Ejercicio g:");
        Carrera c1 = carreraRepository.carreraByID(1);
        //System.out.println(c1.getId_carrera());
        System.out.println(estudianteRepository.estudiantesByCarrera(c1, "Rauch"));

        List<EstudianteFiltradioDTO> est= estudianteRepository.estudiantesByCarrera(c1, "Rauch");
        for (EstudianteFiltradioDTO estudiante : est) {
            System.out.println(estudiante);
        }
        //EJERCICIO 3
    }
}