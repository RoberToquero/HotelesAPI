package it.juan.user.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.juan.user.entity.Habitacion;
import it.juan.user.entity.Hotel;
import it.juan.user.entity.User;
import it.juan.user.service.HotelService;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Hoteles y habitaciones", description = "Lista de hoteles con sus respectivas habitaciones")
@RequestMapping("/api_hoteles") //esta sera la raiz de la url, es decir http://127.0.0.1:5000/api_hoteles/

public class HotelRestController {

    //Inyectamos el servicio para poder hacer uso de el
    @Autowired
    private HotelService hotelService;

    //PARA EL JWT al meter user y password en params
    //http://127.0.0.1:5000/api_hoteles/user?user=juan&password=juan
    //Luego escribir en el Header autorizacion y el token que te da para poder realizar los métodos
    @Operation(summary = "Genera un JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genera un Bearer", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PostMapping("user")
    public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        if ((username.equals("juan")) && (pwd.equals("juan"))) {
            System.out.println("Me crea el token");
            String token = getJWTToken(username);
            User user = new User();
            user.setUser(username);
            user.setPwd(pwd);
            user.setToken(token);

            return user;
        } else

            return null;

    }
    //Utilizamos el método getJWTToken(...) para construir el token,
    // delegando en la clase de utilidad Jwts que incluye información sobre su expiración
    // y un objeto de GrantedAuthority de Spring que, como veremos más adelante,
    // usaremos para autorizar las peticiones a los recursos protegidos.

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    /*Este método se hará cuando por una petición GET (como indica la anotación) se llame a la url
    http://127.0.0.1:5000/api_hoteles/hotel*/
    @Operation(summary = "Obtiene el listado de hoteles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de hoteles",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Hotel.class)))),
    })
    @GetMapping("/hotel")
    public List<Hotel> findAll(){
        //retornará todos los usuarios
        return hotelService.findAll();
    }

    /*Este método se hará cuando por una petición GET (como indica la anotación) se llame a la url + el id de un usuario
    http://127.0.0.1:5000/api_hoteles/hotel/1 */
    @Operation(summary = "Obtiene un hotel determinado por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el hotel", content = @Content(schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "El hotel no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/hotel/{idHotel}")
    public Hotel getHotel(@PathVariable int idHotel){
        Hotel hotel = hotelService.findById(idHotel);

        if(hotel == null) {
            throw new RuntimeException("Hotel id not found -"+idHotel);
        }
        //retornará al usuario con id pasado en la url
        return hotel;
    }

    /*Este método se hará cuando por una petición POST (como indica la anotación) se llame a la url
    http://127.0.0.1:5000/api_hoteles/hotel/  */
    @Operation(summary = "Registra un nuevo hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el hotel", content = @Content(schema = @Schema(implementation = Hotel.class)))
    })
    @PostMapping("/hotel")
    public Hotel addHotel(@RequestBody Hotel hotel) {
        hotel.setIdHotel(0);

        //Este metodo guardará al usuario enviado
        hotelService.save(hotel);

        return hotel;

    }
    /*Este método se hará cuando por una petición PUT (como indica la anotación) se llame a la url
    http://127.0.0.1:5000/api_hoteles/hotel/  */
    @Operation(summary = "Modifica un hotel en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el hotel", content = @Content(schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "El hotel no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })

    @PutMapping("/hotel")
    public Hotel updateHotel(@RequestBody Hotel hotel) {

        hotelService.save(hotel);

        //este metodo actualizará al usuario enviado

        return hotel;
    }

    /*Este método se hará cuando por una petición DELETE (como indica la anotación) se llame a la url + id del usuario
    http://127.0.0.1:5000/api_hoteles/hotel/1  */
    @Operation(summary = "Elimina un hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el hotel", content = @Content(schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "El hotel no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })

    @DeleteMapping("hotel/{idHotel}")
    public String deteteHotel(@PathVariable int idHotel) {

        Hotel hotel = hotelService.findById(idHotel);

        if(hotel == null) {
            throw new RuntimeException("Hotel id not found -"+idHotel);
        }

        hotelService.deleteById(idHotel);

        //Esto método, recibira el id de un hotel por URL y se borrará de la bd.
        return "Deleted Hotel id - "+idHotel;
    }
    //Hoteles por categoria
    //api_hoteles/hotelesPorCategoria?categoria=4
    @Operation(summary = "Obtiene un hotel filtrado por su categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el hotel", content = @Content(schema = @Schema(implementation = Hotel.class))),
            @ApiResponse(responseCode = "404", description = "El hotel no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/hotelesPorCategoria")
    public List<Hotel> getHotelesPorCategoria(@RequestParam String categoria) {
        List<Hotel> hoteles=hotelService.findByCategoria(categoria);
        return hoteles;
    }

    // Métodos relacionados con Habitacion


    //http://127.0.0.1:5000/api_hoteles/habitacion
    @Operation(summary = "Obtiene el listado de habitaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de habitaciones",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Habitacion.class)))),
    })
    @GetMapping("/habitacion")
    public List<Habitacion> findAllHabitaciones() {
        return hotelService.findAllHabitaciones();
    }

    //http://127.0.0.1:5000/api_hoteles/habitacion/1
    @Operation(summary = "Obtiene una habitacion determinada por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la habitacion", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitacion no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/habitacion/{idHabitacion}")
    public Habitacion getHabitacion(@PathVariable int idHabitacion) {
        Habitacion habitacion = hotelService.findHabitacionById(idHabitacion);
        if (habitacion == null) {
            throw new RuntimeException("Habitacion id not found - " + idHabitacion);
        }
        return habitacion;
    }

    //http://127.0.0.1:5000/api_hoteles/habitacion
    @Operation(summary = "Registra una nueva habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra la habitación", content = @Content(schema = @Schema(implementation = Habitacion.class)))
    })
    @PostMapping("/habitacion")
    public Habitacion addHabitacion(@RequestBody Habitacion habitacion) {
        habitacion.setIdHabitacion(0);
        hotelService.saveHabitacion(habitacion);
        return habitacion;
    }

    //http://127.0.0.1:5000/api_hoteles/habitacion
    @Operation(summary = "Modifica la habitación en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la habitación", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitación no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/habitacion")
    public Habitacion updateHabitacion(@RequestBody Habitacion habitacion) {
        hotelService.saveHabitacion(habitacion);
        return habitacion;
    }

    //http://127.0.0.1:8080/api_hoteles/habitacion/1
    @Operation(summary = "Elimina una habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la habitación", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitación no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/habitacion/{idHabitacion}")
    public String deleteHabitacion(@PathVariable int idHabitacion) {
        Habitacion habitacion = hotelService.findHabitacionById(idHabitacion);
        if (habitacion == null) {
            throw new RuntimeException("Habitacion id not found - " + idHabitacion);
        }
        hotelService.deleteHabitacionById(idHabitacion);
        return "Deleted habitacion id - " + idHabitacion;
    }

    @Operation(summary = "Obtiene una habitacion determinada por el id del hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la habitacion", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitacion no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/hotel/{idHotel}/habitaciones")
    public List<Habitacion> getHabitacionesByHotelId(@PathVariable int idHotel) {
        return hotelService.findHabitacionesByHotelId(idHotel);
    }

    @Operation(summary = "Obtiene una habitacion según su capacidad y su ocupación," +
            " si la ocupacion es==1 está ocupada y si es cero está libre. Ordenadas de menor a mayor precio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la habitacion", content = @Content(schema = @Schema(implementation = Habitacion.class))),
            @ApiResponse(responseCode = "404", description = "La habitacion no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })

    ///habitacionesFiltradas?capacidad=valor&ocupada=valor
    @GetMapping("/habitacionesFiltradas")
    public List<Habitacion> getHabitacionesFiltradas(@RequestParam int capacidad, @RequestParam int ocupada) {
        List<Habitacion> habitaciones = hotelService.findByCapacidadAndOcupadaOrderByPrecioNocheAsc(capacidad, ocupada);

        if (habitaciones == null) {
            throw new RuntimeException("Habitacion not found");
        }
        return habitaciones;
    }


}
