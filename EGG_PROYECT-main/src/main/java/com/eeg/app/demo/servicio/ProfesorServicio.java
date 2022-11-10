package com.eeg.app.demo.servicio;

import com.eeg.app.demo.entidad.ProfesorEntidad;
import java.util.List;

public interface ProfesorServicio {

    /*===CRUD BASICO===*/
//    Crear
    ProfesorEntidad guardarProfesor(String nombre, String apellido, String email, String nacionalidad, String tecnologiaDominante, String Stack);

//     Leer
    List<ProfesorEntidad> listarProfesores();

    ProfesorEntidad buscarProfesorPorEmail(String email);

    ProfesorEntidad buscarProfesorPorId(Long id);

//      Modificar   
    void modificarProfesor(Long id, String nombre, String apellido, String email, String nacionalidad, String tecnologiaDominante, String Stack);

//      Eliminar
    void eliminarProfesor(Long id);

}
