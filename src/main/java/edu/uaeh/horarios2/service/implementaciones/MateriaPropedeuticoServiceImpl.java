package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.db.MateriaPropedeuticoDAO;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.MateriaPropedeutico;
import edu.uaeh.horarios2.service.MateriaPropedeuticoService;
import edu.uaeh.horarios2.service.MateriaService;

@Service
public class MateriaPropedeuticoServiceImpl implements MateriaPropedeuticoService{

    @Autowired
    private MateriaPropedeuticoDAO materiaPropedeuticoDAO;
    @Autowired
    private MateriaService materiaService;

    @Override
    @Transactional(readOnly = true)
    public List<MateriaPropedeutico> getMateriasPropedeuticos() {
        return (List<MateriaPropedeutico>) materiaPropedeuticoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaPropedeutico> getMateriasPropedeuticos(AreaPropedeutica area) {
        return (List<MateriaPropedeutico>) materiaPropedeuticoDAO.findAllByArea(area);
    }

    @Override
    @Transactional(readOnly = true)
    public MateriaPropedeutico getMateriasPropedeuticos(AreaPropedeutica area, Materia origen) {
        return materiaPropedeuticoDAO.findByAreaAndMateriaOrigen(area, origen).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaPropedeutico> getMateriasPropedeuticos(AreaPropedeutica area, Integer semestre) {
        List<Materia> materias = materiaService.getMateriasPropedeuticas(area, semestre);
        List<MateriaPropedeutico> materiasPrope = this.getMateriasPropedeuticos(area);
        List<MateriaPropedeutico> result = new ArrayList<>();
        for(Materia materia : materias){
            for(MateriaPropedeutico materiaPrope : materiasPrope){
                if(materiaPrope.getMateria().equals(materia)){
                    result.add(materiaPrope);
                }
            }
        }
        return result;
    }
    
}
