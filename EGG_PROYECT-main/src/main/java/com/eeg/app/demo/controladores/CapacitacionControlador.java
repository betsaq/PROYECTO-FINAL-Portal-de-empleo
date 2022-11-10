package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.CapacitacionEntidad;
import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.servicio.impl.CapacitacionServicio;
import com.eeg.app.demo.servicio.impl.UsuarioServicioImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/capacitacion")
public class CapacitacionControlador {

    @Autowired
    public CapacitacionServicio capacitacionServicio;

    @Autowired
    public UsuarioServicioImpl usuarioServicio;

    @GetMapping("/registro/{id}")
    public String save(
            @PathVariable String id,
            ModelMap modelo) {

        UsuarioEntidad usuario = usuarioServicio.buscarUsuarioPorId(id);
        modelo.addAttribute("usuario", usuario);
        return "registros/capacitacion_formulario_registro.html";
    }

    @PostMapping("/registrado/{id}")
    public String guardarCapacitacion(
            @PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String nivel,
            @RequestParam String email,
            @RequestParam String fechaEgreso,
            @RequestParam String institucion,
            ModelMap modelo) {

            capacitacionServicio.guardarCapacitacion(nombre, nivel, email, fechaEgreso, institucion);
        try {
            List<CapacitacionEntidad> capacitacion_lista = capacitacionServicio.listarCapacitacionesPorUsuario(id);
            modelo.put("exito", "La capacitacion fue cargada correctamente");
            modelo.addAttribute("capacitacion", capacitacion_lista);
            return "capacitacion/capacitacion_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect: capacitacion/registro";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<CapacitacionEntidad> capacitacion_lista = capacitacionServicio.listarCapacitaciones();
        modelo.addAttribute("capacitacion", capacitacion_lista);
        return "capacitacion/capacitacion_list.html";
    }

    @GetMapping("/listarPorUsuario/{id}")
    public String listarPorUsuario(
            @PathVariable String id,
            ModelMap modelo) throws Exception {

        try {
            List<CapacitacionEntidad> capacitacion_lista = capacitacionServicio.listarCapacitacionesPorUsuario(id);
            modelo.addAttribute("capacitacion", capacitacion_lista);
            return "capacitacion/capacitacion_list.html";
        } catch (Exception e) {
            return "capacitacion/capacitacion_list.html";
        }
    }

    @GetMapping("/busqueda")
    public String buscarCapacitacion(@RequestParam(required = false) String nombre, ModelMap modelo) {

        if (nombre.isEmpty()) {
            List<CapacitacionEntidad> capacitacion_lista = capacitacionServicio.listarCapacitaciones();
            modelo.addAttribute("capacitacion", capacitacion_lista);
            return "administrador/reporte_capacitaciones_por_usuario.html";
        } else {
            List<CapacitacionEntidad> capacitacionEntidades = capacitacionServicio.buscarCapacitacionPorNombre(nombre);
            modelo.addAttribute("capacitacion", capacitacionEntidades);
            return "administrador/reporte_capacitaciones_por_usuario.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarCapacitacion(@PathVariable String id, ModelMap modelo) {

        CapacitacionEntidad capacitacion = capacitacionServicio.buscarCapacitacionPorId(id);
        modelo.addAttribute("capacitacion", capacitacion);
        return "capacitacion/capacitacion_modificar.html";
    }

    @PostMapping("/modificado/{id}")
    public String capacitacionModificada(@PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String nivel,
            @RequestParam String email,
            @RequestParam String fechaEgreso,
            @RequestParam String institucion,
            ModelMap modelo) {

        try {
            capacitacionServicio.modificarCapacitacion(id, nombre, nivel, email, fechaEgreso, institucion);
            List<CapacitacionEntidad> capacitacion_lista = capacitacionServicio.listarCapacitaciones();
            modelo.addAttribute("capacitacion", capacitacion_lista);
            modelo.put("exito", "exito");
            return "capacitacion/capacitacion_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:../";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String bajarCapacitacion(@PathVariable String id, ModelMap modelo) {

        try {
            capacitacionServicio.eliminarCapacitacion(id);
            modelo.put("exito", "exito");
            return "capacitacion/capacitacion_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:../";
        }
    }
}
