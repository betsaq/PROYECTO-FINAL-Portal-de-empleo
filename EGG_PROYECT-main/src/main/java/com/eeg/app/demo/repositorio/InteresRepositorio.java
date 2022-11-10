package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.InteresEntidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresRepositorio extends JpaRepository<InteresEntidad, String> {

    @Query("SELECT i FROM InteresEntidad i WHERE i.Interes LIKE %:nombre%")
    public List<InteresEntidad> buscarInteresesPorNombre(@Param(value = "nombre") String nombre);

    @Query("SELECT i FROM InteresEntidad i WHERE i.Nombreusuario LIKE %:nombre%")
    public List<InteresEntidad> listarInteresesPorUsuario(@Param(value = "nombre") String nombre);
}
