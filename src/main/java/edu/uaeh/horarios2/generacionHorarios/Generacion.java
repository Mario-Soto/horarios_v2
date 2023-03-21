package edu.uaeh.horarios2.generacionHorarios;

import org.springframework.stereotype.Component;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Component
public class Generacion {
    private HashMap<Long, Integer[][]> estructuraDisponibilidadDocentes;
    private HashMap<Long, Integer[][]> estructuraDisponibilidadGrupos;
    /*
     * Datos Grupos -> 
     * 0 -> dias disponibles
     * 1 -> horas permitidas: [0] -> minimas [1] -> maximas
     */
    private HashMap<Long, HashMap<Integer, Integer[]>> datosGrupos;
    private HashMap<Long, List<Long>> clasesImpartidasPorDocentes;
    private HashMap<Long,HashMap<Integer, List<Integer>>> disponibilidadGrupos;
}
