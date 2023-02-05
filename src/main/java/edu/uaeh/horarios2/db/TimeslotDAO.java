package edu.uaeh.horarios2.db;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import edu.uaeh.horarios2.domain.Timeslot;

public interface TimeslotDAO extends CrudRepository<Timeslot, Long>{
    Iterable<Timeslot> findAllByDia(Integer dia);
    Iterable<Timeslot> findAllByDiaAndHoraInicio(Integer dia, Integer inicio);
    Iterable<Timeslot> findAllByHoraInicioGreaterThanEqualAndHoraFinLessThanEqual(Integer inicio, Integer fin);
    Iterable<Timeslot> findAllByDiaInAndHoraInicio(Collection<Integer> dia, Integer inicio);
}
