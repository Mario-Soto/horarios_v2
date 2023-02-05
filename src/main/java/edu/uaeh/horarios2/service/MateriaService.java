package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;

public interface MateriaService {
    public List<Materia> getMaterias();

    public void guardar(Materia materia);

    public void eliminar(Materia materia);

    public Materia getMateria(Materia materia);

    public Materia getMateria(Long idMateria);

    public Materia getMateria(Integer idMateria);

    public List<Materia> getMateriasPorProgramaEducativo(ProgramaEducativo programaEducativo);

    public List<Materia> getMateriasPorProgramaEducativo(ProgramaEducativo programaEducativo, Integer semestre);

    public List<Materia> getMateriasPorProgramaEducativo(ProgramaEducativo programaEducativo, Integer semestre, List<TipoMateria> tipos);

    public List<Materia> getMateriasIdiomas();

    public List<Materia> getMateriasPropedeuticas();

    public List<Materia> getMateriasPropedeuticas(AreaPropedeutica areaPropedeutica);

    public List<Materia> getMateriasPropedeuticas(AreaPropedeutica areaPropedeutica, Integer semestre);

    public List<Materia> getOptativas(ProgramaEducativo programaEducativo);

    public List<Materia> getMateriasBachillerato();
    
    public List<Materia> getMateriasBachillerato(Integer semestre);

    public List<Materia> getMateriasLTI();
    
    public List<Materia> getMateriasLTI(Integer semestre);

    public List<Materia> getMateriasLT();
    
    public List<Materia> getMateriasLT(Integer semestre);

    public List<Materia> getMateriasIAI();
    
    public List<Materia> getMateriasIAI(Integer semestre);
}
