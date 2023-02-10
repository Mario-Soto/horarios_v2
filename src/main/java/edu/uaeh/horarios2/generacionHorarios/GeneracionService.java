package edu.uaeh.horarios2.generacionHorarios;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uaeh.horarios2.service.DocenteService;
import edu.uaeh.horarios2.service.GrupoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeneracionService {
    
    @Autowired
    private DocenteService docenteService;
    @Autowired
    private GrupoService grupoService;

    public void disponibilidadDocentes(Generacion generacionHorarios){
        HashMap<Long, Integer[][]> disponibilidades = new HashMap<>();
        docenteService.getDocentes().forEach(docente -> {
            if(docente.getEstatus() == 1){
                Integer[][] disponibilidadDocente = new Integer[5][14];
                docente.getDisponibilidad().forEach(disponibilidad -> {
                    disponibilidadDocente[disponibilidad.getSlot().getDia()-1][disponibilidad.getSlot().getHoraInicio()-7] = disponibilidad.getEstatus();
                });
                disponibilidades.put(docente.getIdDocente(), disponibilidadDocente);
            }
        });
        generacionHorarios.setDisponibilidadDocentes(disponibilidades);
    }

    public HashMap<Long, Integer[]> materiasHorasGrupos(Generacion generacion){
        HashMap<Long, Integer[]> datos = new HashMap<>();
        grupoService.getGrupos().forEach(grupo -> {
            Integer[] dato = {0, 0}; // 0 -> materias 1 -> horas
            grupo.getClases().forEach(clase -> {
                dato[0]++;
                dato[1] += clase.getMateria().getHorasSemana();
            });
            datos.put(grupo.getIdGrupo(), dato);
        });
        return datos;
    }

    public HashMap<Long, Integer[]> disponibilidadGrupos(Generacion generacion){
        HashMap<Long, Integer[]> disponibilidades = new HashMap<>();
        List<Integer> dias = List.of(1,2,3,4,5);
        
        

        return disponibilidades;
    }
}
