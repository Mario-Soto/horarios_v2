package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;

import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Grupo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MateriaExtra implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMateriaExtra;
    @ManyToOne
    @JoinColumn(name = "grupo")
    private Grupo grupo;
    @ManyToOne
    @JoinColumn(name = "materia_origen")
    private Materia materiaOrigen;
    @ManyToOne
    @JoinColumn(name = "materia")
    private Materia materia;
    @ManyToOne
    @JoinColumn(name = "docente")
    private Docente docente;
}
