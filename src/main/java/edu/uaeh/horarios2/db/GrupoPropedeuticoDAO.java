package edu.uaeh.horarios2.db;

import java.util.Optional;
import edu.uaeh.horarios2.domain.catalogos.GrupoPropedeutico;
import edu.uaeh.horarios2.domain.Grupo;
import org.springframework.data.repository.CrudRepository;

public interface GrupoPropedeuticoDAO extends CrudRepository<GrupoPropedeutico, Long>{
    Optional<GrupoPropedeutico> findByGrupo(Grupo grupo);
}
