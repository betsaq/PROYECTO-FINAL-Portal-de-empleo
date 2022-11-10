package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.PaisEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepositorio extends JpaRepository<PaisEntidad, String> {

    @Query("SELECT p FROM PaisEntidad p WHERE p.nombre LIKE %:nombre%")
    public PaisEntidad buscarPorNombre(@Param(value = "nombre") String nombre);
}
