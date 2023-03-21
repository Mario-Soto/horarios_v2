package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;

public interface GrupoService {
    public List<Grupo> getGrupos();

    public void guardar(Grupo grupo);

    public void eliminar(Grupo grupo);

    public Grupo getGrupo(Grupo grupo);

    public Grupo getGrupo(Long idGrupo);

    public Grupo getGrupo(Integer idGrupo);

    public Grupo getGrupo(ProgramaEducativo programaEducativo, Integer semestre, Integer grupo);

    public List<Grupo> getGruposPorTurno(Integer turno);

    public List<Grupo> getGruposMatutinos();

    public List<Grupo> getGruposVespertinos();

    public List<Grupo> getGruposPorProgramaEducativo(ProgramaEducativo programaEducativo);

    public List<Grupo> getGruposPorSemestre(ProgramaEducativo programaEducativo, Integer semestre);

    public List<ProgramaEducativo> getProgramasEducativos();
}
