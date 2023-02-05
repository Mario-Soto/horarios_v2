package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.AulaDAO;
import edu.uaeh.horarios2.domain.Aula;
import edu.uaeh.horarios2.service.AulaService;
import edu.uaeh.horarios2.service.TipoAulaService;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaDAO aulaDAO;
    @Autowired
    private TipoAulaService tipoAulaService;

    @Override
    @Transactional(readOnly = true)
    public List<Aula> getAulas() {
        return (List<Aula>) aulaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Aula aula) {
        aulaDAO.save(aula);
    }

    @Override
    @Transactional
    public void eliminar(Aula aula) {
        aulaDAO.delete(aula);
    }

    @Override
    @Transactional(readOnly = true)
    public Aula getAula(Aula aula) {
        return aulaDAO.findById(aula.getIdAula()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Aula getAula(Long idAula) {
        return aulaDAO.findById(idAula).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Aula getAula(Integer idAula) {
        return aulaDAO.findById(idAula.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aula> getAulasNormales() {
        return aulaDAO.findAllByTipoAndEstatus(tipoAulaService.getTipoAula(1), true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aula> getLaboratorios() {
        return aulaDAO.findAllByTipoAndEstatus(tipoAulaService.getTipoAula(2), true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aula> getAulasComputo() {
        return aulaDAO.findAllByTipoAndEstatus(tipoAulaService.getTipoAula(3), true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aula> getAulasEspeciales() {
        return aulaDAO.findAllByTipoAndEstatus(tipoAulaService.getTipoAula(4), true);
    }
}
