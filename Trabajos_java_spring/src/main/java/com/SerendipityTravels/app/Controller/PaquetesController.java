package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.PaquetesTuristicos;
import com.SerendipityTravels.app.Repository.PaquetesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/paquetes")
public class PaquetesController {

    @Autowired
    private PaquetesRepository paquetesRepository;

    // Mostrar todos los paquetes en formato JSON (para el modal)
    @GetMapping("/listar-json")
    @ResponseBody
    public List<PaquetesTuristicos> listarPaquetesJson() {
        return paquetesRepository.findAll(); // Devuelve todos los paquetes en formato JSON
    }

    // Listar todos los paquetes en la página principal
    @GetMapping("/listar")
    public String listarPaquetes(Model model) {
        List<PaquetesTuristicos> listaPaquetes = paquetesRepository.findAll();
        model.addAttribute("listaPaquetes", listaPaquetes);
        return "verPaquetes"; // Vista para mostrar la lista
    }

    // Formulario para agregar un nuevo paquete
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("paquete", new PaquetesTuristicos());
        return "formularioPaquetes"; // Vista para el formulario
    }

    // Guardar un paquete
    @PostMapping("/guardar")
    public String guardarPaquete(PaquetesTuristicos paquete) {
        paquetesRepository.save(paquete);
        return "redirect:/paquetes/listar";
    }

    // Editar un paquete existente
    @GetMapping("/editar/{id}")
    public String editarPaquete(@PathVariable("id") int id, Model model) {
        PaquetesTuristicos paquete = paquetesRepository.findById(id).orElse(null);
        if (paquete != null) {
            model.addAttribute("paquete", paquete);
            return "formularioPaquetes";
        } else {
            return "redirect:/paquetes/listar";
        }
    }

    // Eliminar un paquete
    @GetMapping("/eliminar/{id}")
    public String eliminarPaquete(@PathVariable("id") int id) {
        paquetesRepository.deleteById(id);
        return "redirect:/paquetes/listar";
    }
}
