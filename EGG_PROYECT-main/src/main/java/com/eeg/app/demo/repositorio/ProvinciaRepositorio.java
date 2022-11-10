package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.ProvinciaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProvinciaRepositorio extends JpaRepository<ProvinciaEntidad, String> {

    @Query("SELECT p FROM ProvinciaEntidad p WHERE p.nombre LIKE %:nombre%")
    public ProvinciaEntidad buscarPorNombre(@Param(value = "nombre") String nombre);
}
