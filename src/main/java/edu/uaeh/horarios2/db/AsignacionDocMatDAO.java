package edu.uaeh.horarios2.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AsignacionDocMat;

public interface AsignacionDocMatDAO extends CrudRepository<AsignacionDocMat, Long>{
    List<AsignacionDocMat> findAllByDocente(Docente docente);
    List<AsignacionDocMat> findAllByMateria(Materia materia);
    List<AsignacionDocMat> findAllByDocenteAndEstatus(Docente docente, Boolean estatus);
    List<AsignacionDocMat> findAllByMateriaAndEstatus(Materia materia, Boolean estatus);
}
