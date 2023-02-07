package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.GrupoPropedeuticoDAO;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.GrupoPropedeutico;
import edu.uaeh.horarios2.service.GrupoPropedeuticoService;

@Service
public class GrupoPropedeuticoServiceImpl implements GrupoPropedeuticoService{

    @Autowired
    private GrupoPropedeuticoDAO grupoPropedeuticoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoPropedeutico> getGruposPropedeuticos() {
        return (List<GrupoPropedeutico>) grupoPropedeuticoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoPropedeutico getGrupoPropedeutico(GrupoPropedeutico grupo) {
        return grupoPropedeuticoDAO.findById(grupo.getIdGrupoPropedeutico()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoPropedeutico getGrupoPropedeutico(Grupo grupo) {
        return grupoPropedeuticoDAO.findByGrupo(grupo).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(GrupoPropedeutico grupo) {
        grupoPropedeuticoDAO.save(grupo);
    }

    @Override
    @Transactional
    public void eliminar(GrupoPropedeutico grupo) {
        grupoPropedeuticoDAO.delete(grupo);
    }
    
}
