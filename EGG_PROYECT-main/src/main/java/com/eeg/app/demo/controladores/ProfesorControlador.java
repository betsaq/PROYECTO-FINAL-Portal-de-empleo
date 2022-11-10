package com.eeg.app.demo.controladores;

import java.util.List;
import com.eeg.app.demo.entidad.ProfesorEntidad;
import com.eeg.app.demo.servicio.impl.ProfesorServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("profesor")
public class ProfesorControlador {

    @Autowired
    public ProfesorServicioImpl profesorServicio;

    @GetMapping("/lista")
    public String listarProfesor(ModelMap modelo) {

        List<ProfesorEntidad> listaProfesores = profesorServicio.listarProfesores();
        modelo.addAttribute("listaProfesores", listaProfesores);
        return "profesor/profesor_lista.html";
    }

    @PostMapping("/registrar")
    public String guardarProfesor(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String nacionalidad,
            @RequestParam String tecnologiaDominante,
            @RequestParam String stack, ModelMap modelo) {

        String mensaje = "Bienvenido a EGG-ALL chequea tu casilla de correo, buzon de entrada o spam";

        try {
            profesorServicio.guardarProfesor(nombre, apellido, email, nacionalidad, tecnologiaDominante, stack);
            modelo.put("exito", mensaje);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "demo.html";
        }
        return "registros/profesor_registro.html";
    }

    @PostMapping("/editar/{id}")
    public String modificarProfesor(@PathVariable Long id,
            String nombre, String apellido,
            String email, String nacionalidad,
            String tecnologiaDominante, String Stack,
            ModelMap modelo) {

        try {
            ProfesorEntidad profesor = profesorServicio.buscarProfesorPorId(id);
            modelo.addAttribute("profesor", profesor);
            profesorServicio.modificarProfesor(id, nombre, apellido, email, nacionalidad, tecnologiaDominante, Stack);
            modelo.put("exito", "exito");
            return "redirect:../list";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "profesor/profesor_editar.html";
        }
    }

    @PostMapping("/delete/{id}")
    public String BajaProfesor(@PathVariable Long id, ModelMap modelo) {

        try {
            profesorServicio.eliminarProfesor(id);
            return "redirect:../list";
        } catch (Exception e) {
            return "profesor/profesor_lista.html";
        }
    }
}
