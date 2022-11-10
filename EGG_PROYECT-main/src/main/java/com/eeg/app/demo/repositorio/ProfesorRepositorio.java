package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.ProfesorEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ProfesorRepositorio extends JpaRepository<ProfesorEntidad, Long> {

    @Query("SELECT p FROM ProfesorEntidad p WHERE p.email LIKE %:email%")
    public ProfesorEntidad buscarPorEmail(@RequestParam String email);
}
