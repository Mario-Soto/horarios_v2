package edu.uaeh.horarios2.db;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;

public interface MateriaDAO extends CrudRepository<Materia, Long>{
    Iterable<Materia> findAllByProgramaEducativo(ProgramaEducativo programaEducativo);
    Iterable<Materia> findAllByProgramaEducativoAndTipoMateria(ProgramaEducativo programaEducativo, TipoMateria tipoMateria);
    Iterable<Materia> findAllByProgramaEducativoAndSemestre(ProgramaEducativo programaEducativo, Integer semestre);
    Iterable<Materia> findAllByProgramaEducativoAndSemestreAndTipoMateriaIn(ProgramaEducativo programaEducativo, Integer semestre, Collection<TipoMateria> tipoMaterias);
    Iterable<Materia> findAllByTipoMateria(TipoMateria tipoMateria);
    // List<Materia> findAllByTipoMateria(TipoMateria tipoMateria);
}
