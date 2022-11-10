package com.eeg.app.demo.controladores;

import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.servicio.impl.CapacitacionServicio;
import com.eeg.app.demo.servicio.impl.UsuarioServicioImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    public String mensaje = "Modificación exitosa";

    @Autowired
    public UsuarioServicioImpl usuarioServicio;

    @Autowired
    public CapacitacionServicio capacitacionServicio;

    @GetMapping("/editar/{id}")
    public String editarPerfil(@PathVariable String id, ModelMap modelo) {

        UsuarioEntidad entidad = usuarioServicio.buscarUsuarioPorId(id);
        modelo.addAttribute("usuario", entidad);
        return "usuario/crear_perfil.html";
    }

    @PostMapping("/editado/{id}")
    public String perfilEditado(@PathVariable String id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String descripcion,
            ModelMap modelo) {

        try {
            UsuarioEntidad usuario = usuarioServicio.editarPerfil(id, nombre, apellido, email, pais, provincia, descripcion);
            modelo.put("ok", mensaje);
            modelo.addAttribute("usuario", usuario);
            return "usuario/panel_usuario.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "usuario/crear_perfil.html";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<UsuarioEntidad> usuario_lista = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuario", usuario_lista);
        return "usuario/usuario_list.html";
    }

    @GetMapping("/busqueda")
    public String buscarUsuarioPorEmail(@RequestParam(required = false) String email, ModelMap modelo) {

        if (email.isEmpty()) {
            List<UsuarioEntidad> usuario_lista = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuario", usuario_lista);
        } else {
            UsuarioEntidad usuarioEntidad = usuarioServicio.buscarUsuarioPorEmail(email);
            modelo.addAttribute("usuario", usuarioEntidad);
        }
        return "usuario/usuario_list.html";
    }

    @GetMapping("/registro")
    public String save(HttpSession session) {

        UsuarioEntidad logueado = (UsuarioEntidad) session.getAttribute("usuariosession");

        if (logueado != null) {
            if (logueado.getRol().toString().equals("ADMIN") || logueado.getRol().toString().equals("USER")) {
                return "redirect:../usuario/panel";
            }
        }
        return "registros/usuario_formulario_registro.html";
    }

    @PostMapping("/registrar")
    public String guardarUsuario(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String password,
            ModelMap modelo) {

        String mensaje = "Bienvenido al portal, usuario registrado con exito";
        try {
            usuarioServicio.guardarUsuario(nombre, apellido, email, password);
            modelo.put("ok", mensaje);
            return "index.html";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "index.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable String id, ModelMap modelo) {

        UsuarioEntidad entidad = usuarioServicio.buscarUsuarioPorId(id);
        modelo.addAttribute("usuario", entidad);
        return "usuario/usuario_modificar.html";
    }

    @PostMapping("/modificado/{id}")
    public String usuarioModificado(@PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String password, ModelMap modelo) {

        try {
            try {
                usuarioServicio.modificarUsuario(id, nombre, apellido, email, password);
                modelo.put("ok", mensaje);
                List<UsuarioEntidad> usuario_lista = usuarioServicio.listarUsuarios();
                modelo.addAttribute("usuario", usuario_lista);
                return "usuario/usuario_list.html";
            } catch (Exception e) {
                List<UsuarioEntidad> usuario_lista = usuarioServicio.listarUsuarios();
                modelo.put("error", e.getMessage());
                modelo.addAttribute("usuario", usuario_lista);
                return "usuario/usuario_list.html";
            }
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:../";
        }
    }

    @GetMapping("panel/modificar-foto/{id}")
    public String modificarFoto(@PathVariable String id, ModelMap modelo) {

        UsuarioEntidad entidad = usuarioServicio.buscarUsuarioPorId(id);
        modelo.addAttribute("usuario", entidad);
        return "usuario/cargar_foto.html";
    }

    @PostMapping("/modificado/cargar-foto/{id}")
    public String cargarFoto(@PathVariable String id,
            @RequestParam MultipartFile archivo, ModelMap modelo) throws IOException {

        usuarioServicio.agregarfoto(id, archivo);
        UsuarioEntidad entidad = usuarioServicio.buscarUsuarioPorId(id);
        modelo.put("ok", mensaje);
        modelo.addAttribute("usuario", entidad);
        return "usuario/panel_usuario.html";
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

        UsuarioEntidad entidad = usuarioServicio.buscarUsuarioPorId(id);
        byte[] imagen = entidad.getFoto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/eliminar/{id}")
    public String bajarUsuario(@PathVariable String id, ModelMap modelo) {

        try {
            usuarioServicio.eliminarUsuario(id);
            modelo.put("ok", "exito");
            List<UsuarioEntidad> usuario_lista = usuarioServicio.listarUsuarios();
            modelo.addAttribute("usuario", usuario_lista);
            return "usuario/usuario_list.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:../";
        }

    }

    @GetMapping("/panel")
    public String dashboard(HttpSession session, ModelMap modelo) {

        UsuarioEntidad logueado = (UsuarioEntidad) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin";
        }
        modelo.addAttribute("usuario", logueado); //Quiero mandar el nombre del usuario
        return "usuario/panel_usuario.html";
    }

    /*RUTA PARA AGREGAR CAPACITACIONES AL USUARIO*/
 /* Esta ruta me lleva a ver las capacitaciones actuales para poder agregarlas a mi perfil*/
    @GetMapping("inscripcion/{id}")
    public String inscripcionCapacitacion(@PathVariable String id, ModelMap modelo, HttpSession session) {

        try {
            UsuarioEntidad logueado = (UsuarioEntidad) session.getAttribute("usuariosession");
            UsuarioEntidad entidad = usuarioServicio.inscripcion(id, logueado.getId());

            modelo.addAttribute("usuario", entidad);
            return "usuario/panel_usuario.html";
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
}
