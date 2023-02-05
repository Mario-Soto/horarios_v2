package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;

public interface ClaseService {
    public List<Clase> getClases();

    public void guardar(Clase clase);

    public void eliminar(Clase clase);

    public Clase getClase(Clase clase);

    public Clase getClase(Long idClase);

    public Clase getClase(Integer idClase);

    public List<Clase> getClasesPorGrupo(Grupo grupo);

    public List<Clase> getClasesPorDocente(Docente docente);

    public List<Clase> getClasesPorMateria(Materia materia);
}
