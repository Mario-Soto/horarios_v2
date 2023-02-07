package edu.uaeh.horarios2.service;

import java.util.List;
import edu.uaeh.horarios2.domain.catalogos.MateriaPropedeutico;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;

public interface MateriaPropedeuticoService{
    public List<MateriaPropedeutico> getMateriasPropedeuticos();
    public List<MateriaPropedeutico> getMateriasPropedeuticos(AreaPropedeutica area);
    public MateriaPropedeutico getMateriasPropedeuticos(AreaPropedeutica area, Materia origen);
    public List<MateriaPropedeutico> getMateriasPropedeuticos(AreaPropedeutica area, Integer semestre);
}
