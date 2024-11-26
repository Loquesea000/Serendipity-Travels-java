package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Administradores;
import com.SerendipityTravels.app.Repository.AdministradoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdministradoresRepository administradoresRepository;

    // Listar todos los administradores
    @GetMapping("/listar")
    public String listarAdministradores(Model model) {
        List<Administradores> listaAdministradores = administradoresRepository.findAll();
        model.addAttribute("listaAdministradores", listaAdministradores);
        return "listarAdministradores"; // Vista para listar administradores
    }

    // Mostrar formulario para agregar un nuevo administrador
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("administrador", new Administradores());
        return "formularioAdministradores"; // Vista para el formulario
    }

    // Guardar un administrador
    @PostMapping("/guardar")
    public String guardarAdministrador(@ModelAttribute Administradores administrador) {
        administradoresRepository.save(administrador);
        return "redirect:/admin/listar"; // Redirigir a la lista después de guardar
    }

    // Editar un administrador existente
    @GetMapping("/editar/{id}")
    public String editarAdministrador(@PathVariable("id") int id, Model model) {
        Administradores administrador = administradoresRepository.findById(id).orElse(null);
        if (administrador != null) {
            model.addAttribute("administrador", administrador);
            return "formularioAdministradores"; // Reutilizar el formulario para edición
        } else {
            return "redirect:/admin/listar"; // Si no se encuentra, redirigir a la lista
        }
    }

    // Eliminar un administrador
    @GetMapping("/eliminar/{id}")
    public String eliminarAdministrador(@PathVariable("id") int id) {
        administradoresRepository.deleteById(id);
        return "redirect:/admin/listar"; // Redirigir a la lista después de eliminar
    }
}
