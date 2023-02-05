package edu.uaeh.horarios2.domain.catalogos;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import edu.uaeh.horarios2.domain.Grupo;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class ProgramaEducativo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProgramaEducativo;
	private String nombre;
	private String nombreCorto;
	private Boolean estatus;
	private Integer semestres;
	@OneToMany(mappedBy = "programaEducativo", fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Grupo> grupos;
}
