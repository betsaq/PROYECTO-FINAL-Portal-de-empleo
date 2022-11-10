package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.PaisEntidad;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.repositorio.PaisRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaisServicioImpl {

    @Autowired
    private PaisRepositorio paisRepositorio;

    @Transactional
    public PaisEntidad guardarPais(String nombre) throws MiExcepcion {
        validar(nombre);
        PaisEntidad pais = new PaisEntidad();
        pais.setNombre(nombre);
        paisRepositorio.save(pais);
        return pais;
    }

    @Transactional(readOnly = true)
    public List<PaisEntidad> listarPaises() {
        List<PaisEntidad> pais = new ArrayList();
        pais = paisRepositorio.findAll();
        return pais;
    }

    @Transactional(readOnly = true)
    public PaisEntidad buscarPaisPorNombre(String nombre) {
        PaisEntidad entidad = paisRepositorio.buscarPorNombre(nombre);
        return entidad;
    }

    public PaisEntidad buscarPaisPorId(String id) {

        Optional<PaisEntidad> respuesta = paisRepositorio.findById(id);

        PaisEntidad pais = respuesta.get();
        if (!respuesta.isPresent()) {
            return null;
        }
        return pais;
    }

    @Transactional
    public void modificarPais(String id, String nombre) throws MiExcepcion {

        validar(nombre);

        Optional<PaisEntidad> respuesta = paisRepositorio.findById(id);

        PaisEntidad pais = null;
        if (respuesta.isPresent()) {
            pais = respuesta.get();
            pais.setNombre(nombre);
            paisRepositorio.save(pais);
        }
    }

    @Transactional
    public void eliminarPais(String id) {
        Optional<PaisEntidad> respuesta = paisRepositorio.findById(id);

        if (respuesta.isPresent()) {
            PaisEntidad entidad = respuesta.get();
            paisRepositorio.delete(entidad);
        }
    }

    private void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null || nombre.matches("^([A-Z]{1}[a-záéíóú]+[ ]?){1,3}$") == false) {
            throw new MiExcepcion("Ingrese un PAÍS VÁLIDO");
        }
    }
}
