package edu.uaeh.horarios2.GA.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.GrupoPropedeutico;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.service.AreaPropedeuticaService;
import edu.uaeh.horarios2.service.ClaseService;
import edu.uaeh.horarios2.service.DocenteService;
import edu.uaeh.horarios2.service.GrupoPropedeuticoService;
import edu.uaeh.horarios2.service.GrupoService;
import edu.uaeh.horarios2.service.MateriaExtraService;
import edu.uaeh.horarios2.service.MateriaPropedeuticoService;
import edu.uaeh.horarios2.service.MateriaService;
import edu.uaeh.horarios2.service.SesionService;

@Slf4j
@Service
public class HorariosService {

    @Autowired
    GrupoService grupoService;
    @Autowired
    ClaseService claseService;
    @Autowired
    MateriaService materiaService;
    @Autowired
    MateriaExtraService materiaExtraService;
    @Autowired
    AreaPropedeuticaService areaPropedeuticaService;
    @Autowired
    GrupoPropedeuticoService grupoPropedeuticoService;
    @Autowired
    MateriaPropedeuticoService materiaPropedeuticoService;
    @Autowired
    SesionService sesionService;
    @Autowired
    DocenteService docenteService;

    public void generaGrupos(ProgramaEducativo programaEducativo, MultiValueMap<String, String> params) {
        this.eliminarGrupos(programaEducativo);
        List<String> keys = new ArrayList<String>(params.keySet());
        Integer numeroGrupo = 1;
        for (String key : keys) {
            Integer semestre = Integer.parseInt(key.split("-")[1]);
            String turno = key.split("-")[2];
            if (turno.equals("mat")) {
                numeroGrupo = 1;
            }
            Integer grupos = Integer.parseInt(params.get(key).get(0));
            for (int i = 1; i <= grupos; i++) {
                Grupo grupo = new Grupo();
                grupo.setProgramaEducativo(programaEducativo);
                grupo.setSemestre(semestre);
                grupo.setGrupo(numeroGrupo);
                numeroGrupo++;
                if (turno.equals("mat")) {
                    grupo.setTurno(1);
                } else {
                    grupo.setTurno(2);
                }
                grupoService.guardar(grupo);
            }
        }
    }

    public void eliminarGrupos() {
        List<Grupo> grupos = grupoService.getGrupos();
        for (Grupo grupo : grupos) {
            grupoService.eliminar(grupo);
        }
    }

    public void eliminarGrupos(ProgramaEducativo programaEducativo) {
        List<Grupo> grupos = grupoService.getGruposPorProgramaEducativo(programaEducativo);
        for (Grupo grupo : grupos) {
            grupoService.eliminar(grupo);
        }
    }

    public void generaClases(ProgramaEducativo programaEducativo, MultiValueMap<String, String> params) {
        this.eliminarClases(programaEducativo);
        List<String> keys = new ArrayList<>(params.keySet());
        keys.forEach(key -> {
            log.info("key: " + key + " value: " + params.get(key));
            if (key.split("-").length <= 2) {
                Long idGrupo = Long.parseLong(key.split("-")[1]);
                Grupo grupo = grupoService.getGrupo(idGrupo);
                params.get(key).forEach(val -> {
                    Long idMateria = Long.parseLong(val);
                    Materia materia = materiaService.getMateria(idMateria);
                    Materia destino = materiaService.getMateria(idMateria);
                    if (materia.getMateria().contains("IDIOMA")) {
                        destino = materiaService.getMateria(Long.parseLong(params.get(key + "-idioma").get(0)));
                    } else if (materia.getMateria().contains("PROPEDÉUTICA")) {
                        AreaPropedeutica area = areaPropedeuticaService
                                .getAreaPropedeutica(Long.parseLong(params.get(key + "-prope").get(0)));
                        destino = materiaPropedeuticoService.getMateriasPropedeuticos(area, materia)
                                .getMateria();
                    }
                    Clase clase = new Clase();
                    clase.setGrupo(grupo);
                    clase.setMateria(materia);
                    clase.setMateriaFinal(destino);
                    claseService.guardar(clase);
                });
            } else if (key.split("-")[2].contains("prope")) {
                Long idGrupo = Long.parseLong(key.split("-")[1]);
                Grupo grupo = grupoService.getGrupo(idGrupo);
                GrupoPropedeutico grupoPrope = new GrupoPropedeutico();
                grupoPrope.setGrupo(grupo);
                AreaPropedeutica area = areaPropedeuticaService
                        .getAreaPropedeutica(Long.parseLong(params.get(key).get(0)));
                grupoPrope.setAreaPropedeutica(area);
                grupoPropedeuticoService.guardar(grupoPrope);
            } else if (key.split("-")[2].contains("opt")) {
                Long idGrupo = Long.parseLong(key.split("-")[1]);
                Grupo grupo = grupoService.getGrupo(idGrupo);
                Materia origen = materiaService.getMateria(Long.parseLong(key.split("-")[3]));
                params.get(key).forEach(val -> {
                    Clase clase = new Clase();
                    clase.setGrupo(grupo);
                    clase.setMateria(origen);
                    Materia destino = materiaService.getMateria(Long.parseLong(val));
                    clase.setMateriaFinal(destino);
                    claseService.guardar(clase);
                });
            }
        });
    }

