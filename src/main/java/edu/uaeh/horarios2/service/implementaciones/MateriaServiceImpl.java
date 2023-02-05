package edu.uaeh.horarios2.service.implementaciones;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.MateriaDAO;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;
import edu.uaeh.horarios2.service.MateriaService;
import edu.uaeh.horarios2.service.ProgramaEducativoService;
import edu.uaeh.horarios2.service.TipoMateriaService;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaDAO materiaDAO;
    @Autowired
    private TipoMateriaService tipoMateriaService;
    @Autowired
    private ProgramaEducativoService programaEducativoService;

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMaterias() {
        return (List<Materia>) materiaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Materia materia) {
        materiaDAO.save(materia);
    }

    @Override
    @Transactional
    public void eliminar(Materia materia) {
        materiaDAO.delete(materia);
    }

    @Override
    @Transactional(readOnly = true)
    public Materia getMateria(Materia materia) {
        return materiaDAO.findById(materia.getIdMateria()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Materia getMateria(Long idMateria) {
        return materiaDAO.findById(idMateria).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Materia getMateria(Integer idMateria) {
        return materiaDAO.findById(idMateria.longValue()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasPorProgramaEducativo(ProgramaEducativo programaEducativo) {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativo(programaEducativo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasPorProgramaEducativo(ProgramaEducativo programaEducativo, Integer semestre) {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndSemestre(programaEducativo, semestre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasPorProgramaEducativo(ProgramaEducativo programaEducativo, Integer semestre, List<TipoMateria> tipos){
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndSemestreAndTipoMateriaIn(programaEducativo, semestre, tipos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasIdiomas(){
        return (List<Materia>) materiaDAO.findAllByTipoMateria(tipoMateriaService.getTipoMateria(3));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasPropedeuticas(){
        return (List<Materia>) materiaDAO.findAllByTipoMateria(tipoMateriaService.getTipoMateria(2));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasPropedeuticas(AreaPropedeutica areaPropedeutica){
        return areaPropedeutica.getMaterias();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasPropedeuticas(AreaPropedeutica areaPropedeutica, Integer semestre){
        List<Materia> propes = new ArrayList<>();
        for(Materia prope : areaPropedeutica.getMaterias()){
            if(prope.getSemestre().equals(semestre)){
                propes.add(prope);
            }
        }
        return propes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getOptativas(ProgramaEducativo programaEducativo){
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndTipoMateria(programaEducativo, tipoMateriaService.getTipoMateria(4));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasBachillerato() {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativo(programaEducativoService.getProgramaEducativo(1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasBachillerato(Integer semestre) {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndSemestre(programaEducativoService.getProgramaEducativo(1), semestre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasLTI() {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativo(programaEducativoService.getProgramaEducativo(2));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasLTI(Integer semestre) {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndSemestre(programaEducativoService.getProgramaEducativo(2), semestre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasLT() {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativo(programaEducativoService.getProgramaEducativo(3));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasLT(Integer semestre) {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndSemestre(programaEducativoService.getProgramaEducativo(3), semestre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasIAI() {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativo(programaEducativoService.getProgramaEducativo(4));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getMateriasIAI(Integer semestre) {
        return (List<Materia>) materiaDAO.findAllByProgramaEducativoAndSemestre(programaEducativoService.getProgramaEducativo(4), semestre);
    }
}
