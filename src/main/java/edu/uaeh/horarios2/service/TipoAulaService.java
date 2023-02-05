package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.catalogos.TipoAula;

public interface TipoAulaService {
    public List<TipoAula> getTiposAula();

    public void guardar(TipoAula tipoAula);

    public void eliminar(TipoAula tipoAula);

    public TipoAula getTipoAula(TipoAula tipoAula);

    public TipoAula getTipoAula(Long idTipo);

    public TipoAula getTipoAula(Integer idTipo);
}
