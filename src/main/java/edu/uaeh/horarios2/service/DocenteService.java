package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;

public interface DocenteService {
    public List<Docente> getDocentes();

    public void guardar(Docente docente);

    public void eliminar(Docente docente);

    public Docente getDocente(Docente docente);

    public Docente getDocente(Long idDocente);

    public Docente getDocente(Integer idDocente);

    public List<Docente> getDocentesPorTipo(TipoEmpleado tipo);

    public List<Docente> getDocentesTiempoCompleto();

    public List<Docente> getDocentesTiempoCompleto4738();

    public List<Docente> getDocentesPorAsignatura();

    public List<Docente> getDocentesSindicalizados();
}
