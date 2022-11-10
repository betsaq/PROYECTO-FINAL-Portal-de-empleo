package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.InteresEntidad;
import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.repositorio.InteresRepositorio;
import com.eeg.app.demo.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteresServicio {

    @Autowired
    private InteresRepositorio interesRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void guardarInteres(String nombre, String email) throws MiExcepcion {

        InteresEntidad interes = new InteresEntidad();
        interes.setInteres(nombre);

        UsuarioEntidad usuario = usuarioRepositorio.buscarPorEmail(email);
        interes.setNombreusuario(usuario.getNombre());

        interes.setUsuario(usuario);

        InteresEntidad i = interesRepositorio.save(interes);
        usuario.agregarIntereses(i);
    }

    //LISTAR  (BUSCA LAS CAPACITACIONES REGISTRADAS PARA MOSTRARLAS EN EL LISTADO DEL ADMIN)
    @Transactional(readOnly = true)
    public List<InteresEntidad> listarInteres() {
        List<InteresEntidad> interes = new ArrayList();
        interes = interesRepositorio.findAll();
        return interes;
    }

    //LISTAR  (BUSCA LAS CAPACITACIONES REGISTRADAS PARA MOSTRARLAS EN EL LISTADO  USUARIOS
    @Transactional(readOnly = true)
    public List<InteresEntidad> listarInteresesPorUsuario(String id) {

        UsuarioEntidad usuario = usuarioRepositorio.findById(id).get();
        List<InteresEntidad> listaIntereses = interesRepositorio.listarInteresesPorUsuario(usuario.getNombre());
        return listaIntereses;
    }

    @Transactional
    public void modificarInteres(String Id, String nombre, String email) throws MiExcepcion {
        Optional<InteresEntidad> respuesta = interesRepositorio.findById(Id);
        InteresEntidad interes = null;
        if (respuesta.isPresent()) {
            interes = respuesta.get();
            interes.setInteres(nombre);
            UsuarioEntidad usuario = usuarioRepositorio.buscarPorEmail(email);
            interes.setNombreusuario(usuario.getNombre());
            interesRepositorio.save(interes);
        }
    }

    @Transactional
    public void eliminarInteres(String id) throws Exception {

        validarId(id);
        InteresEntidad respuesta = interesRepositorio.getOne(id);
        try {
            UsuarioEntidad u = respuesta.getUsuario();
            u.eliminarIntereses(respuesta);
            interesRepositorio.delete(respuesta);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //BUSCAR POR NOMBRE
    @Transactional(readOnly = true)
    public List<InteresEntidad> buscarCapacitacionPorNombre(String nombre) {
        List<InteresEntidad> intereses_lista = interesRepositorio.buscarInteresesPorNombre(nombre);
        return intereses_lista;
    }

    public InteresEntidad getOne(String id) {
        return interesRepositorio.getOne(id);
    }

    private void validarId(String Id) throws MiExcepcion {
        if (Id.isEmpty() || Id == null) {
            throw new MiExcepcion("no existe una herramienta disponible para ese interés");
        }
    }
}
