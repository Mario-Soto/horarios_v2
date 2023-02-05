package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.AsignacionDocMatDAO;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AsignacionDocMat;
import edu.uaeh.horarios2.service.AsignacionDocMatService;

@Service
public class AsignacionDocMatServiceImpl implements AsignacionDocMatService {
    @Autowired
    private AsignacionDocMatDAO asignacionDocMatDAO;

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionDocMat> getAsignacionesPorDocente(Docente docente) {
        return asignacionDocMatDAO.findAllByDocente(docente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionDocMat> getAsignacionesPermitidasPorDocente(Docente docente) {
        return asignacionDocMatDAO.findAllByDocenteAndEstatus(docente, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionDocMat> getDocentesPorMateria(Materia materia) {
        return asignacionDocMatDAO.findAllByMateriaAndEstatus(materia, true);
    }

    @Override
    @Transactional
    public void guardar(AsignacionDocMat asignacion) {
        asignacionDocMatDAO.save(asignacion);
    }

    @Override
    @Transactional
    public void eliminar(AsignacionDocMat asignacion) {
        asignacion.setEstatus(false);
        guardar(asignacion);
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionDocMat getAsignacion(AsignacionDocMat asignacion) {
        return asignacionDocMatDAO.findById(asignacion.getIdAsignacionDocMat()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionDocMat getAsignacion(Long idAsignacion) {
        return asignacionDocMatDAO.findById(idAsignacion).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionDocMat getAsignacion(Integer idAsignacion) {
        return asignacionDocMatDAO.findById(idAsignacion.longValue()).orElse(null);
    }
}
