package com.eeg.app.demo.initDB;

import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.enumm.Rol;
import com.eeg.app.demo.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ismael Se busca crear un usuario Admin (Prepersistido)
 */
@Service
public class PrePersistencia implements CommandLineRunner {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Value("${cust.data.email}")
    private String email;
    @Value("${cust.data.password}")
    private String pass;

    @Value("${cust.data.email2}")
    private String email2;
    @Value("${cust.data.password2}")
    private String pass2;

    @Override
    public void run(String... arg) throws Exception {

        /*Lista para agregar usuarios y luego persistirlos*/
        List<UsuarioEntidad> listaUsuariosPersistidos = new ArrayList<>();
        /*Datos admin*/
        UsuarioEntidad userAdmin = new UsuarioEntidad();
        userAdmin.setNombre("Admin");
        userAdmin.setApellido("Admin");
        userAdmin.setPassword(new BCryptPasswordEncoder().encode(pass));
        userAdmin.setEmail(email);
        userAdmin.setAlta(new Date());
        userAdmin.setRol(Rol.ADMIN);
        listaUsuariosPersistidos.add(userAdmin);

        /*Datos usuario prueba*/
        UsuarioEntidad usuarioPrueba = new UsuarioEntidad();
        usuarioPrueba.setNombre("Ismael");
        usuarioPrueba.setApellido("Rosas");
        usuarioPrueba.setPassword(new BCryptPasswordEncoder().encode(pass2));
        usuarioPrueba.setEmail(email2);
        usuarioPrueba.setAlta(new Date());
        usuarioPrueba.setRol(Rol.USER);
        listaUsuariosPersistidos.add(usuarioPrueba);

        listaUsuariosPersistidos.forEach(u -> {
            usuarioRepositorio.save(u);
        });
    }
}
