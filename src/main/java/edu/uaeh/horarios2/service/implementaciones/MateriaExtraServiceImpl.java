package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.MateriaExtraDAO;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import edu.uaeh.horarios2.service.MateriaExtraService;

@Service
public class MateriaExtraServiceImpl implements MateriaExtraService{

    @Autowired
    private MateriaExtraDAO materiaExtraDAO;

    @Override
    @Transactional(readOnly = true)
    public List<MateriaExtra> getMateriasExtras() {
        return (List<MateriaExtra>) materiaExtraDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaExtra> getMateriasExtras(Grupo grupo) {
        return (List<MateriaExtra>) materiaExtraDAO.findAllByGrupo(grupo);
    }

    @Override
    @Transactional(readOnly = true)
    public MateriaExtra getMateriasExtras(Grupo grupo, Materia origen) {
        return materiaExtraDAO.findByGrupoAndMateriaOrigen(grupo, origen).orElse(null);
    }
    
    @Override
    @Transactional
    public void guardar(MateriaExtra materiaExtra){
        materiaExtraDAO.save(materiaExtra);
    }

    @Override
    @Transactional
    public void eliminar(MateriaExtra materiaExtra){
        materiaExtraDAO.delete(materiaExtra);
    }
}
