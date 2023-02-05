package edu.uaeh.horarios2.db;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;

public interface DocenteDAO extends CrudRepository<Docente, Long>{
    Iterable<Docente> findAllByTipoEmpleadoAndEstatus(TipoEmpleado tipo, Integer estado);
    Iterable<Docente> findAllByTipoEmpleadoOrNumeroEmpleadoInAndEstatus(TipoEmpleado tipo, Collection<Integer> numeroEmpleado, Integer estado);
    Iterable<Docente> findAllByEstatus(Integer estado);
}
