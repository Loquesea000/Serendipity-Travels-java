package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Destinos;
import com.SerendipityTravels.app.Entity.Reservas;
import com.SerendipityTravels.app.Entity.Usuarios;
import com.SerendipityTravels.app.Repository.DestinosRepository;
import com.SerendipityTravels.app.Repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/destinos")
public class DestinosController {

    @Autowired
    private DestinosRepository destinosRepository;

    @Autowired
    private ReservasRepository reservasRepository;

    // Mostrar los destinos disponibles con información del usuario en sesión
    @GetMapping("/listar")
    public String listarDestinos(Model model, HttpSession session) {
        // Recuperar al usuario en sesión
        Usuarios usuarioSesion = (Usuarios) session.getAttribute("usuarioSesion");

        if (usuarioSesion != null) {
            model.addAttribute("cedulaUsuario", usuarioSesion.getCedula()); // Pasar la cédula al modelo
        }

        List<Destinos> listaDestinos = destinosRepository.findAll();
        model.addAttribute("listaDestinos", listaDestinos);
        return "listarDestinos"; // Vista que mostrará los destinos
    }
    
    @GetMapping("/ver")
    public String verDestinos(Model model) {
        List<Destinos> listaDestinos = destinosRepository.findAll(); // Recuperar todos los destinos
        model.addAttribute("listaDestinos", listaDestinos); // Pasar la lista al modelo
        return "verDestinos"; // Nombre de la vista (HTML) que mostrará los destinos
    }

    // Ver detalles de un destino y reservarlo
    @GetMapping("/reservar/{id}")
    public String reservarDestino(@PathVariable("id") int id, Model model) {
        Destinos destino = destinosRepository.findById(id).orElse(null);
        if (destino != null) {
            model.addAttribute("destino", destino);
            return "confirmarReserva"; // Vista para confirmar la reserva
        } else {
            return "redirect:/destinos/listar"; // Si no se encuentra el destino, redirige a la lista
        }
    }

    // Guardar la reserva después de confirmarla
    @PostMapping("/guardarReserva")
    public String guardarReserva(Reservas reserva) {
        reservasRepository.save(reserva); // Guardar la reserva en la base de datos
        return "redirect:/destinos/listar"; // Redirigir a la lista de destinos después de hacer la reserva
    }

    // Método para eliminar un destino
    @GetMapping("/eliminar/{id}")
    public String eliminarDestino(@PathVariable("id") int id) {
        destinosRepository.deleteById(id);
        return "redirect:/destinos/ver";
    }

    // Método para mostrar el formulario para agregar un destino
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("destino", new Destinos());
        return "formularioDestinos";
    }

    // Método para guardar un nuevo destino
    @PostMapping("/guardar")
    public String guardarDestino(Destinos destino) {
        destinosRepository.save(destino);
        return "redirect:/destinos/ver";
    }

    // Método para editar un destino
    @GetMapping("/editar/{id}")
    public String editarDestino(@PathVariable("id") int id, Model model) {
        Destinos destino = destinosRepository.findById(id).orElse(null);
        if (destino != null) {
            model.addAttribute("destino", destino);
            return "formularioDestinos";
        } else {
            return "redirect:/destinos/ver";
        }
    }
}
