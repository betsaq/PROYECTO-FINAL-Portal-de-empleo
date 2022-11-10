package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.HerramientaEntidad;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.repositorio.HerramientaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HerramientaServicioImpl {

    @Autowired
    private HerramientaRepositorio herramientaRepositorio;

    @Transactional
    public HerramientaEntidad guardarHerramienta(String nombre) throws MiExcepcion {
        validar(nombre);
        HerramientaEntidad herramienta = new HerramientaEntidad();
        herramienta.setNombre(nombre);
        herramientaRepositorio.save(herramienta);
        return herramienta;
    }

    @Transactional(readOnly = true)
    public List<HerramientaEntidad> listarHerramientas() {
        List<HerramientaEntidad> herramienta = new ArrayList();
        herramienta = herramientaRepositorio.findAll();
        return herramienta;

    }

    @Transactional(readOnly = true)
    public HerramientaEntidad buscarHerramientaPorNombre(String nombre) {
        HerramientaEntidad entidad = herramientaRepositorio.buscarPorNombre(nombre);
        return entidad;
    }

    public HerramientaEntidad buscarHerramientaPorId(String id) {

        Optional<HerramientaEntidad> respuesta = herramientaRepositorio.findById(id);

        HerramientaEntidad herramienta = respuesta.get();
        if (!respuesta.isPresent()) {
            return null;
        }
        return herramienta;
    }

    @Transactional
    public void modificarHerramienta(String id, String nombre) throws MiExcepcion {

        validar(nombre);

        Optional<HerramientaEntidad> respuesta = herramientaRepositorio.findById(id);

        HerramientaEntidad herramienta = null;
        if (respuesta.isPresent()) {
            herramienta = respuesta.get();
            herramienta.setNombre(nombre);
            herramientaRepositorio.save(herramienta);
        }
    }

    @Transactional
    public void eliminarHerramienta(String id) {
        Optional<HerramientaEntidad> respuesta = herramientaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            HerramientaEntidad entidad = respuesta.get();
            herramientaRepositorio.delete(entidad);
        }
    }

    private void validar(String nombre) throws MiExcepcion {
        //acepta hasta 3 palabras - sin minimo de caracteres
        if (nombre.isEmpty() || nombre == null || nombre.matches("^([A-Z]{1}[a-záéíóú0-9\\D]+[ ]?){1,3}$") == false) {
            throw new MiExcepcion("Ingrese un nombre VÁLIDO de herramienta!");
        }
    }
}
