package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.TipoMateriaDAO;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;
import edu.uaeh.horarios2.service.TipoMateriaService;

@Service
public class TipoMateriaServiceImpl implements TipoMateriaService{

    @Autowired
    private TipoMateriaDAO tipoMateriaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<TipoMateria> getTiposMateria() {
        return (List<TipoMateria>) tipoMateriaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(TipoMateria tipoMateria) {
        tipoMateriaDAO.save(tipoMateria);
    }

    @Override
    @Transactional
    public void eliminar(TipoMateria tipoMateria) {
        tipoMateriaDAO.delete(tipoMateria);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoMateria getTipoMateria(TipoMateria tipoMateria) {
        return tipoMateriaDAO.findById(tipoMateria.getIdTipoMateria()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoMateria getTipoMateria(Long idTipoMateria) {
        return tipoMateriaDAO.findById(idTipoMateria).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoMateria getTipoMateria(Integer idTipoMateria) {
        return tipoMateriaDAO.findById(idTipoMateria.longValue()).orElse(null);
    }
    
}
