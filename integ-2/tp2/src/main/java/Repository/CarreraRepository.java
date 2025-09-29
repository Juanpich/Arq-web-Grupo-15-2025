package Repository;

import Modelo.Carrera;

public interface CarreraRepository {
    public void insertarDesdeCSV();
    public void insertarCarrera (Carrera carr);
    public Carrera carreraByID(int id);
}
