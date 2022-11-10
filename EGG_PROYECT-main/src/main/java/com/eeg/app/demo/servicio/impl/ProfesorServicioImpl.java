package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.ProfesorEntidad;
import com.eeg.app.demo.repositorio.ProfesorRepositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesorServicioImpl {

    @Autowired
    public ProfesorRepositorio profesorRepositorio;

    @Transactional
    public ProfesorEntidad guardarProfesor(String nombre, String apellido, String email, String nacionalidad, String tecnologiaDominante, String Stack) {

        ProfesorEntidad entidad = null;
        try {
            entidad = new ProfesorEntidad();
            entidad.setNombre(nombre);
            entidad.setApellido(apellido);
            entidad.setEmail(email);
            entidad.setNacionalidad(nacionalidad);
            entidad.setTecnologiaDominante(tecnologiaDominante);
            entidad.setStack(Stack);

            profesorRepositorio.save(entidad);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
        return entidad;
    }

    @Transactional(readOnly = true)
    public List<ProfesorEntidad> listarProfesores() {
        return profesorRepositorio.findAll();
    }

  
    @Transactional(readOnly = true)
    public ProfesorEntidad buscarProfesorPorEmail(String email) {
        return profesorRepositorio.buscarPorEmail(email);
    }

    
    @Transactional
    public void modificarProfesor(Long id, String nombre, String apellido, String email, String nacionalidad, String tecnologiaDominante, String Stack) {
        Optional<ProfesorEntidad> obj = profesorRepositorio.findById(id);

        if (obj.isPresent()) {
            ProfesorEntidad entidad = obj.get();
            entidad.setNombre(nombre);
            entidad.setApellido(apellido);
            entidad.setEmail(email);
            entidad.setNacionalidad(nacionalidad);
            entidad.setTecnologiaDominante(tecnologiaDominante);
            entidad.setStack(Stack);
            profesorRepositorio.save(entidad);
        }
    }

  
    @Transactional
    public void eliminarProfesor(Long id) {
        Optional<ProfesorEntidad> obj = profesorRepositorio.findById(id);

        if (obj.isPresent()) {
            ProfesorEntidad entidad = obj.get();
            entidad.setBaja(true);
        }
    }

  
    public ProfesorEntidad buscarProfesorPorId(Long id) {
        Optional<ProfesorEntidad> obj = profesorRepositorio.findById(id);

        ProfesorEntidad entidad = obj.get();
        if (!obj.isPresent()) {
            return null;
        }
        return entidad;
    }
}
