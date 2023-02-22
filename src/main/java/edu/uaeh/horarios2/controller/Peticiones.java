package edu.uaeh.horarios2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.MateriaPropedeutico;
import edu.uaeh.horarios2.generacionHorarios.Generacion;
import edu.uaeh.horarios2.generacionHorarios.GeneracionService;
import edu.uaeh.horarios2.service.AreaPropedeuticaService;
import edu.uaeh.horarios2.service.GrupoService;
import edu.uaeh.horarios2.service.MateriaPropedeuticoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/peticion")
public class Peticiones {
    @Autowired
    MateriaPropedeuticoService materiaPropedeuticoService;
    @Autowired
    GrupoService grupoService;
    @Autowired
    AreaPropedeuticaService areaPropedeuticaService;
    @Autowired
    GeneracionService generacionService;

    @GetMapping(value = "/propedeuticos/{idGrupo}/{idAreaPropedeutica}", produces = "application/json")
    public Map<String, Object> propedeuticosArea(Grupo grupo, AreaPropedeutica area){
        grupo = grupoService.getGrupo(grupo.getIdGrupo());
        area = areaPropedeuticaService.getAreaPropedeutica(area.getIdAreaPropedeutica());
        List<MateriaPropedeutico> materiasPropes = materiaPropedeuticoService.getMateriasPropedeuticos(area, grupo.getSemestre());
        HashMap<String, Object> json = new HashMap<>();
        json.put("grupo", grupo.getIdGrupo());
        json.put("area", area.getIdAreaPropedeutica());
        List<HashMap<String, Object>> materias = new ArrayList<>();
        for(MateriaPropedeutico materia: materiasPropes){
            HashMap<String, Object> datos = new HashMap<>();
            datos.put("materia_origen", materia.getMateriaOrigen().getIdMateria());
            datos.put("materia_id", materia.getMateria().getIdMateria());
            datos.put("materia_nombre", materia.getMateria().getMateria());
            materias.add(datos);
        }
        json.put("materias", materias);
        return json;
    }

    @GetMapping("/prueba")
    public HashMap<Long, HashMap<Integer, Integer[]>> prueba(){
        Integer[][] datos = {{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14},{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14},{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14},{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14},{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14}};
        generacionService.quitarFilasHoras(datos, 2);
        generacionService.quitarColumnasDias(datos, 3);
        return generacionService.datosGrupos(new Generacion());
    }

    @GetMapping("/prueba2")
    public HashMap<Long, List<Long>> prueba2(){
        return generacionService.asignarDocentes(new Generacion());
    }
}
