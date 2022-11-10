package com.eeg.app.demo;

import com.eeg.app.demo.servicio.impl.UsuarioServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioServicioImpl usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/usuario/registro").permitAll()
                .antMatchers("/usuario/panel").hasAnyRole("USER", "ADMIN")// Para acceder debes tenes un Rol
                .antMatchers("/usuario/panel/modificar-foto/**").hasRole("USER") // Para acceder denbes debe tener un rol Admin
                .antMatchers("/admin").hasRole("ADMIN") // Para acceder al archvo admin debe tener un rol Admin
                .antMatchers("/pais/**").hasRole("ADMIN") // Para acceder debe tener un rol Admin
                .antMatchers("/provincia/**").hasRole("ADMIN") // Para acceder denbes debe tener un rol Admin
                .antMatchers("/capacitacion/**").hasAnyRole("USER", "ADMIN") // Para acceder denbes debe tener un rol Admin
                .antMatchers("/interes/**").hasAnyRole("USER", "ADMIN") // Para acceder denbes debe tener un rol Admin
                .antMatchers("/herramienta/**").hasRole("ADMIN") // Para acceder denbes debe tener un rol Admin
                .antMatchers("/usuario/listar").hasRole("ADMIN") // Para acceder denbesdebe tener un rol Admin
                .antMatchers("/usuario/busqueda").hasRole("ADMIN") // Para acceder denbes edebe tener un rol Admin
                .antMatchers("/usuario/modificar").hasRole("ADMIN") // Para acceder denbes debe tener un rol Admin
                .antMatchers("/usuario/modificado").hasRole("ADMIN") // Para acceder denbes debe tener un rol Admin
                .and()
                .formLogin()
                .loginPage("/iniciar-sesion")
                .loginProcessingUrl("/logincheck") //--> Para procesar el login, debe coincidir con el acction del form
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/usuario/panel")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/iniciar-sesion") //Al cerrar la secion lo mandamos apra q se logee otra vez
                .permitAll()
                .and().csrf().disable();

    }

}
