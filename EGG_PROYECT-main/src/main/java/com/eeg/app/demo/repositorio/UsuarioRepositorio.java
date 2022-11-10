package com.eeg.app.demo.repositorio;

import com.eeg.app.demo.entidad.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, String> {

    @Query("SELECT u FROM UsuarioEntidad u WHERE u.email LIKE %:email%")
    public UsuarioEntidad buscarPorEmail(@Param(value = "email") String email);
}
