package edu.uaeh.horarios2.generacionHorarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.service.ClaseService;
import edu.uaeh.horarios2.service.DocenteService;
import edu.uaeh.horarios2.service.GrupoService;
import edu.uaeh.horarios2.service.ProgramaEducativoService;
import edu.uaeh.horarios2.service.SesionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeneracionService {

    @Autowired
    private DocenteService docenteService;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private ClaseService claseService;
    @Autowired
    private SesionService sesionService;
    @Autowired
    private ProgramaEducativoService programaEducativoService;

    public HashMap<Long, Integer[][]> disponibilidadDocentes(Generacion generacionHorarios) {
        HashMap<Long, Integer[][]> disponibilidades = new HashMap<>();
        docenteService.getDocentes().forEach(docente -> {
            if (docente.getEstatus() == 1) {
                Integer[][] disponibilidadDocente = new Integer[5][14];
                docente.getDisponibilidad().forEach(disponibilidad -> {
                    disponibilidadDocente[disponibilidad.getSlot().getDia() - 1][disponibilidad.getSlot()
                            .getHoraInicio() - 7] = disponibilidad.getEstatus();
                });
                disponibilidades.put(docente.getIdDocente(), disponibilidadDocente);
            }
        });
        generacionHorarios.setEstructuraDisponibilidadDocentes(disponibilidades);
        return disponibilidades;
    }

    /*
     * 0 -> dias disponibles
     * 1 -> horas permitidas: [0] -> minimas [1] -> maximas
     */
    public HashMap<Long, HashMap<Integer, Integer[]>> datosGrupos(Generacion generacion) {
        HashMap<Long, HashMap<Integer, Integer[]>> disponibilidades = new HashMap<>();
        final List<Integer> DIAS = List.of(1, 2, 3, 4, 5);
        grupoService.getGrupos().forEach(grupo -> {
            List<Integer> dias = new ArrayList<>(DIAS);
            HashMap<Integer, Integer[]> datos = new HashMap<>();
            datos.put(1, grupo.getHorasPermitidas());
            Integer diasSemana = grupo.getDiasSemana();
            for (int i = 1; i <= 5 - diasSemana; i++) {
                dias.remove((int) (Math.random() * dias.size()));
            }

            Integer[] diasFinal = new Integer[dias.size()];
            for (int i = 0; i < dias.size(); i++) {
                diasFinal[i] = dias.get(i);
            }
            datos.put(0, diasFinal);
            disponibilidades.put(grupo.getIdGrupo(), datos);
        });
        generacion.setDatosGrupos(disponibilidades);
        return disponibilidades;
    }

    public HashMap<Long, Integer[][]> disponibilidadGrupos(Generacion generacion) {
        HashMap<Long, Integer[][]> disponibilidades = new HashMap<>();
        grupoService.getGrupos().forEach(grupo -> {
            Integer[][] disponibilidadGrupo = new Integer[5][14];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 14; j++) {
                    if (grupo.getTurno() == 1) {
                        if (j <= 7) {
                            disponibilidadGrupo[i][j] = 1;
                        } else {
                            disponibilidadGrupo[i][j] = 0;
                        }
                    } else {
                        if (j >= 6) {
                            disponibilidadGrupo[i][j] = 1;
                        } else {
                            disponibilidadGrupo[i][j] = 0;
                        }
                    }
                }
            }
            disponibilidades.put(grupo.getIdGrupo(), disponibilidadGrupo);
        });
        generacion.setEstructuraDisponibilidadGrupos(disponibilidades);
        return disponibilidades;
    }

    public HashMap<Long, Integer[][]> ajustarDisponibilidadGrupos(Generacion generacion){
        HashMap<Long, HashMap<Integer, Integer[]>> datosGrupos = datosGrupos(generacion);
        HashMap<Long, Integer[][]> disponibilidades = disponibilidadGrupos(generacion);

        grupoService.getGrupos().forEach(grupo -> {
            List<Integer> dias = new ArrayList<>(List.of(1,2,3,4,5));
            Integer max = datosGrupos.get(grupo.getIdGrupo()).get(1)[1];
            List<Integer> diasGrupo = Arrays.asList(datosGrupos.get(grupo.getIdGrupo()).get(0));
            log.info("Grupo: "+grupo.getProgramaEducativo().getNombreCorto()+"-"+grupo.getSemestre()+"° "+grupo.getGrupo()+": "+diasGrupo);
            
            dias.removeAll(diasGrupo);
            Integer[][] disponibleGrupo = disponibilidades.get(grupo.getIdGrupo());
            dias.forEach(dia -> {
                quitarColumnasDias(disponibleGrupo, dia-1);
            });
            switch(max){
                case 7:
                    if(grupo.getTurno() == 1){
                        quitarFilasHoras(disponibleGrupo, 7);
                    }else{
                        quitarFilasHoras(disponibleGrupo, 6);
                    }
                    break;
                case 6:
                    quitarFilasHoras(disponibleGrupo, 7);
                    quitarFilasHoras(disponibleGrupo, 6);
                    break;
                case 5:
                    if(grupo.getTurno() == 1){
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 5);
                    }else{
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 8);
                    }
                    break;
                case 4:
                    if(grupo.getTurno() == 1){
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 5);
                        quitarFilasHoras(disponibleGrupo, 4);
                    }else{
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 8);
                        quitarFilasHoras(disponibleGrupo, 13);
                    }
                    break;
                case 3:
                    if(grupo.getTurno() == 1){
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 5);
                        quitarFilasHoras(disponibleGrupo, 4);
                        quitarFilasHoras(disponibleGrupo, 3);
                    }else{
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 8);
                        quitarFilasHoras(disponibleGrupo, 13);
                        quitarFilasHoras(disponibleGrupo, 12);
                    }
                    break;
                case 2:
                    if(grupo.getTurno() == 1){
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 5);
                        quitarFilasHoras(disponibleGrupo, 4);
                        quitarFilasHoras(disponibleGrupo, 3);
                        quitarFilasHoras(disponibleGrupo, 2);
                    }else{
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 8);
                        quitarFilasHoras(disponibleGrupo, 13);
                        quitarFilasHoras(disponibleGrupo, 12);
                        quitarFilasHoras(disponibleGrupo, 11);
                    }
                    break;
                case 1:
                    if(grupo.getTurno() == 1){
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 5);
                        quitarFilasHoras(disponibleGrupo, 4);
                        quitarFilasHoras(disponibleGrupo, 3);
                        quitarFilasHoras(disponibleGrupo, 2);
                        quitarFilasHoras(disponibleGrupo, 1);
                    }else{
                        quitarFilasHoras(disponibleGrupo, 6);
                        quitarFilasHoras(disponibleGrupo, 7);
                        quitarFilasHoras(disponibleGrupo, 8);
                        quitarFilasHoras(disponibleGrupo, 13);
                        quitarFilasHoras(disponibleGrupo, 12);
                        quitarFilasHoras(disponibleGrupo, 11);
                        quitarFilasHoras(disponibleGrupo, 10);
                    }
                    break;
            }
        });
        generacion.setEstructuraDisponibilidadGrupos(disponibilidades);
        return disponibilidades;
    }

    public void quitarFilasHoras(Integer[][] datos, Integer fila){
        for( int i = 0; i < 5; i++){
            datos[i][fila] = 0;
        }
    }

    public void quitarColumnasDias(Integer[][] datos, Integer columna){
        for(int i = 0; i < 14; i++){
            datos[columna][i] = 0;
        }
    }

    public HashMap<Long, List<Long>> asignarDocentes(Generacion generacion){
        HashMap<Long, Integer> horasSemanaDocente = new HashMap<>();
        HashMap<Long, List<Long>> clasesImpartidas = new HashMap<>();

        List<Clase> clases = claseService.getClases();

        clases.sort((c1, c2) -> {
            int doc1 = c1.getMateria().getAsignaciones().size();
            int doc2 = c2.getMateria().getAsignaciones().size();
            
            if(c1.getMateria().esMateriaRelacionadaUno()){
                doc1 = c1.getGrupo().getMateriaDestino(c1.getMateria()).getAsignaciones().size();
            }
            if(c2.getMateria().esMateriaRelacionadaUno()){
                doc2 = c2.getGrupo().getMateriaDestino(c2.getMateria()).getAsignaciones().size();
            }

            return doc1 - doc2;
        });

        clases.forEach(clase -> {
            log.info("Clase: "+clase.getMateria().getMateria());
            boolean indicador = false;
            Long idDocente = Long.valueOf(0);
            Integer horas = 0;
            int contador = 0;
            boolean permitidoRegistrar = true;
            do{
                indicador = false;
                if(contador == 10){
                    log.info("No es posible obtener un docente");
                    log.info("Clase: "+clase.getIdClase()+" -> "+clase.getMateria().getMateria());
                    permitidoRegistrar = false;
                    idDocente = Long.valueOf(-1);
                    break;
                }
                if(clase.getMateria().esMateriaRelacionadaUno()){
                    idDocente = clase.getGrupo().getMateriaDestino(clase.getMateria()).getRandomDocentePermitido().getIdDocente();
                }else if(clase.getMateria().esMateriaRelacionadaDos()){
                    List<Materia> materiasExtras = new ArrayList<>();
                    clase.getGrupo().getMateriaExtras().forEach(materiaExtra -> {
                        if(materiaExtra.getMateriaOrigen().getIdMateria().equals(clase.getMateria().getIdMateria())){
                            materiasExtras.add(materiaExtra.getMateria());
                        }
                    });
                    
                    materiasExtras.forEach(materia -> {
                        Long id = materia.getRandomDocentePermitido().getIdDocente();
                        Integer horasSem;
                        if(horasSemanaDocente.get(id) == null){
                            horasSem = materia.getHorasSemana();
                        }else{
                            horasSem = horasSemanaDocente.get(id) + materia.getHorasSemana();
                        }

                        horasSemanaDocente.put(id, horasSem);
                        if(clasesImpartidas.get(id) == null){
                            List<Long> lista = new ArrayList<>();
                            lista.add(clase.getIdClase());
                            clasesImpartidas.put(id, lista);
                        }else{
                            clasesImpartidas.get(id).add(clase.getIdClase());
                        }
                    });
                    permitidoRegistrar = false;
                    break;
                }else{
                    idDocente = clase.getMateria().getRandomDocentePermitido().getIdDocente();
                }

                if( horasSemanaDocente.get(idDocente) == null ){
                    horas = clase.getMateria().getHorasSemana();
                }else{
                    horas = horasSemanaDocente.get(idDocente) + clase.getMateria().getHorasSemana();
                    if(horas > docenteService.getDocente(idDocente).getHorasPermitidas()){
                        indicador = true;
                        contador++;
                    }
                }
            }while(indicador);

            if(permitidoRegistrar){
                horasSemanaDocente.put(idDocente, horas);
                if(clasesImpartidas.get(idDocente) == null){
                    List<Long> lista = new ArrayList<>();
                    lista.add(clase.getIdClase());
                    clasesImpartidas.put(idDocente, lista);
                }else{
                    clasesImpartidas.get(idDocente).add(clase.getIdClase());
                }
            }
        });

        generacion.setClasesImpartidasPorDocentes(clasesImpartidas);
        this.guardarClasesGeneradas(generacion);
        return clasesImpartidas;
    }

    public void guardarClasesGeneradas(Generacion generacion){
        generacion.getClasesImpartidasPorDocentes().keySet().forEach(key -> {
            Docente docente = docenteService.getDocente(key);
            generacion.getClasesImpartidasPorDocentes().get(key).forEach(idClase -> {
                Clase clase = claseService.getClase(idClase);
                clase.setDocente(docente);
                claseService.guardar(clase);
            });
        });
    }

    public HashMap<Long,HashMap<Integer, List<Integer>>> generarDisponibilidadesGrupos(Generacion generacion){
        HashMap<Long,HashMap<Integer, List<Integer>>> disponibilidadGrupos = new HashMap<>();
        this.ajustarDisponibilidadGrupos(generacion);
        generacion.getEstructuraDisponibilidadGrupos().keySet().forEach(key -> {
            HashMap<Integer, List<Integer>> disponibilidades = new HashMap<>();
            for(int i = 0; i < 5; i++){
                List<Integer> lista = new ArrayList<>();
                for(int j = 0; j < 14; j++){
                    if(generacion.getEstructuraDisponibilidadGrupos().get(key)[i][j] == 1){
                        lista.add(j);
                    }
                }
                if(!lista.isEmpty()){
                    disponibilidades.put(i, lista);
                }
            }
            disponibilidadGrupos.put(key, disponibilidades);
        });
        
        generacion.setDisponibilidadGrupos(disponibilidadGrupos);
        return disponibilidadGrupos;
    }

    public void asignarClases(Generacion generacion){
        grupoService.getGrupos().forEach(grupo -> {
            if(!grupo.getProgramaEducativo().getNombreCorto().equals("Bach")){
                grupo.getClases().forEach(clase -> {
                    
                });
            }
        });
    }

    public void asignarPropedeuticos(){
        final ProgramaEducativo PROGRAMA = programaEducativoService.getProgramaEducativoPorNombreCorto("Bach");
        List<Grupo> grupos4 = grupoService.getGruposPorSemestre(PROGRAMA, 4);
        List<Grupo> grupos5 = grupoService.getGruposPorSemestre(PROGRAMA, 5);
        List<Grupo> grupos6 = grupoService.getGruposPorSemestre(PROGRAMA, 6);
        int cantidadGrupos4 = grupos4.size();
        int cantidadGrupos5 = grupos5.size();

        if ( cantidadGrupos4 > cantidadGrupos5){
            // GRUPOS DE 5 SE VAN A 13-15
        }else{
            // GRUPOS DE 4 y 6 SE VAN A 13-15
        }
    }
}
