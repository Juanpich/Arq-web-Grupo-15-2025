package Repository.impl;

import DTO.CarreraConInscriptosDTO;
import DTO.CarreraReporteDTO;
import Factory.JPAUtil;
import Modelo.Carrera;
import Repository.CarreraRepository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

    //recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    public List<CarreraConInscriptosDTO> consultarCarrerasConInscripos() {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT new DTO.CarreraConInscriptosDTO(CAST(count(i.id_estudiante) AS integer), c.carrera, c.duracion) FROM Carrera c JOIN c.Inscriptos i GROUP BY c.id_carrera ORDER BY COUNT(i.id_estudiante) DESC");
        List<CarreraConInscriptosDTO> carreras = query.getResultList();
        return carreras;
    }

    /*Generar un reporte de las carreras, que para cada carrera incluya información de los
      inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
      los años de manera cronológica.*/
    public List<CarreraReporteDTO> consultarReporteCarrera() {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT new DTO.CarreraReporteDTO(c.carrera, CAST(count(i.inscripcion) AS INTEGER ), CAST(SUM(CASE WHEN i.graduacion <> 0THEN 1 ELSE 0 END) AS INTEGER), i.inscripcion) FROM Carrera c JOIN c.Inscriptos i GROUP BY c.id_carrera, i.inscripcion  ORDER BY c.carrera ASC, i.inscripcion DESC");
        List<CarreraReporteDTO> reporte = query.getResultList();
        return reporte;
    }
    public Carrera carreraByID(int id){
        EntityManager em = JPAUtil.getEntityManager();
        String jpl = "SELECT c FROM Carrera c WHERE id_carrera = : id";
        Query query = em.createQuery(jpl).setParameter("id", id);
        Carrera carr = (Carrera) query.getSingleResult();
        return carr;
    }
}
