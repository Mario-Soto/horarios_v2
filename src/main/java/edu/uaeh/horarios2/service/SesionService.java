package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Aula;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Sesion;

public interface SesionService {
    public List<Sesion> getSesiones();

    public void guardar(Sesion sesion);

    public void eliminar(Sesion sesion);

    public Sesion getSesion(Sesion sesion);

    public Sesion getSesion(Long idSesion);

    public Sesion getSesion(Integer idSesion);

    public List<Sesion> getSesionesPorClase(Clase clase);

    public List<Sesion> getSesionesPorAula(Aula aula);

    public Sesion[][] getHorarioPorGrupo(Grupo grupo);

    public Sesion[][] getHorarioPorGrupo(Long idGrupo);

    public Sesion[][] getHorarioPorGrupo(Integer idGrupo);

    public Integer[][] getEstructura(Sesion[][] horario);
}
