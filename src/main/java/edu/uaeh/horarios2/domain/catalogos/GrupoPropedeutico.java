package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;

import edu.uaeh.horarios2.domain.Grupo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class GrupoPropedeutico implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupoPropedeutico;
    @ManyToOne
    @JoinColumn(name = "grupo")
    private Grupo grupo;
    @ManyToOne
    @JoinColumn(name = "area_propedeutica")
    private AreaPropedeutica areaPropedeutica;
}
