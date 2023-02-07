package edu.uaeh.horarios2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.GrupoPropedeutico;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;

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
                    if (materia.getMateria().contains("IDIOMA")) {
                        MateriaExtra materiaExtra = new MateriaExtra();
                        materiaExtra.setGrupo(grupo);
                        materiaExtra.setMateriaOrigen(materia);
                        Materia destino = materiaService.getMateria(Long.parseLong(params.get(key + "-idioma").get(0)));
                        materiaExtra.setMateria(destino);
                        materiaExtraService.guardar(materiaExtra);
                    } else if (materia.getMateria().contains("PROPEDÉUTICA")) {
                        MateriaExtra materiaExtra = new MateriaExtra();
                        materiaExtra.setGrupo(grupo);
                        materiaExtra.setMateriaOrigen(materia);
                        AreaPropedeutica area = areaPropedeuticaService
                                .getAreaPropedeutica(Long.parseLong(params.get(key + "-prope").get(0)));
                        Materia destino = materiaPropedeuticoService.getMateriasPropedeuticos(area, materia)
                                .getMateria();
                        materiaExtra.setMateria(destino);
                        materiaExtraService.guardar(materiaExtra);
                    }
                    Clase clase = new Clase();
                    clase.setGrupo(grupo);
                    clase.setMateria(materia);
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
                Clase clase = new Clase();
                clase.setGrupo(grupo);
                clase.setMateria(origen);
                claseService.guardar(clase);

                params.get(key).forEach(val -> {
                    MateriaExtra materiaExtra = new MateriaExtra();
                    Materia destino = materiaService.getMateria(Long.parseLong(val));
                    materiaExtra.setGrupo(grupo);
                    materiaExtra.setMateriaOrigen(origen);
                    materiaExtra.setMateria(destino);
                    materiaExtraService.guardar(materiaExtra);
                });
            }
        });
    }

    public void eliminarClases() {
        List<Clase> clases = claseService.getClases();
        List<MateriaExtra> materiaExtras = materiaExtraService.getMateriasExtras();
        List<GrupoPropedeutico> grupoPropedeuticos = grupoPropedeuticoService.getGruposPropedeuticos();
        clases.forEach(clase -> {
            claseService.eliminar(clase);
        });
        materiaExtras.forEach(materia -> {
            materiaExtraService.eliminar(materia);
        });
        grupoPropedeuticos.forEach(grupo -> {
            grupoPropedeuticoService.eliminar(grupo);
        });
    }

    public void eliminarClases(ProgramaEducativo programaEducativo) {
        List<Grupo> grupos = grupoService.getGrupos();

        grupos.forEach(grupo -> {
            List<MateriaExtra> materiasExtra = materiaExtraService.getMateriasExtras(grupo);
            materiasExtra.forEach(materia -> {
                materiaExtraService.eliminar(materia);
            });
            GrupoPropedeutico grupoPrope = grupoPropedeuticoService.getGrupoPropedeutico(grupo);
            if (grupoPrope != null) {
                grupoPropedeuticoService.eliminar(grupoPrope);
            }
            grupo.getClases().forEach(clase -> {
                claseService.eliminar(clase);
            });
        });
    }
}
