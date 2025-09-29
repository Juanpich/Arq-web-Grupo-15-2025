import Repository.impl.CarreraRepositoryImpl;
import Repository.impl.EstudianteCarreraRepositoryImpl;
import Repository.impl.EstudianteRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();
        carreraRepository.insertarDesdeCSV();
        EstudianteRepositoryImpl  estudianteRepository = new EstudianteRepositoryImpl();
        estudianteRepository.insertarDesdeCSV();
        EstudianteCarreraRepositoryImpl eCarreraRepository = new EstudianteCarreraRepositoryImpl();
        eCarreraRepository.insertarDesdeCSV();

        System.out.println(estudianteRepository.obtenerEstudiantes());

        System.out.println(estudianteRepository.estudiantesByGenero("Female"));
    }
}
