package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.ProvinciaEntidad;
import com.eeg.app.demo.servicio.impl.ProvinciaServicioImpl;
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
@RequestMapping("/provincia")
public class ProvinciaControlador {

    public String mensaje = "Modificación exitosa";
    public String listado = "No se han encontrado provincias registradas";
    public String eliminado = "Se ha eliminado la Provincia seleccionada";

    @Autowired
    public ProvinciaServicioImpl provinciaServicio;

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<ProvinciaEntidad> provincia_lista = provinciaServicio.listarProvincias();
        modelo.addAttribute("pais", provincia_lista);
        return "provincia/provincia_list.html";
    }

    @GetMapping("/busqueda")
    public String buscarProvinciaPorNombre(@RequestParam(required = false) String nombre, ModelMap modelo) {

        if (nombre.isEmpty()) {
            List<ProvinciaEntidad> provincia_lista = provinciaServicio.listarProvincias();
            if (provincia_lista.isEmpty()) {
                modelo.put("ausencia", listado);
            }
            modelo.addAttribute("provincia", provincia_lista);
        } else {
            ProvinciaEntidad provinciaEntidad = provinciaServicio.buscarProvinciaPorNombre(nombre);
            if (provinciaEntidad == null) {
                modelo.put("ausencia", listado);
            }
            modelo.addAttribute("provincia", provinciaEntidad);
        }
        return "provincia/provincia_list.html";
    }

    @GetMapping("/registro")
    public String save() {

        return "registros/provincia_formulario_registro.html";
    }

    @PostMapping("/registrar")
    public String guardarProvincia(@RequestParam String nombre, ModelMap modelo) {

        String mensaje = "Modificacion exitosa";
        try {
            provinciaServicio.guardarProvincia(nombre);
            List<ProvinciaEntidad> provincia_lista = provinciaServicio.listarProvincias();
            modelo.put("ok", mensaje);
            modelo.addAttribute("provincia", provincia_lista);
            return "/provincia/provincia_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/provincia/provincia_list.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarProvincia(@PathVariable String id, ModelMap modelo) {

        ProvinciaEntidad entidad = provinciaServicio.buscarProvinciaPorId(id);
        modelo.addAttribute("provincia", entidad);
        return "provincia/provincia_modificar.html";
    }

    @PostMapping("/modificado/{id}")
    public String provinciaModificado(@PathVariable String id,
            @RequestParam String nombre,
            ModelMap modelo) {

        try {
            provinciaServicio.modificarProvincia(id, nombre);
            List<ProvinciaEntidad> provincia_lista = provinciaServicio.listarProvincias();
            modelo.put("ok", mensaje);
            modelo.addAttribute("provincia", provincia_lista);
            return "/provincia/provincia_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/provincia/provincia_list.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String bajarProvincia(@PathVariable String id, ModelMap modelo) {

        try {
            provinciaServicio.eliminarProvincia(id);
            List<ProvinciaEntidad> provincia_lista = provinciaServicio.listarProvincias();
            modelo.put("ok", eliminado);
            modelo.addAttribute("provincia", provincia_lista);
            return "/provincia/provincia_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "/provincia/provincia_list.html";
        }
    }
}
