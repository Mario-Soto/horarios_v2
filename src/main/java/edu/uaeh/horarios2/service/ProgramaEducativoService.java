package edu.uaeh.horarios2.service;

import java.util.HashMap;
import java.util.List;

import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;

public interface ProgramaEducativoService {
    public List<ProgramaEducativo> getProgramasEducativos();

    public void guardar(ProgramaEducativo programaEducativo);

    public void eliminar(ProgramaEducativo programaEducativo);

    public ProgramaEducativo getProgramaEducativo(ProgramaEducativo programaEducativo);

    public ProgramaEducativo getProgramaEducativo(Long idProgramaEducativo);

    public ProgramaEducativo getProgramaEducativo(Integer idProgramaEducativo);

    public ProgramaEducativo getProgramaEducativoPorNombreCorto(String nombre);

    public HashMap<Long, ProgramaEducativo> getMap(List<ProgramaEducativo> lista);
}
