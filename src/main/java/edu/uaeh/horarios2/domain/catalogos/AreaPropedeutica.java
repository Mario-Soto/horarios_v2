package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

import edu.uaeh.horarios2.domain.Materia;
import lombok.Data;

@Data
@Entity
public class AreaPropedeutica implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAreaPropedeutica;
    private String area;
    private String nombre;
    private Integer estado;
    @OneToMany
    @JoinTable(name = "materia_propedeutico",
        joinColumns = @JoinColumn(name = "area"),
        inverseJoinColumns = @JoinColumn(name = "materia"))
    private List<Materia> materias;
}
