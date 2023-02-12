package edu.uaeh.horarios2.GA;

public class ParallelProcess implements Runnable{
    
    private Timetable timetable;
    private Individual individual;
    private GeneticAlgorithm algoritmo;

    ParallelProcess(Timetable timetable, Individual individual, GeneticAlgorithm algoritmo){
        this.timetable = timetable;
        this.individual = individual;
        this.algoritmo = algoritmo;
    }

    @Override
    public void run(){
        this.algoritmo.addFit(this.algoritmo.calcFitness(individual, timetable));
    }
}
