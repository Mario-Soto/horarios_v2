package edu.uaeh.horarios2.db;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.catalogos.TipoAula;

public interface TipoAulaDAO extends CrudRepository<TipoAula, Long>{
    
}
