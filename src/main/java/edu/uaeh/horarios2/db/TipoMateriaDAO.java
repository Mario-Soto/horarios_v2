package edu.uaeh.horarios2.db;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.catalogos.TipoMateria;

public interface TipoMateriaDAO extends CrudRepository<TipoMateria, Long>{
    
}