    public void eliminarClases() {
        List<Clase> clases = claseService.getClases();
        List<GrupoPropedeutico> grupoPropedeuticos = grupoPropedeuticoService.getGruposPropedeuticos();
        clases.forEach(clase -> {
            claseService.eliminar(clase);
        });
        grupoPropedeuticos.forEach(grupo -> {
            grupoPropedeuticoService.eliminar(grupo);
        });
    }

    public void eliminarClases(ProgramaEducativo programaEducativo) {
        List<Grupo> grupos = grupoService.getGruposPorProgramaEducativo(programaEducativo);

        grupos.forEach(grupo -> {
            GrupoPropedeutico grupoPrope = grupoPropedeuticoService.getGrupoPropedeutico(grupo);
            if (grupoPrope != null) {
                grupoPropedeuticoService.eliminar(grupoPrope);
            }
            grupo.getClases().forEach(clase -> {
                claseService.eliminar(clase);
            });
        });
    }

    public void generarSesiones(){
        this.eliminarSesiones();
        List<Clase> clases = claseService.getClases();
        clases.forEach(clase ->{
            Integer duracion = clase.getMateria().getHorasSemana();
            while(duracion > 0){
                Sesion sesion = new Sesion();
                sesion.setClase(clase);
                if(clase.esPracticasProfesionales()){
                    if (duracion > 15) {
                        sesion.setDuracion(15);
                        duracion -= 15;
                    } else {
                        sesion.setDuracion(duracion);
                        duracion = 0;
                    }
                }else if(clase.esServicioSocial()){
                    sesion.setDuracion(10);
                    duracion -= 10;
                }else{
                    if(duracion == 7 || (clase.getMateria().getMateria().contains("OPTATIVA") && duracion % 2 == 1)){
                        sesion.setDuracion(3);
                        duracion -= 3;
                    }else if (duracion >= 2) {
                        sesion.setDuracion(2);
                        duracion -= 2;
                    } else {
                        sesion.setDuracion(1);
                        duracion--;
                    }
                }
                sesionService.guardar(sesion);
            }
        });
    }

    public void eliminarSesiones(){
        sesionService.getSesiones().forEach(sesion -> {
            sesionService.eliminar(sesion);
        });
    }

    public void asignarDocentes(){
        HashMap<Long, Integer> horasSemanaDocente = new HashMap<>();

        List<Clase> clases = claseService.getClases();

        clases.sort((c1, c2) -> {
            int doc1 = c1.getMateriaFinal().getAsignaciones().size();
            int doc2 = c2.getMateriaFinal().getAsignaciones().size();
            return doc1 - doc2;
        });

        clases.forEach(clase -> {
            boolean indicador = false;
            Long idDocente = Long.valueOf(0);
            Integer horas = 0;
            int contador = 0;
            boolean permitidoRegistrar = true;
            do{
                indicador = false;
                if(contador == 10){
                    // log.info("No es posible obtener un docente");
                    // log.info("Clase: "+clase.getIdClase()+" -> "+clase.getMateria().getMateria());
                    permitidoRegistrar = false;
                    idDocente = Long.valueOf(-1);
                    break;
                }
                idDocente = clase.getMateriaFinal().getRandomDocentePermitido().getIdDocente();

                if( horasSemanaDocente.get(idDocente) == null ){
                    horas = clase.getMateriaFinal().getHorasSemana();
                }else{
                    horas = horasSemanaDocente.get(idDocente) + clase.getMateriaFinal().getHorasSemana();
                    if(horas > docenteService.getDocente(idDocente).getHorasPermitidas()){
                        indicador = true;
                        contador++;
                    }
                }
            }while(indicador);

            if(permitidoRegistrar){
                horasSemanaDocente.put(idDocente, horas);
                clase.setDocente(docenteService.getDocente(idDocente));
                claseService.guardar(clase);
            }else{
                log.info("No se pudo asignar docente a la clase: "+clase.getIdClase()+" -> "+clase.getMateria().getMateria());
            }
        });
    }
}
