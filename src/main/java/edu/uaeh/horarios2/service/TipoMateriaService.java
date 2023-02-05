package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.catalogos.TipoMateria;

public interface TipoMateriaService {
    public List<TipoMateria> getTiposMateria();

    public void guardar(TipoMateria tipoMateria);

    public void eliminar(TipoMateria tipoMateria);

    public TipoMateria getTipoMateria(TipoMateria tipoMateria);

    public TipoMateria getTipoMateria(Long idTipoMateria);

    public TipoMateria getTipoMateria(Integer idTipoMateria);
}
