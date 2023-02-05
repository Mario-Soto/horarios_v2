package edu.uaeh.horarios2.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sesion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSesion;
    @ManyToOne
    @JoinColumn(name = "clase")
    @ToString.Exclude
    private Clase clase;
    private Integer duracion;
    @ManyToOne
    @JoinColumn(name = "aula")
    @ToString.Exclude
    private Aula aula;
    @ManyToOne
    @JoinColumn(name = "inicio")
    private Timeslot inicio;

    public Boolean equalsInTime(Sesion sesion) {
        Integer diaA = this.inicio.getDia();
        Integer inicioA = this.inicio.getHoraInicio();
        Integer finA = inicioA + this.duracion;

        Integer diaB = sesion.getInicio().getDia();
        Integer inicioB = sesion.getInicio().getHoraInicio();
        Integer finB = inicioB + sesion.getDuracion();

        if(diaA == diaB){
            if (inicioA <= inicioB && inicioB < finA) {
                return true;
            }
            if (inicioB <= inicioA && inicioA < finB) {
                return true;
            }
        }
        
        return false;
    }

    public Integer getFinSesion(){
        return this.inicio.getHoraInicio() + this.duracion;
    }
}
