package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Destinos;
import com.SerendipityTravels.app.Entity.PaquetesTuristicos;
import com.SerendipityTravels.app.Entity.Reservas;
import com.SerendipityTravels.app.Entity.Usuarios;
import com.SerendipityTravels.app.Repository.ReservasRepository;
import com.SerendipityTravels.app.Repository.DestinosRepository;
import com.SerendipityTravels.app.Repository.PaquetesRepository;
import com.SerendipityTravels.app.Repository.UsuariosRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private DestinosRepository destinosRepository;

    @Autowired
    private PaquetesRepository paquetesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Método para guardar la reserva
 /*   @PostMapping("/guardar")
    public String guardarReserva(@RequestBody Reservas reserva) {
        try {
            // Debug: Mostrar datos recibidos
            System.out.println("Datos de la reserva recibida: " + reserva.toString());

            // Establecer la fecha actual como fecha de reserva
            reserva.setFechaReserva(LocalDate.now());

            // Guardar la reserva en la base de datos
            reservasRepository.save(reserva);
            return "Reserva confirmada con éxito.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error al guardar la reserva.";
        }
    }*/
    
    @GetMapping("/ver")
    public String verReservas(Model model, HttpSession session) {
        // Obtener la cédula del usuario desde la sesión
    	Usuarios usuarioSesion = (Usuarios) session.getAttribute("usuarioSesion");
        Long cedulaUsuario = usuarioSesion.getCedula();
        
  /*      if (cedulaUsuario == null) {
            // Si no está en sesión, redirigir al login
            return "redirect:/index"; // o donde quieras redirigir si no hay sesión
        }*/

        // Buscar las reservas del usuario por cédula
        //List<Reservas> reservas = reservasRepository.findAll();
        List<Reservas> reservas = reservasRepository.findByUsuarioCedulaAndEstOrderByFechaReservaDesc(cedulaUsuario, 1);
        model.addAttribute("reservas", reservas);  // Pasar las reservas al modelo

        return "verReservas"; // Nombre de la vista (HTML) que mostrarás
    }
    
    @GetMapping("/buscarPorUsuario/{cedula}")
    public Reservas buscarReservaPorUsuario(@PathVariable long cedula) {
        return reservasRepository.findFirstByUsuarioCedulaOrderByIdDesc(cedula)
                .orElseThrow(() -> new RuntimeException("No se encontraron reservas para el usuario con cédula: " + cedula));
    }
    @PostMapping("/crear")
    public String crearReserva(@RequestBody Reservas reserva) {
        try {
            // Depuración: Verificar datos recibidos
            System.out.println("Datos recibidos:");
            System.out.println("ID Destino: " + reserva.getIdDestino());
            System.out.println("ID Paquete: " + reserva.getIdPaquete());
            System.out.println("Cédula: " + reserva.getCedula());

            // Buscar entidades relacionadas
            Destinos destino = destinosRepository.findById(reserva.getIdDestino())
                    .orElseThrow(() -> new RuntimeException("Destino no encontrado"));
            PaquetesTuristicos paquete = paquetesRepository.findById(reserva.getIdPaquete())
                    .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
            Usuarios usuario = usuariosRepository.findByCedula(reserva.getCedula());

            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado con cédula: " + reserva.getCedula());
            }

            // Asignar relaciones al objeto reserva
            reserva.setDestino(destino);
            reserva.setPaquete(paquete);
            reserva.setUsuario(usuario);

            // Establecer la fecha de reserva
            reserva.setFechaReserva(LocalDate.now());

            // Guardar en la base de datos
            reservasRepository.save(reserva);

            return "Reserva creada exitosamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al crear la reserva: " + e.getMessage();
        }
    }
    
    @GetMapping("/listar")
    public String listarReservas(Model model) {
        List<Reservas> listaReservas = reservasRepository.findAll();
        model.addAttribute("listaReservas", listaReservas);
        return "listarReservas"; // Vista para mostrar la lista
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        List<Destinos> listaDestinos = destinosRepository.findAll();
        List<PaquetesTuristicos> listaPaquetes = paquetesRepository.findAll();
        List<Usuarios> listaUsuarios = usuariosRepository.findAll();

        model.addAttribute("listaDestinos", listaDestinos);
        model.addAttribute("listaPaquetes", listaPaquetes);
        model.addAttribute("listaUsuarios", listaUsuarios);
        model.addAttribute("reserva", new Reservas());
        return "formularioReservas"; // Vista para el formulario
    }

    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable("id") int id, Model model) {
        Reservas reserva = reservasRepository.findById(id).orElse(null);
        if (reserva != null) {
            List<Destinos> listaDestinos = destinosRepository.findAll();
            List<PaquetesTuristicos> listaPaquetes = paquetesRepository.findAll();
            List<Usuarios> listaUsuarios = usuariosRepository.findAll();
            model.addAttribute("listaDestinos", listaDestinos);
            model.addAttribute("listaPaquetes", listaPaquetes);
            model.addAttribute("listaUsuarios", listaUsuarios);
            model.addAttribute("reserva", reserva);
            return "formularioReservas";
        } else {
            return "redirect:/reservas/listar";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable("id") int id) {
        reservasRepository.deleteById(id);
        return "redirect:/reservas/listar";
    }
}
