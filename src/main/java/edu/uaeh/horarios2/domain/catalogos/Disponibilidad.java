package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Timeslot;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Disponibilidad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilidad;
    @ManyToOne
    @JoinColumn(name = "docente")
    private Docente docente;
    @ManyToOne
    @JoinColumn(name = "timeslot")
    private Timeslot slot;
    private Boolean estatus;

}
