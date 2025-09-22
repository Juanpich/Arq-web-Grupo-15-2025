package Repository;

import Factory.JPAUtil;
import Modelo.Carrera;
import Modelo.Estudiante;
import Modelo.EstudianteCarrera;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

import java.io.FileReader;

public class EstudianteCarreraRepositoryImpl {
    public void insertarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/estudianteCarrera.csv"))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante e = em.find(Estudiante.class, Integer.parseInt(linea[1]));
                Carrera c = em.find(Carrera.class, Integer.parseInt(linea[2]));
                EstudianteCarrera carr = new EstudianteCarrera(Integer.parseInt(linea[0]),Integer.parseInt(linea[3]),Integer.parseInt(linea[4]),Integer.parseInt(linea[5]),e, c );
                em.persist(carr);
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
