package com.eeg.app.demo.entidad;

import com.eeg.app.demo.enumm.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Setter
@Getter
public class UsuarioEntidad implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String apellido;
    private String email;
    private String provincia;
    private String pais;

    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CapacitacionEntidad> capacitaciones;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<InteresEntidad> intereses;

    public UsuarioEntidad() {
        this.capacitaciones = new ArrayList<CapacitacionEntidad>();
        this.intereses = new ArrayList<InteresEntidad>();
        this.descripcion = "Hola y bienvenido al nuevo portal, para modificar tu perfil dirigete al menu y selecciona la opcion deseada";
    }

    /**
     * Este metodo setter busca agregar una capacitacion al usuario y a su vez
     * setear en la capacitacion el usuario agregado
     *
     * @param capacitaciones
     *
     */
    public void agregarCapacitacion(CapacitacionEntidad capacitaciones) {
        this.capacitaciones.add(capacitaciones);
    }

    public void eliminarCapacitacion(CapacitacionEntidad capacitaciones) {
        this.capacitaciones.remove(capacitaciones);
    }

    public void agregarIntereses(InteresEntidad intereses) {
        this.intereses.add(intereses);
    }

    public void eliminarIntereses(InteresEntidad intereses) {
        this.intereses.remove(intereses);
    }

}
