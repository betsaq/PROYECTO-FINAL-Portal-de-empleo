package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.CapacitacionEntidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacitacionRepositorio extends JpaRepository<CapacitacionEntidad, String> {

    @Query("SELECT c FROM CapacitacionEntidad c WHERE c.nombre LIKE %:nombre%")
    public List<CapacitacionEntidad> buscarCapacitacionPorNombre(@Param(value = "nombre") String nombre);

    @Query("SELECT c FROM CapacitacionEntidad c WHERE c.nombreUsuario LIKE %:nombre%")
    public List<CapacitacionEntidad> listarCapacitacionesPorUsuario(@Param(value = "nombre") String nombre);

}
