package Repository;

import Factory.JPAUtil;
import Modelo.Carrera;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

import java.io.FileReader;

public class CarreraRepositoryImpl {
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/carrera.csv"))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carr = new Carrera(Integer.parseInt(linea[0]), linea[1], Integer.parseInt(linea[2]));
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
