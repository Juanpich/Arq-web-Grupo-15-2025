package Repository.impl;

import Factory.JPAUtil;
import Modelo.Carrera;
import Repository.CarreraRepository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;

import java.io.FileReader;
import java.util.ArrayList;

public class CarreraRepositoryImpl implements CarreraRepository {
    public void insertarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/carreras.csv"))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carr = new Carrera(Integer.parseInt(linea[0]), linea[1], Integer.parseInt(linea[2]), new ArrayList<>());
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

    public void insertarCarrera (Carrera carr){
      EntityManager em = JPAUtil.getEntityManager();
      em.persist(carr);
      em.getTransaction().commit();
      em.close();
    }

}
