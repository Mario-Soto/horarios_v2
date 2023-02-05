package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.AreaPropedeuticaDAO;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.service.AreaPropedeuticaService;

@Service
public class AreaPropedeuticaServiceImpl implements AreaPropedeuticaService {

    @Autowired
    private AreaPropedeuticaDAO areaPropedeuticaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<AreaPropedeutica> getAreasPropedeuticas() {
        return (List<AreaPropedeutica>) areaPropedeuticaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(AreaPropedeutica area) {
        areaPropedeuticaDAO.save(area);
    }

    @Override
    @Transactional
    public void eliminar(AreaPropedeutica area) {
        areaPropedeuticaDAO.delete(area);
    }

    @Override
    @Transactional(readOnly = true)
    public AreaPropedeutica getAreaPropedeutica(AreaPropedeutica area) {
        return areaPropedeuticaDAO.findById(area.getIdAreaPropedeutica()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AreaPropedeutica getAreaPropedeutica(Long idArea) {
        return areaPropedeuticaDAO.findById(idArea).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AreaPropedeutica getAreaPropedeutica(Integer idArea) {
        return areaPropedeuticaDAO.findById(idArea.longValue()).orElse(null);
    }
}
