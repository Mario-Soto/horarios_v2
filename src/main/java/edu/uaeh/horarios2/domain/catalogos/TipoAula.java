package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import edu.uaeh.horarios2.domain.Aula;
import lombok.Data;

@Data
@Entity
public class TipoAula implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoAula;
    private String tipo;
    @OneToMany(mappedBy = "tipo")
    private List<Aula> aulas;
}
