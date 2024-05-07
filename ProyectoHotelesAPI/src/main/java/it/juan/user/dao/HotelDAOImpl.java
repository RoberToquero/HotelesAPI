package it.juan.user.dao;

import javax.persistence.EntityManager;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.juan.user.entity.Hotel;
@Repository
public class HotelDAOImpl implements HotelDAO{
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Hotel> findAll() {
        /*
         Así de fácil es obtener la clase de implementación subyacente de Hibernate
          cuando se trabaja con JPA EntityManager con unwrap
         */

        Session currentSession = entityManager.unwrap(Session.class);
/*
        Si no quisieramos utilizar hibernate y usar JPA EntityManager por tanto la anterior linea nos sobraria
        EntityManager tiene una forma de hacer consultas
            EntityManager.createQuery(String, Class)
        es parte de JPA al igual que TypedQuery.getResultList() .
        Por tanto la llamada a EntityManager.unwrap() se puede eliminar y reemplazar con entityManager
         */

        //Query<User> theQuery = currentSession.createQuery("from User", User.class);
        Query<Hotel> theQuery = currentSession.createQuery("SELECT u from Hotel u", Hotel.class);

        List<Hotel> hoteles = theQuery.getResultList();

        return hoteles;

    }
    @Override
    public Hotel findById(int idHotel) {
        Session currentSession = entityManager.unwrap(Session.class);

        Hotel hotel = currentSession.get(Hotel.class, idHotel);

        return hotel;
    }

    @Override
    public void save(Hotel hotel) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction t = currentSession.beginTransaction();
        /*
saveOrUpdate () , como su nombre lo indica, funciona como save() o update() en función del campo ID presente en la entidad o no. En la mayoría de los casos, es el método preferido para save() .

    Si el ID no está presente, se llama a save() .
    Si el ID está presente, se llama a update() .

 */

        currentSession.saveOrUpdate(hotel);
        t.commit();
        currentSession.close();

    }

    @Override
    public void deleteById(int idHotel) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction t = currentSession.beginTransaction();
        //forma facil
        //User user=findById(id);
        //currentSession.delete(user);
        //otra forma utilizando sentencias HQL DE hibernate

        Query theQuery = currentSession.createQuery("delete from Hotel u where id IN (:idHotel)");

        theQuery.setParameter("idHotel", idHotel);
        theQuery.executeUpdate();
        t.commit();
        currentSession.close();

    }

    @Override
    public List<Hotel> findByCategoria(String categoria) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Hotel> query = currentSession.createQuery("FROM Hotel WHERE categoria = :categoria", Hotel.class);
        query.setParameter("categoria", categoria);

        return query.getResultList();
    }

}
