package edu.uaeh.horarios2.service.implementaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.TimeslotDAO;
import edu.uaeh.horarios2.domain.Timeslot;
import edu.uaeh.horarios2.service.TimeslotService;

@Service
public class TimeslotServiceImpl implements TimeslotService {

    @Autowired
    private TimeslotDAO timeslotDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Timeslot> getTimeslots() {
        return (List<Timeslot>) timeslotDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Timeslot timeslot) {
        timeslotDAO.save(timeslot);
    }

    @Override
    @Transactional
    public void eliminar(Timeslot timeslot) {
        timeslotDAO.delete(timeslot);
    }

    @Override
    @Transactional(readOnly = true)
    public Timeslot getTimeslot(Timeslot timeslot) {
        return timeslotDAO.findById(timeslot.getIdTimeslot()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Timeslot getTimeslot(Long idTimeslot) {
        return timeslotDAO.findById(idTimeslot).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Timeslot getTimeslot(Integer idTimeslot) {
        return timeslotDAO.findById(idTimeslot.longValue()).orElse(null);
    }

    @Override
    public List<Timeslot> getTimeslotsMatutinos() {
        return (List<Timeslot>) timeslotDAO.findAllByHoraInicioGreaterThanEqualAndHoraFinLessThanEqual(7, 15);
    }

    @Override
    public List<Timeslot> getTimeslotsVespertinos() {
        return (List<Timeslot>) timeslotDAO.findAllByHoraInicioGreaterThanEqualAndHoraFinLessThanEqual(13, 21);
    }

    @Override
    public List<Timeslot> getTimeslotsTCMatutinos() {
        return (List<Timeslot>) timeslotDAO.findAllByHoraInicioGreaterThanEqualAndHoraFinLessThanEqual(9, 15);
    }

    @Override
    public List<Timeslot> getTimeslotsTCVespertinos() {
        return (List<Timeslot>) timeslotDAO.findAllByHoraInicioGreaterThanEqualAndHoraFinLessThanEqual(13, 18);
    }
    
    @Override
    public List<Timeslot> getTimeslotsServicioSocial(){
        Collection<Integer> dias = new ArrayList<Integer>();
        dias.add(6);
        dias.add(7);
        return (List<Timeslot>) timeslotDAO.findAllByDiaInAndHoraInicio(dias, 8);
    }

    @Override
    public List<Timeslot> getTimeslotsPracticasProfesionales(){
        Collection<Integer> dias = new ArrayList<Integer>();
        dias.add(6);
        dias.add(7);
        return (List<Timeslot>) timeslotDAO.findAllByDiaInAndHoraInicio(dias, 7);
    }
}
