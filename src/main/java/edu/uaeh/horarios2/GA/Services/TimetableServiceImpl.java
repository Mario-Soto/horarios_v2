package edu.uaeh.horarios2.GA.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uaeh.horarios2.GA.Timetable;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;
import edu.uaeh.horarios2.generacionHorarios.GeneracionService;
import edu.uaeh.horarios2.service.AulaService;
import edu.uaeh.horarios2.service.ClaseService;
import edu.uaeh.horarios2.service.DocenteService;
import edu.uaeh.horarios2.service.GrupoService;
import edu.uaeh.horarios2.service.MateriaService;
import edu.uaeh.horarios2.service.SesionService;
import edu.uaeh.horarios2.service.TimeslotService;
import edu.uaeh.horarios2.service.TipoMateriaService;

@Service
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private ClaseService claseService;

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private SesionService sesionService;

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private TipoMateriaService tipoMateriaService;

    @Autowired
    private GeneracionService generacionService;

    @Override
    public Timetable timetable() {
        Timetable timetable = new Timetable();
        timetable.setAulas(aulaService.getAulas());
        timetable.setClases(claseService.getClases());
        timetable.setDocentes(docenteService.getDocentes());
        timetable.setGrupos(grupoService.getGrupos());
        timetable.setTimeslots(timeslotService.getTimeslots());
        timetable.setTimeslotsMatutinos(timeslotService.getTimeslotsMatutinos());
        timetable.setTimeslotsVespertinos(timeslotService.getTimeslotsVespertinos());
        timetable.setMaterias(materiaService.getMaterias());
        timetable.setSesiones(sesionService.getSesiones());
        timetable.setTimeslotsPracticasProfesionales(timeslotService.getTimeslotsPracticasProfesionales());
        timetable.setTimeslotsServicioSocial(timeslotService.getTimeslotsServicioSocial());
        timetable.setDisponibilidadGrupos(generacionService.obtenerDisponibilidadesGrupos());
        return timetable;
    }

    @Override
    public Timetable timetable(Timetable clonable) {
        Timetable timetable = new Timetable(clonable);
        return timetable;
    }

    @Override
    public void generarClases(Timetable timetable) {
        List<TipoMateria> tipos = new ArrayList<>();
        tipos.add(tipoMateriaService.getTipoMateria(1));
        tipos.add(tipoMateriaService.getTipoMateria(5));
        tipos.add(tipoMateriaService.getTipoMateria(6));
        for (Grupo grupo : timetable.getGrupos()) {
            List<Materia> materias = timetable.getMateriasPorPE(grupo.getProgramaEducativo(), grupo.getSemestre(), tipos);
            for (Materia materia : materias) {
                Clase clase = new Clase();
                clase.setGrupo(grupo);
                clase.setMateria(materia);
                claseService.guardar(clase);
            }
        }
        timetable.setClases(claseService.getClases());
    }

    @Override
    public void generarSesiones(Timetable timetable) {
        for (Clase clase : timetable.getClases()) {
            Integer duracion = clase.getMateria().getHorasSemana();
            while (duracion > 0) {
                Sesion sesion = new Sesion();
                sesion.setClase(clase);
                if (clase.getMateria().getTipoMateria().getTipo().equals("Prácticas profesionales")) {
                    if (duracion > 15) {
                        sesion.setDuracion(15);
                        duracion -= 15;
                    } else {
                        sesion.setDuracion(duracion);
                        duracion = 0;
                    }
                } else if (clase.getMateria().getTipoMateria().getTipo().equals("Servicio social")) {
                    sesion.setDuracion(10);
                    duracion -= 10;
                } else {
                    if (duracion >= 2) {
                        sesion.setDuracion(2);
                        duracion -= 2;
                    } else {
                        sesion.setDuracion(1);
                        duracion--;
                    }
                }
                sesionService.guardar(sesion);
            }
        }
    }
}
