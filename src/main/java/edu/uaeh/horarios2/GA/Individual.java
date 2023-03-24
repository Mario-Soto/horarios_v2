package edu.uaeh.horarios2.GA;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.domain.Timeslot;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Individual{

    private int[] chromosome;
    private double fitness = -1;

    public Individual(Timetable timetable) {
        // log.info("ENTRO A INDIVIDUAL");
        int numeroClases = timetable.getNumeroSesiones();
        // log.info("CLASES: "+ numeroClases);
        // 1 gene for room, 1 for time, 1 for professor
        int chromosomeLength = numeroClases * 2;

        // Create random individual
        int newChromosome[] = new int[chromosomeLength];
        int chromosomeIndex = 0;

        // Loop through groups
        for (Grupo grupo : timetable.getGrupos()) {
            List<Clase> clases = grupo.getClases();
            for (Clase clase : clases) {
                Docente docente = clase.getDocente();
                int professorId = docente.getIdDocente().intValue();
                for (Sesion sesion : clase.getSesiones()) {
                    // Add random timeslot
                    int timeslotId;
                    if (sesion.getClase().esPracticasProfesionales()) {
                        if (sesion.getDuracion() > 2) {
                            timeslotId = timetable.getTimeslotsPracticasProfesionales()
                                    .get((int) (Math.random() * 2))
                                    .getIdTimeslot().intValue();
                        } else {
                            timeslotId = timetable.getFirstTimeslot().getIdTimeslot().intValue();
                        }
                    } else if (sesion.getClase().esServicioSocial()) {
                        timeslotId = timetable.getTimeslotsServicioSocial().get((int) (Math.random() * 2))
                                .getIdTimeslot().intValue();
                    } else {
                        // log.info("Clase normal - inicio");
                        Timeslot slot;
                        slot = timetable.getRandomTimeslot(clase.getGrupo(), docente, sesion.getDuracion());
                        // log.info("Slot definido");
                        timeslotId = slot.getIdTimeslot().intValue();
                    }
                    newChromosome[chromosomeIndex] = timeslotId;
                    chromosomeIndex++;

                    // Add random room
                    // int roomId = timetable.getRandomAula().getIdAula().intValue();
                    // newChromosome[chromosomeIndex] = roomId;
                    // chromosomeIndex++;

                    // Add professor
                    newChromosome[chromosomeIndex] = professorId;
                    chromosomeIndex++;
                }
            }
        }
        // log.info("Regresar cromosoma");
        this.chromosome = newChromosome;
        // List<Integer> cromosoma = new ArrayList<Integer>();
        // for(int i : this.chromosome){
        // cromosoma.add(Integer.valueOf(i));
        // }
        // log.info("Tamaño: "+cromosoma.size());
        // log.info(cromosoma.toString());
    }

    public Individual(int chromosomeLength) {
        // Create random individual
        int[] individual;
        individual = new int[chromosomeLength];

        /**
         * This comment and the for loop doesn't make sense for this chapter.
         * But I'm leaving it in here because you were instructed to copy this
         * class from Chapter 4 -- and NOT having this comment here might be
         * more confusing than keeping it in.
         * 
         * Comment from Chapter 4:
         * 
         * "In this case, we can no longer simply pick 0s and 1s -- we need to
         * use every city index available. We also don't need to randomize or
         * shuffle this chromosome, as crossover and mutation will ultimately
         * take care of that for us."
         */
        for (int gene = 0; gene < chromosomeLength; gene++) {
            individual[gene] = gene;
        }

        this.chromosome = individual;
    }

    public Individual(int[] chromosome) {
        // Create individual chromosome
        this.chromosome = chromosome;
    }

    public int[] getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.length;
    }

    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return this.chromosome[offset];
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene] + ",";
        }
        return output;
    }

    public boolean containsGene(int gene) {
        for (int i = 0; i < this.chromosome.length; i++) {
            if (this.chromosome[i] == gene) {
                return true;
            }
        }
        return false;
    }

}
