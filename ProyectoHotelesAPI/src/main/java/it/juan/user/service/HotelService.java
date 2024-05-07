package it.juan.user.service;

import it.juan.user.entity.Hotel;
import it.juan.user.entity.Habitacion;



import java.util.List;
public interface HotelService {
    // Métodos para Hotel
    public List<Hotel> findAll();

    public Hotel findById(int idHotel);

    public void save(Hotel hotel);

    public void deleteById(int idHotel);

    List<Hotel> findByCategoria(String categoria);


    // Métodos para Habitacion

    List<Habitacion> findAllHabitaciones();

    Habitacion findHabitacionById(int idHabitacion);

    //Salvar habitación, es decir para insertar y modificar

    void saveHabitacion(Habitacion habitacion);

    //Borrar habitacion por ID

    void deleteHabitacionById(int idHabitacion);

    //Listar habitaciones por idHotel

    List<Habitacion> findHabitacionesByHotelId(int idHotel);

    //Listar por capacidad y ocupacion

    List<Habitacion> findByCapacidadAndOcupadaOrderByPrecioNocheAsc(int capacidad, int ocupada);



}
