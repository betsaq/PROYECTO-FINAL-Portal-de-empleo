package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.InteresEntidad;
import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.servicio.impl.InteresServicio;
import com.eeg.app.demo.servicio.impl.UsuarioServicioImpl;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/interes")
public class InteresControlador {

    @Autowired
    private InteresServicio interesServicio;

    @Autowired
    public UsuarioServicioImpl usuarioServicio;

    //---------------REGISTRAR
    @GetMapping("/registro/{id}")
    public String registrar(@PathVariable String id, ModelMap modelo) {

        UsuarioEntidad usuario = usuarioServicio.buscarUsuarioPorId(id);
        modelo.addAttribute("usuario", usuario);
        return "registros/interes_formulario_registro.html";
    }

    @PostMapping("/registrado/{id}")
    public String registro(
            @PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String email,
            ModelMap modelo) throws MiExcepcion {

        try {
            interesServicio.guardarInteres(nombre, email);
            List<InteresEntidad> intereses_lista = interesServicio.listarInteresesPorUsuario(id);
            modelo.put("exito", "Error al guardar interes");
            modelo.addAttribute("intereses", intereses_lista);
            return "interes/interes_lista.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "redirect: interes/registro";
        }
    }

    //---------------listar
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<InteresEntidad> intereses = interesServicio.listarInteres();
        modelo.addAttribute("intereses", intereses);
        return "interes/interes_lista.html";
    }

    @GetMapping("/listarPorUsuario/{id}")
    public String listarPorUsuario(@PathVariable String id, ModelMap modelo) {

        List<InteresEntidad> intereses_lista = interesServicio.listarInteresesPorUsuario(id);
        modelo.addAttribute("intereses", intereses_lista);
        return "interes/interes_lista.html";
    }

    //-----------------MODIFICAR
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.addAttribute("interes", interesServicio.getOne(id));
        return "interes/interes_modificar.html";
    }

    @PostMapping("/modificado/{id}")
    public String modificarInteres(@PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String email,
            ModelMap modelo) {

        try {
            interesServicio.modificarInteres(id, nombre, email);
            List<InteresEntidad> interes = interesServicio.listarInteres();
            modelo.addAttribute("intereses", interes);
            modelo.put("exito", "exito");
            return "interes/interes_lista.html";
        } catch (MiExcepcion ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:../";
        }
    }

    //--------- ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarInteres(@PathVariable String id, ModelMap modelo) {

        try {
            interesServicio.eliminarInteres(id);
            return "interes/interes_lista.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "interes/interes_lista.html";
        }
    }

    @GetMapping("/busqueda")
    public String buscarCapacitacion(@RequestParam(required = false) String nombre, ModelMap modelo) {

        if (nombre.isEmpty()) {
            List<InteresEntidad> interes_lista = interesServicio.listarInteres();
            modelo.addAttribute("intereses", interes_lista);
            return "administrador/reporte_intereses_por_usuario.html";
        } else {
            List<InteresEntidad> interesEntidades = interesServicio.buscarCapacitacionPorNombre(nombre);
            modelo.addAttribute("intereses", interesEntidades);
            return "administrador/reporte_intereses_por_usuario.html";
        }
    }
}
