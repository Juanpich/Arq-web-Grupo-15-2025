package Repository.impl;

import Factory.JPAUtil;
import Modelo.Estudiante;
import Modelo.EstudianteCarrera;
import Repository.EstudianteRepository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository {

    public void insertarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/estudiantes.csv"))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante estudiante = new Estudiante(Integer.parseInt(linea[0]), linea[1], linea[2], linea[4], Integer.parseInt(linea[3]), linea[5], Integer.parseInt(linea[6]), new ArrayList<>());
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
    public void insertarEstudiante(Estudiante es){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(es);
        em.getTransaction().commit();
        em.close();
    }
    public List<Estudiante> obtenerEstudiantes(){
        EntityManager em = JPAUtil.getEntityManager();
        String jpql = "SELECT e FROM Estudiante e ORDER BY nombre ASC";
        Query query = em.createQuery(jpql);
        List<Estudiante> estudiantes = query.getResultList();
        return estudiantes;
    }
    public List<Estudiante> estudiantesByGenero(String genero){
        EntityManager em = JPAUtil.getEntityManager();
        String jplq =  "SELECT e FROM Estudiante e WHERE e.genero = :genero";
        Query query = em.createQuery(jplq).setParameter("genero", genero);
        List<Estudiante> estudiantes = query.getResultList();
        return estudiantes;
    }
}

