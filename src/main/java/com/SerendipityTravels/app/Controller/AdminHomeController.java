package com.SerendipityTravels.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {
	// Muestra la página AdminHome
    @GetMapping("/AdminHome")
    public String showAdminHome() {
        return "AdminHome"; // Retorna la plantilla AdminHome.html desde templates
    }

}


