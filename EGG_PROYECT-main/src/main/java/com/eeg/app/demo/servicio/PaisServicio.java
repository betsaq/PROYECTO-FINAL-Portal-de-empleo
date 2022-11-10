package com.eeg.app.demo.servicio;

import com.eeg.app.demo.entidad.PaisEntidad;
import java.util.List;

public interface PaisServicio {

    /*===CRUD BASICO===*/
//    Crear
    PaisEntidad guardarPais(String nombre);

//     Leer
    List<PaisEntidad> listarPaises();

    PaisEntidad buscarPaisPorEmail(String email);

    PaisEntidad buscarPaisPorId(Long id);

//      Modificar   
    void modificarPais(String id, String nombre);

//      Eliminar
    void eliminarPais(String id);

}
