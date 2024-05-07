package it.juan.user.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import javax.persistence.*;


    @Entity
    @Table(name="hotel")
    public class Hotel {
        @Schema(description = "Identificador del hotel", example = "1", required = true)
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="id_Hotel")
        private int idHotel;
        @Schema(description = "Nombre del hotel", example = "Paris", required = true)
        @Column(name="nombre")
        private String nombre;
        @Schema(description = "Descripcion del hotel", example = "Centro")
        @Column(name="descripcion")
        private String descripcion;
        @Schema(description = "Número de estrellas del hotel", example = "4")
        @Column(name="categoria")
        private String categoria;
        @Schema(description = "Verifica si tiene piscina", example = "1")
        @Column(name="tiene_piscina")
        private int piscina;
        @Schema(description = "Localidad del hotel", example = "Valladolid")
        @Column(name="localidad")
        private String localidad;
        @Schema(description = "Identificador de las habitaciones", example = "101", required = true)
        @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
        private List<Habitacion> habitaciones;

        public Hotel() {}

        public Hotel(int idHotel, String nombre, String descripcion, String categoria, int piscina, String localidad) {
            this.idHotel = idHotel;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.categoria = categoria;
            this.piscina = piscina;
            this.localidad = localidad;
        }

        public Hotel(int idHotel, String nombre, String descripcion, String categoria, int piscina, String localidad, List<Habitacion> habitaciones) {
            this.idHotel = idHotel;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.categoria = categoria;
            this.piscina = piscina;
            this.localidad = localidad;
            this.habitaciones = habitaciones;
        }

        public int getIdHotel() {
            return idHotel;
        }

        public void setIdHotel(int idHotel) {
            this.idHotel = idHotel;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public int getPiscina() {
            return piscina;
        }

        public void setPiscina(int piscina) {
            this.piscina = piscina;
        }

        public String getLocalidad() {
            return localidad;
        }

        public void setLocalidad(String localidad) {
            this.localidad = localidad;
        }

        public List<Habitacion> getHabitaciones() {
            return habitaciones;
        }

        public void setHabitaciones(List<Habitacion> habitaciones) {
            this.habitaciones = habitaciones;
        }

        @Override
        public String toString() {
            return "Hotel{" +
                    "idHotel=" + idHotel +
                    ", nombre='" + nombre + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    ", categoria='" + categoria + '\'' +
                    ", piscina=" + piscina +
                    ", localidad='" + localidad + '\'' +
                    ", habitaciones=" + habitaciones +
                    '}';
        }
    }
