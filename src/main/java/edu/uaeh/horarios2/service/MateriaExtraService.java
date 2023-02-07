package edu.uaeh.horarios2.service;

import java.util.List;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Grupo;

public interface MateriaExtraService{
    public List<MateriaExtra> getMateriasExtras();
    public List<MateriaExtra> getMateriasExtras(Grupo grupo);
    public MateriaExtra getMateriasExtras(Grupo grupo, Materia origen);
    public void guardar(MateriaExtra materiaExtra);
    public void eliminar(MateriaExtra materiaExtra);
}
