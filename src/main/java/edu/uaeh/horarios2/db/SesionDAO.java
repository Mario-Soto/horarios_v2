package edu.uaeh.horarios2.db;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Aula;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.domain.Timeslot;

public interface SesionDAO extends CrudRepository<Sesion, Long>{
    Iterable<Sesion> findAllByClase(Clase clase);
    Iterable<Sesion> findAllByAula(Aula aula);
    Iterable<Sesion> findAllByInicio(Timeslot inicio);
}
