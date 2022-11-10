package com.eeg.app.demo.controladores;

import com.eeg.app.demo.servicio.impl.UsuarioServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    public UsuarioServicioImpl usuarioServicio;

    @GetMapping()
    public String inicio() {

        return "index.html";
    }

    @GetMapping("/registro")
    public String save() {
        return "registros/usuario_formulario_registro.html";
    }

    @GetMapping("/iniciar-sesion")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Password inválidos");
        }
        return "iniciar_sesion.html";
    }
}
