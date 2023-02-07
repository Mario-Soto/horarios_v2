package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;

import edu.uaeh.horarios2.domain.Materia;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MateriaPropedeutico implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMateriaPropedeutico;
    @ManyToOne
    @JoinColumn(name = "materia")
    private Materia materia;
    @ManyToOne
    @JoinColumn(name = "area")
    private AreaPropedeutica area;
    @ManyToOne
    @JoinColumn(name = "materia_origen")
    private Materia materiaOrigen;
}
