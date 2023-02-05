package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.GrupoDAO;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.service.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService{

    @Autowired
    private GrupoDAO grupoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> getGrupos() {
        return (List<Grupo>) grupoDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Grupo grupo) {
        grupoDAO.save(grupo);
    }

    @Override
    @Transactional
    public void eliminar(Grupo grupo) {
        grupoDAO.delete(grupo);
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getGrupo(Grupo grupo) {
        return grupoDAO.findById(grupo.getIdGrupo()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getGrupo(Long idGrupo) {
        return grupoDAO.findById(idGrupo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getGrupo(Integer idGrupo) {
        return grupoDAO.findById(idGrupo.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getGrupo(ProgramaEducativo programaEducativo, Integer semestre, Integer grupo){
        return grupoDAO.findByProgramaEducativoAndSemestreAndGrupo(programaEducativo, semestre, grupo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> getGruposPorTurno(Integer turno) {
        return (List<Grupo>) grupoDAO.findAllByTurno(turno);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> getGruposMatutinos() {
        return (List<Grupo>) grupoDAO.findAllByTurno(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> getGruposVespertinos() {
        return (List<Grupo>) grupoDAO.findAllByTurno(2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grupo> getGruposPorProgramaEducativo(ProgramaEducativo programaEducativo) {
        return (List<Grupo>) grupoDAO.findAllByProgramaEducativo(programaEducativo);
    }
    
}
