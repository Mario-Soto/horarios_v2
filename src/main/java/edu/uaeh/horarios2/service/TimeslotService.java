package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Timeslot;

public interface TimeslotService {
    public List<Timeslot> getTimeslots();

    public void guardar(Timeslot timeslot);

    public void eliminar(Timeslot timeslot);

    public Timeslot getTimeslot(Timeslot timeslot);

    public Timeslot getTimeslot(Long idTimeslot);

    public Timeslot getTimeslot(Integer idTimeslot);

    public List<Timeslot> getTimeslotsMatutinos();

    public List<Timeslot> getTimeslotsVespertinos();

    public List<Timeslot> getTimeslotsTCMatutinos();

    public List<Timeslot> getTimeslotsTCVespertinos();

    public List<Timeslot> getTimeslotsServicioSocial();
    
    public List<Timeslot> getTimeslotsPracticasProfesionales();
}
