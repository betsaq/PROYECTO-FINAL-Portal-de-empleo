package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.HerramientaEntidad;
import com.eeg.app.demo.servicio.impl.HerramientaServicioImpl;
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
@RequestMapping("/herramienta")
public class HerramientaControlador {

    public String mensaje = "Modificación exitosa";
    public String listado = "No se han encontrado herramientas registradas";
    public String eliminado = "Se ha eliminado la Herramienta seleccionada";

    @Autowired
    public HerramientaServicioImpl herramientaServicio;

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<HerramientaEntidad> herramienta_lista = herramientaServicio.listarHerramientas();
        modelo.addAttribute("herramienta", herramienta_lista);
        return "herramienta/herramienta_list.html";
    }

    @GetMapping("/busqueda")
    public String buscarHerramientaPorNombre(@RequestParam(required = false) String nombre, ModelMap modelo) {

        if (nombre.isEmpty()) {
            List<HerramientaEntidad> herramienta_lista = herramientaServicio.listarHerramientas();
            if (herramienta_lista.isEmpty()) {
                modelo.put("ausencia", listado);
            }
            modelo.addAttribute("herramienta", herramienta_lista);
        } else {
            HerramientaEntidad herramientaEntidad = herramientaServicio.buscarHerramientaPorNombre(nombre);
            if (herramientaEntidad == null) {
                modelo.put("ausencia", listado);
            }
            modelo.addAttribute("herramienta", herramientaEntidad);
        }

        return "herramienta/herramienta_list.html";
    }

    @GetMapping("/registro")
    public String save() {

        return "registros/herramienta_formulario_registro.html";
    }

    @PostMapping("/registrar")
    public String guardarHerramienta(@RequestParam String nombre, ModelMap modelo) {

        String mensaje = "Modificacion exitosa";

        try {
            herramientaServicio.guardarHerramienta(nombre);
            List<HerramientaEntidad> herramienta_lista = herramientaServicio.listarHerramientas();
            modelo.put("ok", mensaje);
            modelo.addAttribute("herramienta", herramienta_lista);
            return "/herramienta/herramienta_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/herramienta/herramienta_list.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarHerramienta(@PathVariable String id, ModelMap modelo) {

        HerramientaEntidad entidad = herramientaServicio.buscarHerramientaPorId(id);
        modelo.addAttribute("herramienta", entidad);
        return "herramienta/herramienta_modificar.html";
    }

    @PostMapping("/modificado/{id}")
    public String herramientaModificado(@PathVariable String id,
            @RequestParam String nombre,
            ModelMap modelo) {

        try {
            herramientaServicio.modificarHerramienta(id, nombre);
            List<HerramientaEntidad> herramienta_lista = herramientaServicio.listarHerramientas();
            modelo.put("ok", mensaje);
            modelo.addAttribute("herramienta", herramienta_lista);
            return "/herramienta/herramienta_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:../";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String bajarHerramienta(@PathVariable String id, ModelMap modelo) {

        try {
            herramientaServicio.eliminarHerramienta(id);
            List<HerramientaEntidad> herramienta_lista = herramientaServicio.listarHerramientas();
            modelo.put("ok", eliminado);
            modelo.addAttribute("herramienta", herramienta_lista);
            return "/herramienta/herramienta_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/herramienta/herramienta_list.html";
        }
    }
}
