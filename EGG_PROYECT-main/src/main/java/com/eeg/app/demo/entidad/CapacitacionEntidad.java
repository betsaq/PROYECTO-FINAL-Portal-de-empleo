package com.eeg.app.demo.entidad;

import com.eeg.app.demo.enumm.NivelCapacitacion;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
public class CapacitacionEntidad implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private NivelCapacitacion nivel;

    @Column(name = "fecha_de_egreso")
    private String fechaEgreso;

    private String academia;

    private String nombreUsuario;

    @OneToOne
    private UsuarioEntidad usuario;

    public CapacitacionEntidad() {
    }

}
