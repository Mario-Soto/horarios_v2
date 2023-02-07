package edu.uaeh.horarios2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.AreaPropedeutica;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.service.ProgramaEducativoService;
import edu.uaeh.horarios2.service.AreaPropedeuticaService;
import edu.uaeh.horarios2.service.GrupoService;
import edu.uaeh.horarios2.service.HorariosService;
import edu.uaeh.horarios2.service.MateriaService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Slf4j
@SessionAttributes({ "programasEducativos" })
public class Horarios {
    @Autowired
    ProgramaEducativoService programaEducativoService;
    @Autowired
    HorariosService horariosService;
    @Autowired
    AreaPropedeuticaService areaPropedeuticaService;
    @Autowired
    MateriaService materiaService;

    @Autowired
    GrupoService grupoService;

    @ModelAttribute("programasEducativos")
    HashMap<Long, ProgramaEducativo> getProgramas(){
        return new HashMap<>();
    }

    @GetMapping("/generar-grupos")
    public String generarGrupos(Model model) {
        model.addAttribute("titulo", "Generar grupos - Programas educativos");
        List<ProgramaEducativo> programasEducativos = programaEducativoService.getProgramasEducativos();

        model.addAttribute("programasEducativosList", programasEducativos);
        return "generacion/programas_educativos";
    }

    @PostMapping(value = "/generar-grupos", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String guardarGrupos(@RequestBody MultiValueMap<String, String> formData, Model model) {
        horariosService.eliminarGrupos();
        List<String> keys = new ArrayList<>(formData.keySet());
        HashMap<Long, ProgramaEducativo> programasEducativos = new HashMap<>();
        for (String key : keys) {
            Long val = Long.parseLong(formData.get(key).get(0));
            programasEducativos.put(val, programaEducativoService.getProgramaEducativo(val));
        }
        List<Long> peKeys = new ArrayList<>(programasEducativos.keySet());
        model.addAttribute("programasEducativos", programasEducativos);
        return "redirect:/generar-grupos/programa-educativo/"
                + programasEducativos.get(peKeys.get(0)).getIdProgramaEducativo();
    }

    @GetMapping("/generar-grupos/programa-educativo/{idProgramaEducativo}")
    public String generarGruposProgramaEducativo(ProgramaEducativo programaEducativo, Model model) {
        programaEducativo = programaEducativoService.getProgramaEducativo(programaEducativo.getIdProgramaEducativo());
        model.addAttribute("titulo", "Generar grupos - " + programaEducativo.getNombre());
        model.addAttribute("programaEducativo", programaEducativo);
        return "generacion/grupos";
    }

    @PostMapping(value = "/generar-grupos/programa-educativo/{idProgramaEducativo}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String guardarGruposProgramaEducativo(ProgramaEducativo programaEducativo,
            @RequestBody MultiValueMap<String, String> formData, Model model,
            @ModelAttribute("programasEducativos") HashMap<Long, ProgramaEducativo> programasEducativos) {
        programaEducativo = programaEducativoService.getProgramaEducativo(programaEducativo.getIdProgramaEducativo());
        log.info("Programas educativos: " + programasEducativos);
        horariosService.generaGrupos(programaEducativo, formData);
        programasEducativos.remove(programaEducativo.getIdProgramaEducativo());
        if (programasEducativos.isEmpty()) {
            programasEducativos = programaEducativoService.getMap(grupoService.getProgramasEducativos());
            model.addAttribute("programasEducativos", programasEducativos);
            List<Long> peKeys = new ArrayList<>(programasEducativos.keySet());
            return "redirect:/generar-materias/programa-educativo/"
                    + programasEducativos.get(peKeys.get(0)).getIdProgramaEducativo();
        }
        model.addAttribute("programasEducativos", programasEducativos);
        List<Long> peKeys = new ArrayList<>(programasEducativos.keySet());
        return "redirect:/generar-grupos/programa-educativo/"
                + programasEducativos.get(peKeys.get(0)).getIdProgramaEducativo();

    }

    @GetMapping(value = "/generar-materias")
    public String generarMaterias(Model model, @ModelAttribute("programasEducativos") HashMap<Long, ProgramaEducativo> programasEducativos){
        if(programasEducativos.isEmpty()){
            programasEducativos = programaEducativoService.getMap(grupoService.getProgramasEducativos());
            if(programasEducativos.isEmpty()){
                return "redirect:/generar-grupos";
            }
        }
        model.addAttribute("programasEducativos", programasEducativos);
        List<Long> peKeys = new ArrayList<>(programasEducativos.keySet());
        return "redirect:/generar-materias/programa-educativo/" + programasEducativos.get(peKeys.get(0)).getIdProgramaEducativo();
    }

    @GetMapping("/generar-materias/programa-educativo/{idProgramaEducativo}")
    public String generarMateriasProgramaEducativo(ProgramaEducativo programaEducativo, Model model) {
        programaEducativo = programaEducativoService.getProgramaEducativo(programaEducativo.getIdProgramaEducativo());
        model.addAttribute("titulo", "Elegir materias - " + programaEducativo.getNombre());
        List<Grupo> grupos = grupoService.getGruposPorProgramaEducativo(programaEducativo);
        model.addAttribute("grupos", grupos);

        if (programaEducativo.getNombre().equals("Bachillerato")) {
            List<AreaPropedeutica> areas = areaPropedeuticaService.getAreasPropedeuticas();
            List<Materia> materias = materiaService.getMateriasBachillerato();
            List<Materia> idiomas = materiaService.getMateriasIdiomas();
            model.addAttribute("materias", materias);
            model.addAttribute("idiomas", idiomas);
            model.addAttribute("areas", areas);
            return "generacion/materias-bach";
        }
        List<Materia> materias = materiaService.getMateriasPorProgramaEducativo(programaEducativo);
        List<Materia> optativas = materiaService.getOptativas(programaEducativo);
        model.addAttribute("materias", materias);
        model.addAttribute("optativas", optativas);
        return "generacion/materias";
    }

    @PostMapping(value = "/generar-materias/programa-educativo/{idProgramaEducativo}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String guardarMateriasProgramaEducativo(ProgramaEducativo programaEducativo,
            @RequestBody MultiValueMap<String, String> formData, Model model,
            @ModelAttribute("programasEducativos") HashMap<Long, ProgramaEducativo> programasEducativos) {
        programaEducativo = programaEducativoService.getProgramaEducativo(programaEducativo.getIdProgramaEducativo());
        horariosService.generaClases(programaEducativo, formData);
        programasEducativos.remove(programaEducativo.getIdProgramaEducativo());
        if (programasEducativos.isEmpty()) {
            return "redirect:/generar-grupos";
        }
        List<Long> peKeys = new ArrayList<>(programasEducativos.keySet());
        return "redirect:/generar-materias/programa-educativo/"
                + programasEducativos.get(peKeys.get(0)).getIdProgramaEducativo();
    }

    @GetMapping("/generar-horario")
    public String generarHorario(Model model){
        model.addAttribute("titulo", "Generar horario");
        return "generacion/empezar";
    }
}
