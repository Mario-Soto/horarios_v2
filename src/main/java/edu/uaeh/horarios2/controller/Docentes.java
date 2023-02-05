package edu.uaeh.horarios2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;
import edu.uaeh.horarios2.service.DocenteService;
import edu.uaeh.horarios2.service.ProgramaEducativoService;
import edu.uaeh.horarios2.service.TipoEmpleadoService;
import jakarta.validation.Valid;

@Controller
public class Docentes {

    @Autowired
    DocenteService docenteService;
    @Autowired
    TipoEmpleadoService tipoEmpleadoService;
    @Autowired
    ProgramaEducativoService programaEducativoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "HOLA");
        return "template";
    }

    @GetMapping("/docentes")
    public String docentes(Model model) {
        List<Docente> docentes = docenteService.getDocentes();
        model.addAttribute("docentes", docentes);
        model.addAttribute("titulo", "Docentes");
        return "docentes/index";
    }

    @GetMapping("/docentes/nuevo")
    public String nuevoDocente(Docente docente, Model model) {
        List<TipoEmpleado> tiposEmpleado = tipoEmpleadoService.getTiposEmpleado();
        model.addAttribute("tiposEmpleado", tiposEmpleado);
        model.addAttribute("titulo", "Nuevo docente");
        return "docentes/form";
    }

    @PostMapping("/docentes/guardar")
    public String guardarDocente(@Valid Docente docente, Errors errores, Model model) {
        if (errores.hasErrors()) {
            List<TipoEmpleado> tiposEmpleado = tipoEmpleadoService.getTiposEmpleado();
            model.addAttribute("tiposEmpleado", tiposEmpleado);
            model.addAttribute("titulo", "Nuevo docente");
            return "docentes/form";
        }

        docenteService.guardar(docente);
        return "redirect:/docentes";
    }

    @GetMapping("/docentes/editar/{idDocente}")
    public String editarDocente(Docente docente, Model model) {
        docente = docenteService.getDocente(docente.getIdDocente());
        model.addAttribute("docente", docente);
        model.addAttribute("titulo", "Editar docente");
        List<TipoEmpleado> tiposEmpleado = tipoEmpleadoService.getTiposEmpleado();
        model.addAttribute("tiposEmpleado", tiposEmpleado);
        return "docentes/form";
    }

}
