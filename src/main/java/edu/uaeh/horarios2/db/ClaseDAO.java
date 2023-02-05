package edu.uaeh.horarios2.db;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;

public interface ClaseDAO extends CrudRepository<Clase, Long>{
    Iterable<Clase> findAllByGrupo(Grupo grupo);
    Iterable<Clase> findAllByDocente(Docente docente);
    Iterable<Clase> findAllByMateria(Materia materia);
}
