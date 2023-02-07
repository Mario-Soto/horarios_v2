package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.ProgramaEducativoDAO;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.service.ProgramaEducativoService;

@Service
public class ProgramaEducativoServiceImpl implements ProgramaEducativoService {

    @Autowired
    private ProgramaEducativoDAO programaEducativoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ProgramaEducativo> getProgramasEducativos() {
        return (List<ProgramaEducativo>) programaEducativoDAO.findAllByEstatus(true);
    }

    @Override
    @Transactional
    public void guardar(ProgramaEducativo programaEducativo) {
        programaEducativoDAO.save(programaEducativo);
    }

    @Override
    @Transactional
    public void eliminar(ProgramaEducativo programaEducativo) {
        programaEducativo.setEstatus(false);
        programaEducativoDAO.save(programaEducativo);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramaEducativo getProgramaEducativo(ProgramaEducativo programaEducativo) {
        return programaEducativoDAO.findById(programaEducativo.getIdProgramaEducativo()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramaEducativo getProgramaEducativo(Long idProgramaEducativo) {
        return programaEducativoDAO.findById(idProgramaEducativo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramaEducativo getProgramaEducativo(Integer idProgramaEducativo) {
        return programaEducativoDAO.findById(idProgramaEducativo.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramaEducativo getProgramaEducativoPorNombreCorto(String nombre) {
        return programaEducativoDAO.findByNombreCorto(nombre).orElse(null);
    }
    
    @Override
    public HashMap<Long,ProgramaEducativo> getMap(List<ProgramaEducativo> lista){
        HashMap<Long, ProgramaEducativo> map = new HashMap<>();
        for(ProgramaEducativo pe : lista){
            map.put(pe.getIdProgramaEducativo(), pe);
        }
        return map;
    }
}
