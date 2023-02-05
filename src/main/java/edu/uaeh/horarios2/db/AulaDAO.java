package edu.uaeh.horarios2.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import edu.uaeh.horarios2.domain.Aula;
import edu.uaeh.horarios2.domain.catalogos.TipoAula;

public interface AulaDAO extends CrudRepository<Aula, Long>{
    List<Aula> findAllByTipoAndEstatus(TipoAula tipo, Boolean estatus);
}
