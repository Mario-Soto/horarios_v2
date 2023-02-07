package edu.uaeh.horarios2.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import edu.uaeh.horarios2.domain.catalogos.MateriaPropedeutico;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;

public interface MateriaPropedeuticoDAO extends CrudRepository<MateriaPropedeutico, Long> {
    Iterable<MateriaPropedeutico> findAllByArea(AreaPropedeutica area);

    Optional<MateriaPropedeutico> findByAreaAndMateriaOrigen(AreaPropedeutica area, Materia origen);
}
