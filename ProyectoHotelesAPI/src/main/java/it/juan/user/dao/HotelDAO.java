package it.juan.user.dao;

import java.util.List;

import it.juan.user.entity.Hotel;
public interface HotelDAO {
    public List<Hotel> findAll();

    public Hotel findById(int idHotel);

    public void save(Hotel hotel);

    public void deleteById(int idHotel);

    List<Hotel> findByCategoria(String categoria);
}
