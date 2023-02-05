package edu.uaeh.horarios2.service.implementaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.DocenteDAO;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;
import edu.uaeh.horarios2.service.DocenteService;
import edu.uaeh.horarios2.service.TipoEmpleadoService;

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteDAO docenteDAO;

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;


    @Override
    @Transactional(readOnly = true)
    public List<Docente> getDocentes() {
        return (List<Docente>) docenteDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Docente docente) {
        docenteDAO.save(docente);
    }

    @Override
    @Transactional
    public void eliminar(Docente docente) {
        docenteDAO.delete(docente);
    }

    @Override
    @Transactional(readOnly = true)
    public Docente getDocente(Docente docente) {
        return docenteDAO.findById(docente.getIdDocente()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Docente getDocente(Long idDocente) {
        return docenteDAO.findById(idDocente).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Docente getDocente(Integer idDocente) {
        return docenteDAO.findById(idDocente.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> getDocentesPorTipo(TipoEmpleado tipo) {
        return (List<Docente>) docenteDAO.findAllByTipoEmpleadoAndEstatus(tipo, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> getDocentesTiempoCompleto() {
        return (List<Docente>) docenteDAO.findAllByTipoEmpleadoAndEstatus(tipoEmpleadoService.getTipoEmpleado(2),  1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> getDocentesTiempoCompleto4738() {
        Collection<Integer> restricciones = new ArrayList<Integer>();
        restricciones.add(4738);
        return (List<Docente>) docenteDAO.findAllByTipoEmpleadoOrNumeroEmpleadoInAndEstatus(tipoEmpleadoService.getTipoEmpleado(2), restricciones, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> getDocentesPorAsignatura() {
        return (List<Docente>) docenteDAO.findAllByTipoEmpleadoAndEstatus(tipoEmpleadoService.getTipoEmpleado(1), 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> getDocentesSindicalizados() {
        return (List<Docente>) docenteDAO.findAllByTipoEmpleadoAndEstatus(tipoEmpleadoService.getTipoEmpleado(3), 1);
    }
}
