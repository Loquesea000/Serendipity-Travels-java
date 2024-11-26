package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Administradores;
import com.SerendipityTravels.app.Repository.AdministradoresRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministradorLoginController {

    @Autowired
    private AdministradoresRepository administradoresRepository;

    // Mostrar el formulario de login
    @GetMapping("/admin/login")
    public String mostrarFormularioLogin(Model model) {
        return "adminLogin"; // Nombre del HTML para el formulario de login
    }

    // Procesar el inicio de sesión
    @PostMapping("/admin/login")
    public String procesarLogin(@RequestParam("correo") String correo,
                                 @RequestParam("contra") String contra,
                                 HttpSession session,
                                 Model model) {
        // Buscar el administrador en la base de datos
        Administradores admin = administradoresRepository.findByCorreoAndContra(correo, contra);

        if (admin != null) {
            // Si las credenciales son correctas, guardar al administrador en la sesión
            session.setAttribute("adminSesion", admin);
            return "redirect:/AdminHome"; // Redirigir a la página principal del administrador
        } else {
            // Si las credenciales son incorrectas, mostrar un mensaje de error
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "adminLogin"; // Volver al formulario de login
        }
    }

    // Cerrar sesión
    @GetMapping("/admin/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Invalidar la sesión
        return "redirect:/"; // Redirigir al login
    }
}
