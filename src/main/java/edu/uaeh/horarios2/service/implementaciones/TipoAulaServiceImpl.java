package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.TipoAulaDAO;
import edu.uaeh.horarios2.domain.catalogos.TipoAula;
import edu.uaeh.horarios2.service.TipoAulaService;

@Service
public class TipoAulaServiceImpl implements TipoAulaService {

    @Autowired
    private TipoAulaDAO tipoAulaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<TipoAula> getTiposAula() {
        return (List<TipoAula>) tipoAulaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(TipoAula tipoAula) {
        tipoAulaDAO.save(tipoAula);
    }

    @Override
    @Transactional
    public void eliminar(TipoAula tipoAula) {
        tipoAulaDAO.delete(tipoAula);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAula getTipoAula(TipoAula tipoAula) {
        return tipoAulaDAO.findById(tipoAula.getIdTipoAula()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAula getTipoAula(Long idTipo) {
        return tipoAulaDAO.findById(idTipo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAula getTipoAula(Integer idTipo) {
        return tipoAulaDAO.findById(idTipo.longValue()).orElse(null);
    }
}
