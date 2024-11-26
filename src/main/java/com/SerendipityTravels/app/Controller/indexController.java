package com.SerendipityTravels.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
	@GetMapping({"/","index","/home","/inicio"})
	public String redirectToHomePage() {
		return "Arranque";
	}

}
