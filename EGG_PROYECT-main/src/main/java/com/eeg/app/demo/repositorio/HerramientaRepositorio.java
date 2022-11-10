package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.HerramientaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HerramientaRepositorio extends JpaRepository<HerramientaEntidad, String> {

    @Query("SELECT p FROM HerramientaEntidad p WHERE p.nombre LIKE %:nombre%")
    public HerramientaEntidad buscarPorNombre(@Param(value = "nombre") String nombre);
}
