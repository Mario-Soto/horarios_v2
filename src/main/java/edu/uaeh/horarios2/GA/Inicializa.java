package edu.uaeh.horarios2.GA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uaeh.horarios2.GA.Services.TimetableService;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.service.ClaseService;
import edu.uaeh.horarios2.service.SesionService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Inicializa {
    @Autowired
    private ClaseService claseService;
    @Autowired
    private SesionService sesionService;
    @Autowired
    private TimetableService timetableService;
    private Timetable timetable;
    private List<Clase> clases;

    public void inicioGA() {
        log.info("Inicializando el GA");
        timetable = timetableService.timetable();
        log.info("Cree el timetable");
        // timetableService.generarClases(timetable);
        // timetableService.generarSesiones(timetable);

        //GeneticAlgorithm ga = new GeneticAlgorithm(150, 0.005, 0.9, 1, 20);
        GeneticAlgorithm ga = new GeneticAlgorithm(75, 0.005, 0.90, 1, 20);
        log.info("Cree el Algoritmo");
        Population population = ga.initPopulation(timetable);
        log.info("Evalué el algoritmo");
        ga.evalPopulation(population, timetable);
        int generation = 1;

        while (!ga.isTerminationConditionMet(generation, 3000) && !ga.isTerminationConditionMet(population)) {
            if (generation % 500 == 0) {
                log.info("Generation: " + generation + " Fittest: " + population.getFittest(0).getFitness());
            }

            population = ga.crossoverPopulation(population);
            population = ga.mutatePopulation(population, timetable);
            ga.evalPopulation(population, timetable);
            generation++;
        }

        timetable.createClasses(population.getFittest(0));
        log.info("Solution found in " + generation + " generations");
        log.info("Best individual is " + population.getFittest(0).toString());
        log.info("Fitness: " + population.getFittest(0).getFitness());
        log.info("Clashes: " + timetable.calcClashes());
        log.info("Clash Ultima clase: " + timetable.getClashUltimaClase());
        log.info("Clash Aula ocupada: " + timetable.getClashAulaOcupada());
        log.info("Clash Profesor disponible: " + timetable.getClashProfesorDisponible());
        log.info("Clash Misma hora: " + timetable.getClashMismaHora());
        log.info("Clash Sesión al día: " + timetable.getClashSesionAlDia());
        log.info("Clash Horas muertas: " + timetable.getClashHorasMuertas());
        log.info("Clash Clase a las 7: " + timetable.getClashClaseALas7());
        log.info("Clash Clase a las 18: " + timetable.getClashClaseALas18());
        log.info("Clash Jornada homogenea (grupos): " + timetable.getClashJornadaHomogeneaGrupos());
        //log.info("Clash Dias cursados: " + timetable.getClashDiasDeClases());
        
        log.info("Terminó el proceso");

        clases = timetable.getClases();
        guardarBD();
    }

    public void guardarBD() {
        for (Clase clase : clases) {
            claseService.guardar(clase);
            for (Sesion sesion : clase.getSesiones()) {
                sesionService.guardar(sesion);
            }
        }
    }

}
