package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Destinos;
import com.SerendipityTravels.app.Entity.Pagos;
import com.SerendipityTravels.app.Entity.PaquetesTuristicos;
import com.SerendipityTravels.app.Entity.Reservas;
import com.SerendipityTravels.app.Entity.Usuarios;
import com.SerendipityTravels.app.Repository.DestinosRepository;
import com.SerendipityTravels.app.Repository.PaquetesRepository;
import com.SerendipityTravels.app.Repository.ReservasRepository;
import com.SerendipityTravels.app.Repository.UsuariosRepository;
import com.SerendipityTravels.app.Repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private DestinosRepository destinosRepository;

    @Autowired
    private PaquetesRepository paquetesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private PagosRepository PagosRepository;
    
    @GetMapping
    public String mostrarFormularioPago(@RequestParam("idReserva") int idReserva, Model model) {
        // Obtener la reserva por su ID
        Reservas reserva = reservasRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Pasar la reserva al modelo para que sea accesible en la vista
        model.addAttribute("reserva", reserva);

        // Obtener el destino y paquete de la reserva
        model.addAttribute("destino", reserva.getDestino());
        model.addAttribute("paquete", reserva.getPaquete());

        return "pagos"; // Nombre de la vista que contiene el formulario de pago
    }
    
    @PostMapping("/crear")
    public String procesarPago(@RequestParam("idReserva") int idReserva, 
                               @RequestParam("metodoPago") String metodoPago, 
                               @RequestParam("montoPagado") long montoPagado, 
                               RedirectAttributes redirectAttributes) {
        try {
            // Buscar la reserva usando el ID recibido
            Reservas reserva = reservasRepository.findById(idReserva)
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada para el ID: " + idReserva));

            // Crear un nuevo objeto de pago y asignar los valores
            Pagos nuevoPago = new Pagos();
            nuevoPago.setMetodo(metodoPago);
            nuevoPago.setMontoPagado(montoPagado);
            nuevoPago.setReserva(reserva);  // Asociamos la reserva
            nuevoPago.setFechaPago(new Date());  // Fecha actual

            // Guardar el pago en la base de datos
            PagosRepository.save(nuevoPago);

            // Cambiar el estado de la reserva a 0 (pagada)
            reserva.setEst(0);  // Cambiar el estado de la reserva a 0
            reservasRepository.save(reserva);  // Guardar la reserva con el nuevo estado

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("message", "Pago procesado con éxito.");

            // Redirigir al usuario a la página de ver reservas
            return "redirect:/reservas/ver";  // Redirige al controlador de reservas/ver
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, mostrar un mensaje de error y redirigir
            redirectAttributes.addFlashAttribute("error", "Hubo un error al procesar el pago.");
            return "redirect:/pagos";  // Redirige nuevamente al formulario de pago
        }
    }



    
   /* @GetMapping("/formulario")
    public String mostrarFormularioDePago(@RequestParam("idReserva") int idReserva, Model model) {
        // Buscar la reserva por su ID
        Reservas reserva = reservasRepository.findById(idReserva).orElse(null);

        if (reserva != null) {
            // Obtener el destino y el paquete de la reserva
            Destinos destino = reserva.getDestino();
            PaquetesTuristicos paquete = reserva.getPaquete();

            // Pasar los datos al modelo
            model.addAttribute("reserva", reserva);
            model.addAttribute("destino", destino);
            model.addAttribute("paquete", paquete);
        }

        return "formularioPago"; // Vista para el formulario de pago
    }*/

    // Obtener la última reserva del usuario, destino y paquete
    /*@GetMapping
    public String mostrarFormularioPagos(@SessionAttribute("usuarioSesion") Usuarios usuario, Model model) {
        // Obtener la última reserva del usuario
        List<Reservas> listaReservas = reservasRepository.findByUsuarioCedulaOrderByFechaReservaDesc(usuario.getCedula());
        Reservas ultimaReserva = listaReservas.isEmpty() ? null : listaReservas.get(0);

        if (ultimaReserva != null) {
            // Obtener los detalles del destino y paquete
            Destinos destino = destinosRepository.findById(ultimaReserva.getDestino().getId()).orElse(null);
            PaquetesTuristicos paquete = paquetesRepository.findById(ultimaReserva.getPaquete().getId()).orElse(null);

            // Pasar al modelo los datos
            model.addAttribute("reserva", ultimaReserva);
            model.addAttribute("destino", destino);
            model.addAttribute("paquete", paquete);
        }

        return "pagos"; // Renderiza el archivo pagos.html
    }*/
}
