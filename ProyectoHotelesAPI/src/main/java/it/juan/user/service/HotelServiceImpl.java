package it.juan.user.service;

import it.juan.user.dao.HabitacionDAO;
import it.juan.user.entity.Hotel;
import it.juan.user.entity.Habitacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.juan.user.dao.HotelDAO;
@Service
public class HotelServiceImpl implements HotelService {
    //Objetos de DAO para llamar a los métodos que interactúan con la base de datos
    private final HotelDAO hotelesDAO;
    private final HabitacionDAO habitacionDAO;

    // @Autowired para inyectar nuestro DAO y hacer uso de él:
    @Autowired
    public HotelServiceImpl(HotelDAO hotelesDAO, HabitacionDAO habitacionDAO) {
        this.hotelesDAO = hotelesDAO;
        this.habitacionDAO = habitacionDAO;
    }

    @Override
    public List<Hotel> findAll() {

        return hotelesDAO.findAll();
    }

    @Override
    public Hotel findById(int idHotel) {
        Hotel hotel = hotelesDAO.findById(idHotel);
        return hotel;
    }

    @Override
    public void save(Hotel hotel) {
        hotelesDAO.save(hotel);

    }

    @Override
    public void deleteById(int idHotel) {
        hotelesDAO.deleteById(idHotel);
    }

    @Override
    public List<Hotel> findByCategoria(String categoria) {
        return hotelesDAO.findByCategoria(categoria);
    }

    @Override
    public List<Habitacion> findAllHabitaciones() {
        return habitacionDAO.findHabitacionAll();
    }

    @Override
    public Habitacion findHabitacionById(int idHabitacion) {
        Habitacion habitacion = habitacionDAO.findByIdHabitacion(idHabitacion);
        return habitacion;
    }

    @Override
    public void saveHabitacion(Habitacion habitacion) {
        habitacionDAO.saveHabitacion(habitacion);

    }

    @Override
    public void deleteHabitacionById(int idHabitacion) {
        habitacionDAO.deleteHabitacionById(idHabitacion);
    }

    @Override
    public List<Habitacion> findHabitacionesByHotelId(int idHotel) {
        Hotel hotel =hotelesDAO.findById(idHotel);
        if(hotel == null){
            throw new RuntimeException("Hotel id not found " + idHotel);
        }
        return hotel.getHabitaciones();
    }

    @Override
    public List<Habitacion> findByCapacidadAndOcupadaOrderByPrecioNocheAsc(int capacidad, int ocupada) {
        return habitacionDAO.findByCapacidadAndOcupadaOrderByPrecioNocheAsc(capacidad, ocupada);
    }



}
