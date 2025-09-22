import Modelo.Carrera;
import Repository.CarreraRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();
        carreraRepository.insertarDesdeCSV();
    }
}
