package com.SerendipityTravels.app.Controller;

import com.SerendipityTravels.app.Entity.Usuarios;
import com.SerendipityTravels.app.Repository.UsuariosRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;
    
    
    // Endpoint para obtener el usuario actual desde la sesión
    @GetMapping("/getUsuarioSesion")
    @ResponseBody
    public Usuarios getUsuarioSesion(HttpSession session) {
        Long cedula = (Long) session.getAttribute("usuarioSesionCedula");
        if (cedula == null) {
            System.out.println("Cédula no encontrada en sesión.");
            return null; // Retorna null si no hay cédula
        }
        return usuariosRepository.findById(cedula).orElse(null);
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuarios> listaUsuarios = usuariosRepository.findAll();
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "listarUsuarios"; // Vista para mostrar la lista
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "formularioUsuarios"; // Vista para el formulario
    }
    
    @GetMapping("/formularioregistro")
    public String mostrarFormularioregistro(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "formularioUsuarioRegistro"; // Vista para el formulario
    }
    
    @PostMapping("/guardarRegistro")
    public String guardarUsuarioRegistro(Usuarios usuario) {
        usuariosRepository.save(usuario);
        return "redirect:/login";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(Usuarios usuario) {
        usuariosRepository.save(usuario);
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/editar/{cedula}")
    public String editarUsuario(@PathVariable("cedula") long cedula, Model model) {
        Usuarios usuario = usuariosRepository.findById(cedula).orElse(null);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "formularioUsuarios";
        } else {
            return "redirect:/usuarios/listar";
        }
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminarUsuario(@PathVariable("cedula") long cedula) {
        usuariosRepository.deleteById(cedula);
        return "redirect:/usuarios/listar";
    }
}
