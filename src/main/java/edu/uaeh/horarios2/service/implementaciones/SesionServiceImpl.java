package edu.uaeh.horarios2.service.implementaciones;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.SesionDAO;
import edu.uaeh.horarios2.domain.Aula;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.service.ClaseService;
import edu.uaeh.horarios2.service.GrupoService;
import edu.uaeh.horarios2.service.SesionService;

@Service
public class SesionServiceImpl implements SesionService {

    @Autowired
    private SesionDAO sesionDAO;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private ClaseService claseService;

    @Override
    @Transactional(readOnly = true)
    public List<Sesion> getSesiones() {
        return (List<Sesion>) sesionDAO.findAll();
    }

    @Override
    public void guardar(Sesion sesion) {
        sesionDAO.save(sesion);
    }

    @Override
    public void eliminar(Sesion sesion) {
        sesionDAO.delete(sesion);
    }

    @Override
    @Transactional(readOnly = true)
    public Sesion getSesion(Sesion sesion) {
        return sesionDAO.findById(sesion.getIdSesion()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Sesion getSesion(Long idSesion) {
        return sesionDAO.findById(idSesion).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Sesion getSesion(Integer idSesion) {
        return sesionDAO.findById(idSesion.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sesion> getSesionesPorClase(Clase clase) {
        return (List<Sesion>) sesionDAO.findAllByClase(clase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sesion> getSesionesPorAula(Aula aula) {
        return (List<Sesion>) sesionDAO.findAllByAula(aula);
    }

    @Override
    public Sesion[][] getHorarioPorGrupo(Grupo grupo) {
        List<Clase> clases = claseService.getClasesPorGrupo(grupo);
        List<Sesion> sesiones = new ArrayList<>();
        for (Clase clase : clases) {
            if (!clase.esOptativa()) {
                sesiones.addAll(clase.getSesiones());
            }
        }
        Sesion[][] horario;
        if (grupo.getSemestre() == 8) {
            horario = new Sesion[7][14];
        } else if (grupo.getSemestre() == 9) {
            horario = new Sesion[7][15];
        } else {
            horario = new Sesion[5][14];
        }
        for (Sesion sesion : sesiones) {
            horario[sesion.getInicio().getDia() - 1][sesion.getInicio().getHoraInicio() - 7] = sesion;
        }
        return horario;
    }

    @Override
    public Sesion[][] getHorarioPorGrupo(Long idGrupo) {
        return getHorarioPorGrupo(grupoService.getGrupo(idGrupo));
    }

    @Override
    public Sesion[][] getHorarioPorGrupo(Integer idGrupo) {
        return getHorarioPorGrupo(grupoService.getGrupo(idGrupo));
    }

    @Override
    public Integer[][] getEstructura(Sesion[][] horario) {
        Integer[][] estructura = new Integer[horario.length][horario[0].length];
        for (int i = 0; i < estructura.length; i++) {
            for (int j = 0; j < estructura[i].length; j++) {
                if (horario[i][j] != null) {
                    estructura[i][j] = horario[i][j].getDuracion();
                } else {
                    estructura[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < estructura.length; i++) {
            for (int j = 0; j < estructura[i].length; j++) {
                if (estructura[i][j] == 2 && j != estructura[i].length - 1) {
                    estructura[i][j + 1] = 0;
                }
            }
        }
        return estructura;
    }

}
