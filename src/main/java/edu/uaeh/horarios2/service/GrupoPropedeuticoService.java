package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.GrupoPropedeutico;

public interface GrupoPropedeuticoService {
    public List<GrupoPropedeutico> getGruposPropedeuticos();
    public GrupoPropedeutico getGrupoPropedeutico(GrupoPropedeutico grupo);
    public GrupoPropedeutico getGrupoPropedeutico(Grupo grupo);
    public void guardar(GrupoPropedeutico grupo);
    public void eliminar(GrupoPropedeutico grupo);
}
