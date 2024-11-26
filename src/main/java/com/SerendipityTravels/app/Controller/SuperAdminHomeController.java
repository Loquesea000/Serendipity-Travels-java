package com.SerendipityTravels.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperAdminHomeController {
	// Muestra la página AdminHome
    @GetMapping("/superAdminHome")
    public String showsuperAdminHome() {
        return "superAdminHome"; // Retorna la plantilla AdminHome.html desde templates
    }

}


