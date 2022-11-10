package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.CapacitacionEntidad;
import com.eeg.app.demo.entidad.InteresEntidad;
import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.repositorio.CapacitacionRepositorio;
import com.eeg.app.demo.repositorio.InteresRepositorio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    CapacitacionRepositorio capacitacionRepositorio;

    @Autowired
    InteresRepositorio interesRepositorio;

    @GetMapping()
    public String dashboard(HttpSession session, ModelMap modelo) {

        UsuarioEntidad logueado = (UsuarioEntidad) session.getAttribute("usuariosession");
        String nombre = logueado.getNombre();
        modelo.put("nombre", nombre.toUpperCase()); //Quiero mandar el nombre del usuario
        return "admin/panel_admin.html";
    }

    @GetMapping("/reportes/capacitacionesPorUsuario")
    public String reportesCapacitacionPorUsuario(ModelMap modelo) {

        List<CapacitacionEntidad> lista = capacitacionRepositorio.findAll();
        modelo.addAttribute("capacitacion", lista);
        return "administrador/reporte_capacitaciones_por_usuario.html";
    }

    @GetMapping("/reportes/interesesPorUsuario")
    public String reporetsInteresesPorUsuario(ModelMap modelo) {

        List<InteresEntidad> lista = interesRepositorio.findAll();
        modelo.addAttribute("intereses", lista);
        return "administrador/reporte_intereses_por_usuario.html";
    }
}
