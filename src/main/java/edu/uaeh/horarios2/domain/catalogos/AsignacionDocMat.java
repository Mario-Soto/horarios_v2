package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Materia;
import lombok.Data;

@Data
@Entity
public class AsignacionDocMat implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacionDocMat;
    @ManyToOne
    @JoinColumn(name = "docente")
    private Docente docente;
    @ManyToOne
    @JoinColumn(name = "materia")
    private Materia materia;
    private Boolean estatus;
}
