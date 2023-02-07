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
import lombok.ToString.Exclude;

@Data
@Entity
public class AreaPropedeutica implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAreaPropedeutica;
    private String area;
    private String nombre;
    private Integer estatus;
    @OneToMany
    @JoinTable(name = "materia_propedeutico",
        joinColumns = @JoinColumn(name = "area"),
        inverseJoinColumns = @JoinColumn(name = "materia"))
    @Exclude
    private List<Materia> materias;
}
