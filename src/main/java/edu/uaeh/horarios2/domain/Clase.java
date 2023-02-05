package edu.uaeh.horarios2.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Clase implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClase;
    @ManyToOne
    @JoinColumn(name = "grupo")
    @ToString.Exclude
    private Grupo grupo;
    @ManyToOne
    @JoinColumn(name = "docente")
    @ToString.Exclude
    private Docente docente;
    @ManyToOne
    @JoinColumn(name = "materia")
    private Materia materia;
    @OneToMany(mappedBy = "clase")
    private List<Sesion> sesiones;


    public Boolean esPracticasOServicio(){
        return this.getMateria().esPracticasOServicio();
    }

    public Boolean esPracticasProfesionales(){
        return this.getMateria().esPracticasProfesionales();
    }

    public Boolean esServicioSocial(){
        return this.getMateria().esServicioSocial();
    }

    public Boolean esOptativa(){
        return this.getMateria().esOptativa();
    }

    public Boolean esPropedeutica(){
        return this.getMateria().esPropedeutica();
    }

    public Boolean esIdiomas(){
        return this.getMateria().esIdiomas();
    }
}
