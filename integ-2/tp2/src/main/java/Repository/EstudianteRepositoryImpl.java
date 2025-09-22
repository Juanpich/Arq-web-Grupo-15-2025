package Repository;

import Factory.JPAUtil;
import Modelo.Carrera;
import Modelo.Estudiante;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

import java.io.FileReader;
import java.util.ArrayList;

public class EstudianteRepositoryImpl {

    public void insertarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/estudiantes.csv"))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante estudiante = new Estudiante(Integer.parseInt(linea[0]), linea[1], linea[2], linea[3], Integer.parseInt(linea[4]), linea[5], Integer.parseInt(linea[6]), new ArrayList<>());
                em.persist(estudiante);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error al insertar las carreras");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

