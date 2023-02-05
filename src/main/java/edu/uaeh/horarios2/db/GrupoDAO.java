package edu.uaeh.horarios2.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;

public interface GrupoDAO extends CrudRepository<Grupo, Long>{
    Iterable<Grupo> findAllByProgramaEducativo(ProgramaEducativo programaEducativo);
    Iterable<Grupo> findAllByTurno(Integer turno);
    Optional<Grupo> findByProgramaEducativoAndSemestreAndGrupo(ProgramaEducativo pe, Integer semestre, Integer grupo);
}
