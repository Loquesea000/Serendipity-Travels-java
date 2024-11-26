package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Usuarios;
import com.SerendipityTravels.app.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Muestra la página de login
    @GetMapping({"/sesion","/login"})
    public String showLoginForm(Model model) {
        model.addAttribute("usuario", new Usuarios()); // Agrega un objeto vacío al modelo
        return "index"; // Retorna la plantilla index.html
    }

    // Procesa las credenciales del formulario
    @PostMapping("/index")
    public String processLogin(
            @RequestParam("correo") String correo,
            @RequestParam("contra") String contra,
            HttpSession session, // Agrega HttpSession como parámetro
            Model model) {

        // Busca el usuario en la base de datos
        Usuarios usuario = usuariosRepository.findByCorreoAndContra(correo, contra);

        if (usuario != null) {
            session.setAttribute("usuarioSesion", usuario);
            session.setAttribute("usuarioSesionCedula", usuario.getCedula()); // Guarda la cédula
            System.out.println("Cédula guardada en sesión: " + session.getAttribute("usuarioSesionCedula")); // Debug
            return "redirect:/UsuarioHome";
        } else {
            // Credenciales inválidas: muestra un mensaje de error
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            model.addAttribute("usuario", new Usuarios()); // Mantiene el objeto vacío para el formulario
            return "index"; // Vuelve a cargar el formulario con el mensaje de error
        }
    }
    
}
