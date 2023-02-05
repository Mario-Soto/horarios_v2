package edu.uaeh.horarios2.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.service.ProgramaEducativoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Slf4j
@SessionAttributes({"programasEducativos"})
public class Horarios {
    @Autowired
    ProgramaEducativoService programaEducativoService;

    @GetMapping("/generar-grupos")
    public String generarGrupos(Model model) {
        model.addAttribute("titulo", "Generar grupos - Programas educativos");
        List<ProgramaEducativo> pe = programaEducativoService.getProgramasEducativos();
        Queue<ProgramaEducativo> programasEducativos = new LinkedList<ProgramaEducativo>(pe);
        model.addAttribute("programasEducativos", programasEducativos);
        return "generacion/programas_educativos";
    }

    @PostMapping(value = "/generar-grupos", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String guardarGrupos(@RequestBody MultiValueMap<String, String> formData, Model model) {
        List<String> keys = new ArrayList<>(formData.keySet());
        List<ProgramaEducativo> programasEducativos = new ArrayList<>();
        for (String key : keys) {
            int val = Integer.parseInt(formData.get(key).get(0));
            programasEducativos.add(programaEducativoService.getProgramaEducativo(val));
        }
        model.addAttribute("programasEducativos", programasEducativos);
        return "redirect:/generar-grupos/programa-educativo/" + programasEducativos.get(0).getIdProgramaEducativo();
    }

    @GetMapping("/generar-grupos/programa-educativo/{idProgramaEducativo}")
    public String generarGruposProgramaEducativo(ProgramaEducativo programaEducativo, Model model) {
        programaEducativo = programaEducativoService.getProgramaEducativo(programaEducativo.getIdProgramaEducativo());
        model.addAttribute("titulo", "Generar grupos - " + programaEducativo.getNombre());
        model.addAttribute("programaEducativo", programaEducativo);
        return "generacion/grupos";
    }
    
    @PostMapping(value = "/generar-grupos/programa-educativo/{idProgramaEducativo}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String guardarGruposProgramaEducativo(ProgramaEducativo programaEducativo, @RequestBody MultiValueMap<String, String> formData, Model model, @ModelAttribute("programasEducativos") Queue<ProgramaEducativo> programasEducativos) {
        log.info("Programas educativos: " + programasEducativos);
        List<String> keys = new ArrayList<>(formData.keySet());
        for (String key : keys) {
            log.info(formData.get(key).get(0));
        }
        programasEducativos.poll();
        model.addAttribute("programasEducativos", programasEducativos);
        if (programasEducativos.isEmpty()) {
            return "redirect:/generar-grupos";
        }else{
            return "redirect:/generar-grupos/programa-educativo/" + programasEducativos.peek().getIdProgramaEducativo();
        }
    }
}
