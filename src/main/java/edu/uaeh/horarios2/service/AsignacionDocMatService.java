package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AsignacionDocMat;

public interface AsignacionDocMatService {
    public List<AsignacionDocMat> getAsignacionesPorDocente(Docente docente);

    public List<AsignacionDocMat> getAsignacionesPermitidasPorDocente(Docente docente);

    public List<AsignacionDocMat> getDocentesPorMateria(Materia materia);

    public void guardar(AsignacionDocMat asignacion);

    public void eliminar(AsignacionDocMat asignacion);

    public AsignacionDocMat getAsignacion(AsignacionDocMat asignacion);

    public AsignacionDocMat getAsignacion(Long idAsignacion);

    public AsignacionDocMat getAsignacion(Integer idAsignacion);
}
