package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.ClaseDAO;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.service.ClaseService;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseDAO claseDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Clase> getClases() {
        return (List<Clase>) claseDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Clase clase) {
        claseDAO.save(clase);
    }

    @Override
    @Transactional
    public void eliminar(Clase clase) {
        claseDAO.delete(clase);
    }

    @Override
    @Transactional(readOnly = true)
    public Clase getClase(Clase clase) {
        return claseDAO.findById(clase.getIdClase()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Clase getClase(Long idClase) {
        return claseDAO.findById(idClase).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Clase getClase(Integer idClase) {
        return claseDAO.findById(idClase.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clase> getClasesPorGrupo(Grupo grupo) {
        return (List<Clase>) claseDAO.findAllByGrupo(grupo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clase> getClasesPorDocente(Docente docente) {
        return (List<Clase>) claseDAO.findAllByDocente(docente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clase> getClasesPorMateria(Materia materia) {
        return (List<Clase>) claseDAO.findAllByMateria(materia);
    }
}
