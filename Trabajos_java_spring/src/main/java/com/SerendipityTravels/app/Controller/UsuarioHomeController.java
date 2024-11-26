package com.SerendipityTravels.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioHomeController {

    // Muestra la página UsuarioHome
    @GetMapping("/UsuarioHome")
    public String showUsuarioHome() {
        return "UsuarioHome"; // Retorna la plantilla UsuarioHome.html desde templates
    }
}
