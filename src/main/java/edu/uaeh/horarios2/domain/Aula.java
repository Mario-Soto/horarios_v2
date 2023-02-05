package edu.uaeh.horarios2.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import edu.uaeh.horarios2.domain.catalogos.TipoAula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"tipo"})
public class Aula implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAula;
    private Integer edificio;
    private String salon;
    @ManyToOne
    @JoinColumn(name = "tipo")
    private TipoAula tipo;
    private Boolean estatus;
    private Integer capacidad;
}
