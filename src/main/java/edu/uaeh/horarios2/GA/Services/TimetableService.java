package edu.uaeh.horarios2.GA.Services;

import edu.uaeh.horarios2.GA.Timetable;

public interface TimetableService {
    public Timetable timetable();
    public Timetable timetable(Timetable clonable);
    public void generarClases(Timetable timetable);
    public void generarSesiones(Timetable timetable);
}
