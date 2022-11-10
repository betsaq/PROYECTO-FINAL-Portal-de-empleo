package com.eeg.app.demo.servicio.impl;

import com.eeg.app.demo.entidad.CapacitacionEntidad;
import com.eeg.app.demo.entidad.UsuarioEntidad;
import com.eeg.app.demo.enumm.NivelCapacitacion;
import com.eeg.app.demo.excepciones.MiExcepcion;
import com.eeg.app.demo.repositorio.CapacitacionRepositorio;
import com.eeg.app.demo.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CapacitacionServicio {

    @Autowired
    private CapacitacionRepositorio capacitacionRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //GUARDAR UNA NUEVA CAPACITACION QUE LO REALIZA EL ADMIN
    @Transactional
    public void guardarCapacitacion(String nombre, String nivel, String email, String fechaEgreso, String institucion) {

        CapacitacionEntidad capacitacionRealizada = new CapacitacionEntidad();
        
        capacitacionRealizada.setNombre(nombre);

        switch (nivel) {
            case "Basico":
                capacitacionRealizada.setNivel(NivelCapacitacion.BASICO);
                break;
            case "Intermedio":
                capacitacionRealizada.setNivel(NivelCapacitacion.INTERMEDIO);
                break;
            case "Avanzado":
                capacitacionRealizada.setNivel(NivelCapacitacion.AVANZDO);
                break;
            default:
                throw new AssertionError();
        }

        UsuarioEntidad usuario = usuarioRepositorio.buscarPorEmail(email);
        capacitacionRealizada.setNombreUsuario(usuario.getNombre());

        capacitacionRealizada.setFechaEgreso(fechaEgreso);
        capacitacionRealizada.setAcademia(institucion);
        capacitacionRealizada.setUsuario(usuario);

        CapacitacionEntidad capacitacion = capacitacionRepositorio.save(capacitacionRealizada);
        usuario.agregarCapacitacion(capacitacion);
    }

    //LISTAR  (BUSCA LAS CAPACITACIONES REGISTRADAS PARA MOSTRARLAS EN EL LISTADO DEL ADMIN)
    @Transactional(readOnly = true)
    public List<CapacitacionEntidad> listarCapacitaciones() {
        List<CapacitacionEntidad> capacitaciones = new ArrayList();
        capacitaciones = capacitacionRepositorio.findAll();
        return capacitaciones;
    }

    //LISTAR  (BUSCA LAS CAPACITACIONES REGISTRADAS PARA MOSTRARLAS EN EL LISTADO  USUARIOS
    @Transactional(readOnly = true)
    public List<CapacitacionEntidad> listarCapacitacionesPorUsuario(String id) {

        Optional<UsuarioEntidad> respuesta = usuarioRepositorio.findById(id);

        try {
            UsuarioEntidad usuario = null;
            List<CapacitacionEntidad> listaCapacitaciones = null;
            if (respuesta.isPresent()) {
                usuario = respuesta.get();
                String nombre = usuario.getNombre();
                try {
                    listaCapacitaciones = capacitacionRepositorio.listarCapacitacionesPorUsuario(nombre);
                    return listaCapacitaciones;
                } catch (Exception e) {
                    return listaCapacitaciones;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public CapacitacionEntidad buscarCapacitacionPorId(String id) {
        CapacitacionEntidad capacitacion = capacitacionRepositorio.findById(id).get();
        return capacitacion;
    }

    //     BUSCAR POR NOMBRE
    @Transactional(readOnly = true)
    public List<CapacitacionEntidad> buscarCapacitacionPorNombre(String nombre) {
        List<CapacitacionEntidad> capacitacion_list = capacitacionRepositorio.buscarCapacitacionPorNombre(nombre);
        return capacitacion_list;
    }

    //MODIFICAR
    @Transactional
    public void modificarCapacitacion(String id, String nombre, String nivel, String email, String fechaEgreso, String institucion) throws MiExcepcion {

        Optional<CapacitacionEntidad> respuesta = capacitacionRepositorio.findById(id);

        CapacitacionEntidad capacitacion = null;

        if (respuesta.isPresent()) {
            capacitacion = respuesta.get();
            capacitacion.setNombre(nombre);

            switch (nivel) {
                case "Basico":
                    capacitacion.setNivel(NivelCapacitacion.BASICO);
                    break;
                case "Intermedio":
                    capacitacion.setNivel(NivelCapacitacion.INTERMEDIO);
                    break;
                case "Avanzado":
                    capacitacion.setNivel(NivelCapacitacion.AVANZDO);
                    break;
                default:
                    throw new AssertionError();
            }

            UsuarioEntidad usuario = usuarioRepositorio.buscarPorEmail(email);
            capacitacion.setNombreUsuario(usuario.getNombre());
            capacitacion.setFechaEgreso(fechaEgreso);
            capacitacion.setAcademia(institucion);

            capacitacionRepositorio.save(capacitacion);

        } else {
            throw new MiExcepcion("No se encontró la capacitacion solicitada");
        }
    }

    //ELIMINAR
    @Transactional
    public void eliminarCapacitacion(String id) {
        Optional<CapacitacionEntidad> respuesta = capacitacionRepositorio.findById(id);

        if (respuesta.isPresent()) {
            CapacitacionEntidad entidad = respuesta.get();
            UsuarioEntidad usuario = usuarioRepositorio.findById(entidad.getUsuario().getId()).get();

            usuario.eliminarCapacitacion(entidad);
            capacitacionRepositorio.delete(entidad);
        }
    }

    //VALIDACION
    public void validar(String id, String nivel, Date anioEgreso, UsuarioEntidad idUsuario /*HerramientaEntidad idHerramienta*/) throws MiExcepcion {

        if (id == null || id.isEmpty()) {
            throw new MiExcepcion("El id no puede ser nulo");
        }

        if (nivel == null || nivel.isEmpty()) {
            throw new MiExcepcion("Campo nulo. Seleccione nivel del usuario!");
        }

        if (anioEgreso == null || anioEgreso.after(anioEgreso) || anioEgreso.before(anioEgreso)) {
            throw new MiExcepcion("Año de Egreso mal ingresado. Ingreselo nuevamente!");
        }

        if (idUsuario == null) {
            throw new MiExcepcion("El usuario no puede ser nulo");
        }
    }

    public String generarCertificado() {
        String[] abc = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        String certificadoStr = "Cert/:";

        do {

            int numeroRandom = (int) (Math.floor(Math.random() * 9));
            String numeroRandomStr = String.valueOf(numeroRandom);
            certificadoStr = certificadoStr + numeroRandomStr;

            int letraRandom = (int) (Math.floor(Math.random() * abc.length));
            if (letraRandom % 2 == 0) {
                certificadoStr = certificadoStr + abc[letraRandom];
            } else {
                certificadoStr = certificadoStr + abc[letraRandom].toUpperCase();
            }

        } while (certificadoStr.length() < 15);
        return certificadoStr;
    }
}
