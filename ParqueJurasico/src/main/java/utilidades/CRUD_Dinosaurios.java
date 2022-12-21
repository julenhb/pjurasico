package utilidades;

import com.example.parquejurasico.Dinosaurio;
import clases.HibernateUtil;
import com.example.parquejurasico.Recinto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CRUD_Dinosaurios {

    static SessionFactory factory = HibernateUtil.getSessionFactory();


    //MeterDinos
    public static void guardarDinosaurio(Dinosaurio dino) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(dino);
            transaction.commit();
            System.out.println("Dino guardado");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }

    //Eliminar Dinos
    //Prácticamente igual que la creación de Dinosaurios pero cambiando la consulta y pasando un parámetro de referencia
    public static void eliminarDinosaurio(String nDino) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Dinosaurio dino = (Dinosaurio) session.createQuery("FROM Dinosaurio where nombre='" + nDino + "'").uniqueResult();
            session.remove(dino);
            transaction.commit();
            System.out.println("Dino eliminado");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }


    //Método para llenar el ComboBox Dietas
    public static void rellenarDieta(ArrayList<String> lista) {
        lista.add("Carnívoro");
        lista.add("Hervíboro");
    }

    //Método para llenar el ComboBox Recinto
    public static void llenarRecinto(ArrayList<String> array) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = (Query) session.createQuery(" FROM Recinto"); //Consulto todos los recintos
        List<Recinto> recintos = query.list(); //Guardo el resultado en una lista
        session.getTransaction().commit();

        for (Recinto recinto : recintos) {
            array.add(recinto.getNombre()); //A partir de la lista creo objetos y me quedo con el nombre del Recinto
        }
        session.close();
    }

    //Buscar un recinto
    public static Recinto buscarRecinto(String nRecinto) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //Paso el nombre del recinto y lo utilizo como filtro en la cláusla WHERE
        Query query = session.createQuery(" FROM Recinto where nombre=:nRecinto");
        query.setParameter("nRecinto", nRecinto);
        //Creo un objeto a través del resultado de la consulta
        Recinto recinto = (Recinto) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return recinto;

    }


    //LLENAR LA LISTA
    public static ObservableList<String> rellenarList(String nRec) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //Paso el nombre del recinto como filtro para el WHERE
        Recinto recinto = (Recinto) session.createQuery("FROM Recinto where nombre='" + nRec + "'").uniqueResult();
        int idR = recinto.getIdRecinto();
        //Obtengo el id del recinto
        Query query1 = session.createQuery("FROM Dinosaurio where recinto='" + idR + "'");
        List<Dinosaurio> dinos = query1.list();
        //En la segunda consulta pido todos los dinosaurios del mismo recinto y los meto en un List
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (Dinosaurio dinosaurio : dinos) {
            lista.add(dinosaurio.getNombre());
        }
        //Como antes, con el bucle voy creando objetos en el bucle y me quedo con su nombre, lo que se
        //verá después en el ListView
        return lista;
    }

    //LLENAR LA TABLA
    public static ObservableList<Dinosaurio> rellenarTabla() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //Pido todos mis dinosaurios
        Query query = session.createQuery("FROM Dinosaurio");
        List<Dinosaurio> dinos = query.list();
        //Los guardo en una lista para después rellenar con objetos un ObservableList
        ObservableList<Dinosaurio> lista = FXCollections.observableArrayList();
        for (Dinosaurio dinosaurio : dinos) {
            lista.add(dinosaurio);
        }
        return lista;
    }

    public static Dinosaurio encontrarDino(String nDino) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Dinosaurio dinosaurio = (Dinosaurio) session.createQuery("FROM Dinosaurio where nombre='" + nDino + "'").uniqueResult();
        //Pido un dinosaurio concreto a través de su nombre (UNIQUE)
        session.getTransaction().commit();
        session.close();
        return dinosaurio;
    }

    /*
    POR LOS SIGUIENTES MÉTODOS DE ABAJO NO HAY UN BOTÓN DE MODIFICAR COMO TAL, ME PARECIÓ MÁS DIVERTIDO
    TRABAJAR ASÍ CON LOS UPDATE EN MI APP
     */

    /*
    Selección natural (QUE NO CRUELDAD, ES EL FLUJO INEVITABLE DE LA VIDA)
    Este método funciona para que, cada vex que un carnívoro coincida con un hervíboro en el mismo recinto,
    el hervíboro muera. Si inserto un carnívoro en el mismo recinto donde hay hervíboros, su campo vivo
    pasará a ser False.

    Si inserto un hervíboro en un recinto con carnívoros, su morirá automáticamente.
     */
    public static void selNatural(String dieta, int id_rec) {
        //Paso como parámetros la dieta del dinosaurio que se va a insertar y su id de recinto
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        ArrayList<Integer> idDinos = new ArrayList<>();
        //Este array será importante para el caso de inserción de hervíboros


        Query query = s.createQuery("FROM Dinosaurio");
        List<Dinosaurio> dinos = query.list();
        //Pido todos los dinos y los guardo en una lista

        //Caso de inserción de CARNÍVOROS
        if (dieta.equalsIgnoreCase("Carnívoro")) {
            for (Dinosaurio dinosaurio : dinos) {
                if (dinosaurio.getRecinto().getIdRecinto() == id_rec && dinosaurio.getDieta().equalsIgnoreCase("Hervíboro")) {
                    //Si el dinosaurio i está en el recinto del carnívoro recién insertado y es hervíboro...:
                    dinosaurio.setVivo(false);
                    s.update(dinosaurio);
                    System.out.println("Hervíboros murieron");
                }
            }
        }

        //Caso de inserción de HERVÍBOROS
        if (dieta.equalsIgnoreCase("Hervíboro")) {
            for (Dinosaurio dinosaurio : dinos) {
                if (dinosaurio.getRecinto().getIdRecinto() == id_rec && dinosaurio.getDieta().equalsIgnoreCase("Carnívoro")) {
                    //Si el dinosaurio i es un carnívoro, se guarda su id en el array que hicimos al principio
                    idDinos.add(dinosaurio.getIdDino());
                }
            }
            for (Dinosaurio dinosaurio : dinos) {
                if (!idDinos.contains(dinosaurio.getIdDino()) && dinosaurio.getRecinto().getIdRecinto() == id_rec) {
                    //DEJO A LOS CARNÍVOROS DEL RECINTO FUERA DE LA CONDICIÓN PARA SÓLO MATAR A LOS HERVÍBOROS
                    dinosaurio.setVivo(false);
                    s.update(dinosaurio);
                    System.out.println("Hervíboros murieron");
                }
            }
            s.getTransaction().commit();
            s.close();
        }
    }




    /*
    TRASLADO DE DINOSAURIOS
    Otra acción lógica de la aplicación es trasladar un dinosaurio de un recinto
    a otro.
     */
    public static void trasladarDinosaurio(Dinosaurio dino, String nRec) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Pido un dinosaurio concreto a través de su nombre (UNIQUE)
            dino.setRecinto(buscarRecinto(nRec));
            //con el método buscarRecinto especifico el recinto al que irá mi dinosaurio
            session.update(dino);
            session.getTransaction().commit();
            System.out.println(dino.getNombre() + " fue trasladado a " + nRec);
            session.close();

    }

    /*

    Estos bonitos lagartos murieron a causa de un meteorito...
    En la saga Final Fantasy, Meteo es un hechizo que lanza meteoros al rival
    En el caso de usar Meteo, todos los dinosaurios morirán
     */
    public static void meteo() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Dinosaurio");
            //Pido todos los Dinosaurios
            List<Dinosaurio> dinos = query.list();
            for (Dinosaurio dinosaurio : dinos) {
                dinosaurio.setVivo(false);
                session.update(dinosaurio);
                //Hago un Update a la BD para que el estado vivo pase a False
            }
            transaction.commit();
            System.out.println("Extinción");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }

    }

    /*
    Más referencias a Final Fantasy...
    No soy un Dios vengativo, como el método de antes mata a todos los dinosaurios,
    este los puede ir reviviendo uno a uno según son seleccionados en el ListView

    En el PrimaryController, el método para llamarlo con el botón se llama Lázaro,
    al igual que el hechizo que se usa para revivir en FF (referencia a la Biblia también...)
     */
    public static void revivir(String nDino) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Dinosaurio dinosaurio = (Dinosaurio) session.createQuery("FROM Dinosaurio where nombre='" + nDino + "'").uniqueResult();
        //Pido un dinosaurio concreto a través de su nombre (UNIQUE)
        dinosaurio.setVivo(true);
        session.update(dinosaurio);
        //Hago un update para revivirlo
        session.getTransaction().commit();
        System.out.println(nDino + " volvió a la vida");
        session.close();

    }

}


