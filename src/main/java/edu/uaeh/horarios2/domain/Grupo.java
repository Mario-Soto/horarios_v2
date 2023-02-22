package edu.uaeh.horarios2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;
    @ManyToOne
    @JoinColumn(name = "programa_educativo")
    private ProgramaEducativo programaEducativo;
    private Integer semestre;
    private Integer grupo;
    private Integer turno;
    @OneToMany(mappedBy = "grupo", fetch = FetchType.EAGER)
    private List<Clase> clases;
    @ManyToOne
    @JoinTable(name = "grupo_propedeutico",
        joinColumns = @JoinColumn(name = "grupo"),
        inverseJoinColumns = @JoinColumn(name = "area_propedeutica")
    )
    private AreaPropedeutica areaPropedeutica;

    @OneToMany(mappedBy = "grupo")
    private List<MateriaExtra> materiaExtras;


    public Materia getMateriaDestino(Materia origen){
        for(MateriaExtra materia : this.materiaExtras){
            if(materia.getMateriaOrigen().equals(origen)){
                return materia.getMateria();
            }
        }
        return null;
    }

    public Boolean tieneOptativas() {
        for (Clase clase : this.clases) {
            if (clase.getMateria().getMateria().contains("OPTATIVA")) {
                return true;
            }
        }
        return false;
    }

    public List<Clase> getOptativas() {
        List<Clase> optativas = new ArrayList<>();
        for (Clase clase : this.getClases()) {
            if (clase.getMateria().getTipoMateria().getTipo().equals("Optativa")) {
                optativas.add(clase);
            }
        }
        return optativas;
    }

    public Integer getHorasSemana() {
        Integer horas = 0;
        for (Clase clase : this.getClases()) {
            if(!clase.esPracticasOServicio()){
                horas += clase.getMateria().getHorasSemana();
            }
        }
        return horas;
    }

    public Integer getDiasSemana() {
        Integer horasSemana = this.getHorasSemana();
        if(horasSemana <= 2){
            return 1;
        } else if (horasSemana <= 10) {
            return 2;
        } else if (horasSemana <= 15) {
            return 3;
        } else if (horasSemana <= 25) {
            return 4;
        } else {
            return 5;
        }
    }

    public Integer[] getHorasPermitidas() {
        Integer[] horasPermitidas = new Integer[2];
        Integer horas = this.getHorasSemana();
        Integer dias = this.getDiasSemana();
        if (horas % dias == 0) {
            horasPermitidas[1] = (int) Math.ceil((horas + 1) / (dias + 0.0));
        }else{
            horasPermitidas[1] = (int) Math.ceil(horas / (dias + 0.0));
        }
        horasPermitidas[0] = horasPermitidas[1] - 2;

        return horasPermitidas;
    }
}
