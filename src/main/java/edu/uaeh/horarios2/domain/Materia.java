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

import org.hibernate.annotations.Where;

import edu.uaeh.horarios2.domain.catalogos.AsignacionDocMat;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Materia implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMateria;
    private String materia;
    @ManyToOne
    @JoinColumn(name = "programa_educativo")
    private ProgramaEducativo programaEducativo;
    private Integer semestre;
    private Integer horasSemana;
    @ManyToOne
    @JoinColumn(name = "tipo_materia")
    private TipoMateria tipoMateria;
    @Where(clause = "estatus = 1")
    @OneToMany(mappedBy = "materia")
    private List<AsignacionDocMat> asignaciones;

    public Docente getRandomDocentePermitido(){
        return this.asignaciones.get((int) (Math.random() * this.asignaciones.size())).getDocente();
    }

    public Boolean esPracticasOServicio(){
        return this.getTipoMateria().getTipo().equals("Prácticas profesionales") || this.getTipoMateria().getTipo().equals("Servicio social");
    }

    public Boolean esPracticasProfesionales(){
        return this.getTipoMateria().getTipo().equals("Prácticas profesionales");
    }

    public Boolean esServicioSocial(){
        return this.getTipoMateria().getTipo().equals("Servicio social");
    }

    public Boolean esOptativa(){
        return this.getTipoMateria().getTipo().equals("Optativa");
    }

    public Boolean esPropedeutica(){
        return this.getTipoMateria().getTipo().equals("Propedéutico");
    }

    public Boolean esIdiomas(){
        return this.getTipoMateria().getTipo().equals("Idiomas");
    }
}
