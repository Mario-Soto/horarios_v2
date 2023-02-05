package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;

public interface AreaPropedeuticaService {
    public List<AreaPropedeutica> getAreasPropedeuticas();

    public void guardar(AreaPropedeutica area);

    public void eliminar(AreaPropedeutica area);

    public AreaPropedeutica getAreaPropedeutica(AreaPropedeutica area);

    public AreaPropedeutica getAreaPropedeutica(Long idArea);

    public AreaPropedeutica getAreaPropedeutica(Integer idArea);
}
