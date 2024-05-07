package it.juan.user.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name="habitacion")
public class Habitacion {
    @Schema(description = "Identificador de la habitación", example = "101", required = true)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_Habitacion")
    private int idHabitacion;
    @Schema(description = "Capacidad de la habitación", example = "2")
    @Column(name="capacidad")
    private int capacidad;
    @Schema(description = "Precio de la habitación por noche", example = "150")
    @Column(name="precio_Noche")
    private int precio;
    @Schema(description = "Desayuno incluido", example = "1")
    @Column(name="incluye_Desayuno")
    private int desayuno;
    @Schema(description = "Habitación ocupada", example = "0")
    @Column(name="ocupada")
    private int ocupada;
    @Schema(description = "Identificador del hotel", example = "1", required = true)
    @ManyToOne
    @JoinColumn(name = "id_Hotel")
    @JsonIgnore
    private Hotel hotel;

    public Habitacion() {
    }

    public Habitacion(int idHabitacion, int capacidad, int precio, int desayuno, int ocupada, Hotel hotel) {
        this.idHabitacion = idHabitacion;
        this.capacidad = capacidad;
        this.precio = precio;
        this.desayuno = desayuno;
        this.ocupada = ocupada;
        this.hotel = hotel;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(int desayuno) {
        this.desayuno = desayuno;
    }

    public int getOcupada() {
        return ocupada;
    }

    public void setOcupada(int ocupada) {
        this.ocupada = ocupada;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    //PARA QUE ME MUESTRE EL IDHOTEL AL BUSCAR UNA HABITACION
    @JsonProperty("idHotel")
    public int getIdHotel() {
        return hotel.getIdHotel();
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", capacidad=" + capacidad +
                ", precio=" + precio +
                ", desayuno=" + desayuno +
                ", ocupada=" + ocupada +
                ", hotel=" +  hotel+
                '}';
    }
}
