package edu.uaeh.horarios2.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;

public interface ProgramaEducativoDAO extends CrudRepository<ProgramaEducativo, Long>{
    Iterable<ProgramaEducativo> findAllByEstatus(Boolean estatus);
    Optional<ProgramaEducativo> findByNombreCorto(String nombre);
}
