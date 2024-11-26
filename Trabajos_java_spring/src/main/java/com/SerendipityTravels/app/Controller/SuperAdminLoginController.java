package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.SuperAdmin;
import com.SerendipityTravels.app.Repository.SuperAdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuperAdminLoginController {

    @Autowired
    private SuperAdminRepository SuperAdminRepository;

    // Mostrar formulario de login
    @GetMapping("/superadmin/login")
    public String mostrarLoginSuperAdmin() {
        return "loginSuperAdmin"; // Vista para el formulario de login
    }

    // Procesar login
    @PostMapping("/superadmin/login")
    public String procesarLogin(@RequestParam("correo") String correo, 
                                 @RequestParam("contra") String contra, 
                                 HttpSession session, 
                                 Model model) {
        // Buscar al SuperAdmin por correo
    	SuperAdmin superAdmin = SuperAdminRepository.findByCorreoAndContra(correo, contra);

        if (superAdmin != null) {
            // Guardar en sesión y redirigir al home del SuperAdmin
            session.setAttribute("superAdminSesion", superAdmin);
            return "redirect:/superAdminHome";
        } else {
            // Mostrar error si las credenciales no coinciden
            model.addAttribute("error", "Credenciales incorrectas. Inténtalo de nuevo.");
            return "loginSuperAdmin";
        }
    }



    // Cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Invalidar la sesión
        return "redirect:/superadmin/login";
    }
}
