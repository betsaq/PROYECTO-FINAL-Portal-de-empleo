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

    
      @Value("${cust.data.email3}")
    private String email3;
    @Value("${cust.data.password3}")
    private String pass3;
    
     @Value("${cust.data.email4}")
    private String email4;
    @Value("${cust.data.password4}")
    private String pass4;
    
    
    
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
        UsuarioEntidad usuarioPrueba2 = new UsuarioEntidad();
        usuarioPrueba2.setNombre("Betty");
        usuarioPrueba2.setApellido("Quevedo Gómez");
        usuarioPrueba2.setPassword(new BCryptPasswordEncoder().encode(pass2));
        usuarioPrueba2.setEmail(email2);
        usuarioPrueba2.setAlta(new Date());
        usuarioPrueba2.setRol(Rol.USER);
        listaUsuariosPersistidos.add(usuarioPrueba2);
        
         /*Datos usuario prueba*/
        UsuarioEntidad usuarioPrueba3 = new UsuarioEntidad();
        usuarioPrueba3.setNombre("Douglas");
        usuarioPrueba3.setApellido("Narinas");
        usuarioPrueba3.setPassword(new BCryptPasswordEncoder().encode(pass3));
        usuarioPrueba3.setEmail(email3);
        usuarioPrueba3.setAlta(new Date());
        usuarioPrueba3.setRol(Rol.USER);
        listaUsuariosPersistidos.add(usuarioPrueba3);
        
        
          /*Datos usuario prueba*/
        UsuarioEntidad usuarioPrueba4 = new UsuarioEntidad();
        usuarioPrueba4.setNombre("Bart");
        usuarioPrueba4.setApellido("Simpsons");
        usuarioPrueba4.setPassword(new BCryptPasswordEncoder().encode(pass4));
        usuarioPrueba4.setEmail(email4);
        usuarioPrueba4.setAlta(new Date());
        usuarioPrueba4.setRol(Rol.USER);
        listaUsuariosPersistidos.add(usuarioPrueba4);
        
        
        

        listaUsuariosPersistidos.forEach(u -> {
            usuarioRepositorio.save(u);
        });
    }
}
