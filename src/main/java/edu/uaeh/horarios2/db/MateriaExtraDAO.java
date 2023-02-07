package edu.uaeh.horarios2.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Grupo;

public interface MateriaExtraDAO extends CrudRepository<MateriaExtra, Long> {
    Iterable<MateriaExtra> findAllByGrupo(Grupo grupo);

    Optional<MateriaExtra> findByGrupoAndMateriaOrigen(Grupo grupo, Materia origen);
}
