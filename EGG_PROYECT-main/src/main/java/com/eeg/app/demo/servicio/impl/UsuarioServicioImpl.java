package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.CapacitacionEntidad;
import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.enumm.Rol;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.repositorio.CapacitacionRepositorio;
import com.eeg.app.demo.repositorio.UsuarioRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicioImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private CapacitacionRepositorio capacitacionRepositorio;

    @Transactional
    public UsuarioEntidad editarPerfil(String id, String nombre, String apellido, String email, String pais, String provincia, String descripcion) {

        Optional<UsuarioEntidad> respuesta = usuarioRepositorio.findById(id);

        UsuarioEntidad usuario = null;
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setPais(pais);
            usuario.setProvincia(provincia);
            usuario.setDescripcion(descripcion);
        }
        usuarioRepositorio.save(usuario);
        return usuario;
    }

    @Transactional
    public UsuarioEntidad guardarUsuario(String nombre, String apellido, String email, String password) throws MiExcepcion {

        validar(nombre, apellido, email, password);

        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);

        usuario.setAlta(new Date());

        usuarioRepositorio.save(usuario);

        return usuario;
    }

    @Transactional(readOnly = true)
    public List<UsuarioEntidad> listarUsuarios() {

        List<UsuarioEntidad> usuario = new ArrayList();

        usuario = usuarioRepositorio.findAll();

        return usuario;

    }

    @Transactional(readOnly = true)
    public UsuarioEntidad buscarUsuarioPorEmail(String email) {
        UsuarioEntidad entidad = usuarioRepositorio.buscarPorEmail(email);
        return entidad;
    }

    public UsuarioEntidad buscarUsuarioPorId(String id) {

        Optional<UsuarioEntidad> respuesta = usuarioRepositorio.findById(id);

        UsuarioEntidad usuario = respuesta.get();
        if (!respuesta.isPresent()) {
            return null;
        }
        return usuario;
    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, String email, String password) throws MiExcepcion {

        validar(nombre, apellido, email, password);

        Optional<UsuarioEntidad> respuesta = usuarioRepositorio.findById(id);

        UsuarioEntidad usuario = null;
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(String id) {
        Optional<UsuarioEntidad> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            UsuarioEntidad entidad = respuesta.get();
            usuarioRepositorio.delete(entidad);
        }
    }

    public void agregarfoto(String id, MultipartFile archivo) throws IOException {
        Optional<UsuarioEntidad> respuesta = usuarioRepositorio.findById(id);
        UsuarioEntidad usuario = respuesta.get();
        usuario.setFoto(archivo.getBytes());
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public UsuarioEntidad inscripcion(String id, String id_usuario) throws MiExcepcion {

        if (id.isEmpty() || id_usuario.isEmpty()) {
            throw new MiExcepcion("id nulos");
        }

        UsuarioEntidad usuario = usuarioRepositorio.findById(id_usuario).get();
        try {
            CapacitacionEntidad capacitacion = capacitacionRepositorio.findById(id).get();
            usuario.agregarCapacitacion(capacitacion);
            usuarioRepositorio.save(usuario);
        } catch (Exception e) {
            throw new MiExcepcion("usuario null");
        }

        return usuario;
    }

    private void validar(String nombre, String apellido, String email, String password) throws MiExcepcion {

        if (nombre.isEmpty() || nombre == null || nombre.length() < 4 || nombre.matches("^([A-Z]{1}[a-záéíóú]+[ ]?){1,3}$") == false) {
            throw new MiExcepcion("Ingrese un NOMBRE VÁLIDO con al menos de 4 letras");
        }

        if (apellido.isEmpty() || apellido == null || apellido.length() < 4 || apellido.matches("^([A-Z]{1}[a-záéíóú]+[ ]?){1,3}$") == false) {
            throw new MiExcepcion("Ingrese un APELLIDO VÁLIDO con al menos 4 letras");
        }

        if (email.isEmpty() || email == null || email.matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") == false) {
            throw new MiExcepcion("Ingrese un MAIL VÁLIDO");
        }

        if (password.isEmpty() || password == null || password.matches("^[A-Za-z0-9]{8,}$") == false) {
            throw new MiExcepcion("Ingresa una contraseña VÁLIDA de al menos 8 caracteres. Puede utilizar números y/o letras");
        }

        UsuarioEntidad u = usuarioRepositorio.buscarPorEmail(email);
        if (u != null) {
            if (u.getEmail().equals(email)) {
                throw new MiExcepcion("El email ingresado ya se encuentra registrado."
                        + "INGRESE UN NUEVO EMAIL!");
            }
        }
    }

    /*
        * Este metodo busca encontrar el usuario y lo transforma en un usuario 
        * de Spring Security
        * Este sirve para hacer el login y a partir de ahi determinar que permisos tendra
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioEntidad usuario = usuarioRepositorio.buscarPorEmail(email);

        /*
        * Aqui convertimos al usuario en un dominio de SpringSecurity. Es decir se crea un usuario que va a necesitar un nombre, contraseNa 
        * y ciertos permisos que los vamos a crear con GrantedAuthority basados en ciertas caracteristicas 
         */
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            //Buscamos atrapar al usuario y guadarlo en  la sessionwweb
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }
}
