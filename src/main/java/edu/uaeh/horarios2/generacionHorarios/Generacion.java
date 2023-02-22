package edu.uaeh.horarios2.generacionHorarios;

import org.springframework.stereotype.Component;

import lombok.Data;

import java.util.HashMap;

@Data
@Component
public class Generacion {
    private HashMap<Long, Integer[][]> disponibilidadDocentes;
    private HashMap<Long, HashMap<Integer, Integer[]>> datosGrupos;
    private HashMap<Long, Integer[][]> disponibilidadGrupos;
}
