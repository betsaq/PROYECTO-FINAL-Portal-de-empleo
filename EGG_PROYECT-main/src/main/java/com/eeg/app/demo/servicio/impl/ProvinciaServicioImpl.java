package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.ProvinciaEntidad;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.repositorio.ProvinciaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinciaServicioImpl {

    @Autowired
    private ProvinciaRepositorio provinciaRepositorio;

    @Transactional
    public ProvinciaEntidad guardarProvincia(String nombre) throws MiExcepcion {
        validar(nombre);
        ProvinciaEntidad provincia = new ProvinciaEntidad();
        provincia.setNombre(nombre);
        provinciaRepositorio.save(provincia);
        return provincia;
    }

    @Transactional(readOnly = true)
    public List<ProvinciaEntidad> listarProvincias() {
        List<ProvinciaEntidad> provincia = new ArrayList();
        provincia = provinciaRepositorio.findAll();
        return provincia;

    }

    @Transactional(readOnly = true)
    public ProvinciaEntidad buscarProvinciaPorNombre(String nombre) {
        ProvinciaEntidad entidad = provinciaRepositorio.buscarPorNombre(nombre);
        return entidad;
    }

    public ProvinciaEntidad buscarProvinciaPorId(String id) {

        Optional<ProvinciaEntidad> respuesta = provinciaRepositorio.findById(id);

        ProvinciaEntidad provincia = respuesta.get();
        if (!respuesta.isPresent()) {
            return null;
        }
        return provincia;
    }

    @Transactional
    public void modificarProvincia(String id, String nombre) throws MiExcepcion {

        validar(nombre);

        Optional<ProvinciaEntidad> respuesta = provinciaRepositorio.findById(id);

        ProvinciaEntidad provincia = null;
        if (respuesta.isPresent()) {
            provincia = respuesta.get();
            provincia.setNombre(nombre);
            provinciaRepositorio.save(provincia);
        }
    }

    @Transactional
    public void eliminarProvincia(String id) {
        Optional<ProvinciaEntidad> respuesta = provinciaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            ProvinciaEntidad entidad = respuesta.get();
            provinciaRepositorio.delete(entidad);
        }
    }

    private void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null || nombre.matches("^([A-Z]{1}[a-záéíóú]+[ ]?){1,3}$") == false) {
            throw new MiExcepcion("Ingrese un PAÍS VÁLIDO");
        }
    }
}
