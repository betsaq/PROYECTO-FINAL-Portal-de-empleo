package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.PaisEntidad;
import com.eeg.app.demo.servicio.impl.PaisServicioImpl;
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
@RequestMapping("/pais")
public class PaisControlador {

    public String mensaje = "Modificacion exitosa";
    public String listado = "No se han encontrado paises registrados";
    public String eliminado = "Se ha eliminado el Pais seleccionado";

    @Autowired
    public PaisServicioImpl paisServicio;

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<PaisEntidad> pais_lista = paisServicio.listarPaises();
        modelo.addAttribute("pais", pais_lista);
        return "pais/pais_list.html";
    }

    @GetMapping("/busqueda")
    public String buscarPaisPorNombre(@RequestParam(required = false) String nombre, ModelMap modelo) {

        if (nombre.isEmpty()) {
            List<PaisEntidad> pais_lista = paisServicio.listarPaises();
            if (pais_lista.isEmpty()) {
                modelo.put("ausencia", listado);
            }
            modelo.addAttribute("pais", pais_lista);
//           
        } else {
            PaisEntidad paisEntidad = paisServicio.buscarPaisPorNombre(nombre);
            if (paisEntidad == null) {
                modelo.put("ausencia", listado);
            }
            modelo.addAttribute("pais", paisEntidad);
        }
        return "pais/pais_list.html";
    }

    @GetMapping("/registro")
    public String save() {

        return "registros/pais_formulario_registro.html";
    }

    @PostMapping("/registrar")
    public String guardarPais(@RequestParam String nombre, ModelMap modelo) {

        try {
            paisServicio.guardarPais(nombre);
            List<PaisEntidad> pais_lista = paisServicio.listarPaises();
            modelo.put("ok", mensaje);
            modelo.addAttribute("pais", pais_lista);
            return "/pais/pais_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/pais/pais_list.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarPais(@PathVariable String id, ModelMap modelo) {

        PaisEntidad entidad = paisServicio.buscarPaisPorId(id);
        modelo.addAttribute("pais", entidad);
        return "pais/pais_modificar.html";
    }

    @PostMapping("/modificado/{id}")
    public String paisModificado(@PathVariable String id, @RequestParam String nombre, ModelMap modelo) {

        try {
            List<PaisEntidad> pais_lista = paisServicio.listarPaises();
            paisServicio.modificarPais(id, nombre);
            modelo.put("ok", mensaje);
            modelo.addAttribute("pais", pais_lista);
            return "pais/pais_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "pais/pais_list.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String bajarPais(@PathVariable String id, ModelMap modelo) {

        try {
            paisServicio.eliminarPais(id);
            modelo.put("ok", eliminado);
            return "pais/pais_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "pais/pais_list.html";
        }
    }
}
