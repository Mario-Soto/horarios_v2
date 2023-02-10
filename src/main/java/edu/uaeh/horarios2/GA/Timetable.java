package edu.uaeh.horarios2.GA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uaeh.horarios2.domain.Aula;
import edu.uaeh.horarios2.domain.Clase;
import edu.uaeh.horarios2.domain.Docente;
import edu.uaeh.horarios2.domain.Grupo;
import edu.uaeh.horarios2.domain.Materia;
import edu.uaeh.horarios2.domain.Sesion;
import edu.uaeh.horarios2.domain.Timeslot;
import edu.uaeh.horarios2.domain.catalogos.MateriaExtra;
import edu.uaeh.horarios2.domain.catalogos.ProgramaEducativo;
import edu.uaeh.horarios2.domain.catalogos.TipoMateria;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
public class Timetable {
	private List<Aula> aulas;
	private List<Clase> clases;
	private List<Docente> docentes;
	private List<Grupo> grupos;
	private List<Sesion> sesiones;
	private List<Timeslot> timeslots;
	private List<Timeslot> timeslotsMatutinos;
	private List<Timeslot> timeslotsVespertinos;
	private List<Timeslot> timeslotsPracticasProfesionales;
	private List<Timeslot> timeslotsServicioSocial;
	private List<Materia> materias;
	private Integer clashes = 0;
	private Integer clashUltimaClase = 0;
	private Integer clashAulaOcupada = 0;
	private Integer clashProfesorDisponible = 0;
	private Integer clashMismaHora = 0;
	private Integer clashSesionAlDia = 0;
	private Integer clashHorasMuertas = 0;
	private Integer clashClaseALas7 = 0;
	private Integer clashClaseALas18 = 0;
	private Integer clashJornadaHomogeneaGrupos = 0;

	public HashMap<Long, Aula> aulasMap() {
		HashMap<Long, Aula> map = new HashMap<>();
		for (Aula object : this.aulas) {
			map.put(object.getIdAula(), object);
		}
		return map;
	}

	public HashMap<Long, Docente> docentesMap() {
		HashMap<Long, Docente> map = new HashMap<>();
		for (Docente object : this.docentes) {
			map.put(object.getIdDocente(), object);
		}
		return map;
	}

	public HashMap<Long, Timeslot> timeslotsMap() {
		HashMap<Long, Timeslot> map = new HashMap<>();
		for (Timeslot object : this.timeslots) {
			map.put(object.getIdTimeslot(), object);
		}
		return map;
	}

	public Timetable(Timetable clonable) {
		this.aulas = clonable.aulas;
		// this.clases = clonable.clases;
		this.docentes = clonable.docentes;
		this.grupos = clonable.grupos;
		// this.sesiones = clonable.sesiones;
		this.timeslots = clonable.timeslots;
		this.materias = clonable.materias;
		this.timeslotsMatutinos = clonable.timeslotsMatutinos;
		this.timeslotsVespertinos = clonable.timeslotsVespertinos;
		this.materias = clonable.materias;
		this.timeslotsPracticasProfesionales = clonable.timeslotsPracticasProfesionales;
		this.timeslotsServicioSocial = clonable.timeslotsServicioSocial;
		this.sesiones = new ArrayList<>();
		this.clases = new ArrayList<>();
		for (Sesion sesion : clonable.sesiones) {
			if (!sesion.getClase().esOptativa()) {
				this.sesiones.add(sesion);
			}
		}
		for (Clase clase : clonable.clases) {
			if (!clase.esOptativa()) {
				this.clases.add(clase);
			}
		}
	}

	public void addAula(Aula aula) {
		this.aulas.add(aula);
	}

	public void addClase(Clase clase) {
		this.clases.add(clase);
	}

	public void addDocente(Docente docente) {
		this.docentes.add(docente);
	}

	public void addGrupo(Grupo grupo) {
		this.grupos.add(grupo);
	}

	public void addSesion(Sesion sesion) {
		this.sesiones.add(sesion);
	}

	public void addTimeslot(Timeslot timeslot) {
		this.timeslots.add(timeslot);
	}

	public List<Materia> getMateriasPorPE(ProgramaEducativo programaEducativo, Integer semestre) {
		List<Materia> materiasPE = new ArrayList<>();
		for (Materia materia : materias) {
			if (programaEducativo.equals(materia.getProgramaEducativo()) && semestre.equals(materia.getSemestre())) {
				materiasPE.add(materia);
			}
		}
		return materiasPE;
	}

	public List<Materia> getMateriasPorPE(ProgramaEducativo programaEducativo, Integer semestre,
			List<TipoMateria> tipos) {
		List<Materia> materiasPE = new ArrayList<>();
		for (Materia materia : materias) {
			if (programaEducativo.equals(materia.getProgramaEducativo()) && semestre.equals(materia.getSemestre())
					&& tipos.contains(materia.getTipoMateria())) {
				materiasPE.add(materia);
			}
		}
		return materiasPE;
	}

	private int numeroClases = 0;

	public void createClasses(Individual individual) {
		// Get individual's chromosome
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;

		for (Grupo grupo : this.grupos) {
			for (Clase clase : grupo.getClases()) {
				for (Sesion sesion : clase.getSesiones()) {
					sesion.setInicio(this.timeslotsMap().get(Long.valueOf(chromosome[chromosomePos])));
					chromosomePos++;
					// sesion.setAula(this.aulasMap().get(Long.valueOf(chromosome[chromosomePos])));
					// chromosomePos++;
					clase.setDocente(this.docentesMap().get(Long.valueOf(chromosome[chromosomePos])));
					chromosomePos++;
				}
			}
		}

	}

	public Aula getRandomAula() {
		return this.aulas.get((int) (Math.random() * this.aulas.size()));
	}

	public Timeslot getRandomTimeslot() {
		return this.timeslots.get((int) (Math.random() * this.timeslots.size()));
	}

	public Timeslot getFirstTimeslot() {
		return this.timeslots.get(0);
	}

	public List<Timeslot> getTimeslotsTCMatutinos() {
		List<Timeslot> slots = new ArrayList<>();
		for (Timeslot slot : timeslotsMatutinos) {
			if (slot.getHoraInicio() >= 9) {
				slots.add(slot);
			}
		}
		return slots;
	}

	public List<Timeslot> getTimeslotsTCVespertinos() {
		List<Timeslot> slots = new ArrayList<>();
		for (Timeslot slot : timeslotsVespertinos) {
			if (slot.getHoraFin() <= 18) {
				slots.add(slot);
			}
		}
		return slots;
	}

	public Timeslot getRandomTimeslot(Grupo grupo, Docente docente) {
		List<Timeslot> slots;
		if (grupo.getTurno() == 1) {
			if (docente != null && docente.esTiempoCompleto()) {
				slots = this.getTimeslotsTCMatutinos();
			} else {
				slots = this.getTimeslotsMatutinos();
			}
		} else {
			if (docente != null && docente.esTiempoCompleto()) {
				slots = this.getTimeslotsTCVespertinos();
			} else {
				slots = this.getTimeslotsVespertinos();
			}
		}
		// List<Timeslot> slotsPermitidos = new ArrayList<>();
		// for (Timeslot slot : slots) {
		// if (slot.getDia().equals(dia)) {
		// slotsPermitidos.add(slot);
		// }
		// }
		return slots.get((int) (Math.random() * slots.size()));
	}

	public Timeslot getRandomTimeslotAntesDeLas8(Grupo grupo, Docente docente) {
		Timeslot slot;
		do {
			slot = getRandomTimeslot(grupo, docente);
		} while (slot.getHoraInicio() == 20);
		return slot;
	}

	public Integer getNumeroSesiones() {
		Integer sesiones = this.sesiones.size();
		for (Sesion sesion : this.sesiones) {
			if (sesion.getClase().esOptativa()) {
				sesiones--;
			}
		}

		return sesiones;
	}

	// /**
	// * Calculate the number of clashes between Classes generated by a
	// * chromosome.
	// *
	// * The most important method in this class; look at a candidate timetable
	// * and figure out how many constraints are violated.
	// *
	// * Running this method requires that createClasses has been run first (in
	// * order to populate this.classes). The return value of this method is
	// * simply the number of constraint violations (conflicting professors,
	// * timeslots, or rooms), and that return value is used by the
	// * GeneticAlgorithm.calcFitness method.
	// *
	// * There's nothing too difficult here either -- loop through this.classes,
	// * and check constraints against the rest of the this.classes.
	// *
	// * The two inner `for` loops can be combined here as an optimization, but
	// * kept separate for clarity. For small values of this.classes.length it
	// * doesn't make a difference, but for larger values it certainly does.
	// *
	// * @return numClashes
	// */
	public int calcClashes() {

		clashUltimaClase = clashUltimaClase();
		clashMismaHora = clashClaseMismaHora();
		// clashAulaOcupada = clashAulaOcupada();
		clashProfesorDisponible = clashProfesorDisponible();
		clashSesionAlDia = clashSesionAlDia();
		clashHorasMuertas = clashHorasMuertas();
		clashClaseALas7 = clashClaseALas7();
		clashClaseALas18 = clashClaseALas18();
		clashJornadaHomogeneaGrupos = clashJornadaHomogeneaGrupos();

		clashes = clashAulaOcupada + clashMismaHora + clashProfesorDisponible + clashUltimaClase + clashSesionAlDia
				+ clashHorasMuertas + clashClaseALas7 + clashClaseALas18 + clashJornadaHomogeneaGrupos;

		return clashes;
	}

	private Integer clashUltimaClase() {
		Integer clash = 0;
		// this.getSesiones().forEach(sesion -> {
		// 	if( sesion.getDuracion() <= 2 && sesion.getInicio().getHoraInicio() + sesion.getDuracion() > 21 ){
		// 		clash += 5;
		// 	}
		// });
		for (Sesion sesionA : this.getSesiones()) {
			if (sesionA.getDuracion() <= 2) {
				if (sesionA.getInicio().getHoraInicio() + sesionA.getDuracion() > 21) {
					clash += 5;
				}
			}
		}
		return clash;
	}

	private Integer clashClaseMismaHora() {
		Integer clash = 0;
		for (Sesion sesionA : this.getSesiones()) {
			// Check clases a la misma hora
			for (Sesion sesionB : this.getSesiones()) {
				if (sesionA.getClase().getGrupo().equals(sesionB.getClase().getGrupo())
						&& sesionA.equalsInTime(sesionB) && sesionA.getIdSesion() != sesionB.getIdSesion()) {
					clash += 5;
					break;
				}
			}
		}
		return clash;
	}

	// private Integer clashAulaOcupada() {
	// 	Integer clash = 0;
	// 	for (Sesion sesionA : this.getSesiones()) {

	// 		// Check if room is taken
	// 		for (Sesion sesionB : this.getSesiones()) {
	// 			if (!sesionA.getClase().esOptativa() && !sesionB.getClase().esOptativa()) {
	// 				if (sesionA.getAula().equals(sesionB.getAula()) && sesionA.equalsInTime(sesionB)
	// 						&& sesionA.getIdSesion() != sesionB.getIdSesion()) {
	// 					clash++;
	// 					break;
	// 				}
	// 			}
	// 		}
	// 	}
	// 	return clash;
	// }

	private Integer clashProfesorDisponible() {
		Integer clash = 0;
		for (Sesion sesionA : this.getSesiones()) {
			// Check if professor is available
			for (Sesion sesionB : this.getSesiones()) {
				if(sesionA.getClase().getDocente().getIdDocente() != -10 && sesionB.getClase().getDocente().getIdDocente() != -10){
					if (sesionA.getClase().getDocente().equals(sesionB.getClase().getDocente())
						&& sesionA.equalsInTime(sesionB) && sesionA.getIdSesion() != sesionB.getIdSesion()) {
						clash += 5;
						break;
					}
				}else{
					if(sesionA.getIdSesion() != sesionB.getIdSesion()){
						List<Docente> docA = new ArrayList<>();
						List<Docente> docB = new ArrayList<>();
						
						if(sesionA.getClase().getDocente().getIdDocente() == -10){
							List<MateriaExtra> extrasA = sesionA.getClase().getGrupo().getMateriaExtras();
							for(MateriaExtra extra : extrasA){
								docA.add(extra.getDocente());
							}
						}else{
							docA.add(sesionA.getClase().getDocente());
						}
	
						if(sesionB.getClase().getDocente().getIdDocente() == -10){
							List<MateriaExtra> extrasB = sesionB.getClase().getGrupo().getMateriaExtras();
							for(MateriaExtra extra : extrasB){
								docB.add(extra.getDocente());
							}
						}else{
							docB.add(sesionB.getClase().getDocente());
						}

						for(Docente a : docA){
							for(Docente b : docB){
								if(a.equals(b) && sesionA.equalsInTime(sesionB)){
									clash += 5;
									break;
								}
							}
						}
					}
				}
			}
		}
		return clash;
	}

	private Integer clashSesionAlDia() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			for (Clase clase : grupo.getClases()) {
				for (Sesion sesionA : clase.getSesiones()) {
					for (Sesion sesionB : clase.getSesiones()) {
						if (sesionA.getInicio().getDia().equals(sesionB.getInicio().getDia())
								&& sesionA.getClase().equals(sesionB.getClase())
								&& !sesionA.equals(sesionB)) {
							if (sesionA.getDuracion() == 2 && sesionB.getDuracion() == 2) {
								clash += 5;
								break;
							} else if (!(sesionA.getFinSesion() == sesionB.getInicio().getHoraInicio()
									|| sesionB.getFinSesion() == sesionA.getInicio().getHoraInicio())) {
								clash++;
								break;
							}
						}
					}
				}
			}
		}
		return clash / 2;
	}

	private Integer clashHorasMuertas() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			List<Sesion> sesiones = getSesionesOrdenadasSinPracticas(grupo);
			for (int i = 0; i < sesiones.size() - 1; i++) {
				if (!sesiones.get(i).getClase().esOptativa()
						&& sesiones.get(i).getInicio().getDia().equals(sesiones.get(i + 1).getInicio().getDia())
						&& sesiones.get(i).getInicio().getHoraInicio() + sesiones.get(i).getDuracion() != sesiones
								.get(i + 1).getInicio().getHoraInicio()) {
					clash++;
				}
			}
		}
		return clash;
	}

	private Integer clashClaseALas7() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			if (grupo.getTurno() == 1) {
				List<Sesion> sesiones = getSesionesOrdenadas(grupo);
				Integer dia = 0;
				for (Sesion sesion : sesiones) {
					if (!sesion.getClase().esOptativa()) {
						if (!sesion.getInicio().getDia().equals(dia)) {
							if (sesion.getInicio().getHoraInicio() != 7) {
								clash += 3;
							}
							dia = sesion.getInicio().getDia();
						}
					}
				}
			}
		}
		return clash;
	}

	private Integer clashClaseALas18() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			if (grupo.getTurno() == 2) {
				Sesion[][] sesiones = getEstructura(grupo);
				for (int i = 0; i < 5; i++) {
					Boolean claseALas18 = false;
					Boolean diaLibre = true;

					for (int j = 0; j < 14; j++) {
						if (sesiones[i][j] != null && !sesiones[i][j].getClase().esOptativa()) {
							if (sesiones[i][j] != null
									&& !sesiones[i][j].getClase().getMateria().esPracticasOServicio()) {
								diaLibre = false;
								if (sesiones[i][j].getInicio().getHoraInicio() == 18) {
									claseALas18 = true;
									break;
								} else if (sesiones[i][j].getInicio().getHoraInicio() == 19
										&& sesiones[i][j].getDuracion() == 2) {
									claseALas18 = true;
									break;
								}
							}
						}
					}
					if (!claseALas18 && !diaLibre) {
						clash++;
					}
				}
			}
		}
		return clash;
	}

	private Integer clashJornadaHomogeneaGrupos() {
		Integer clash = 0;
		for (Grupo grupo : grupos) {
			Integer[] horasDia = { 0, 0, 0, 0, 0 };
			// Integer[] sesionesDia = { 0, 0, 0, 0, 0 };
			for (Clase clase : grupo.getClases()) {
				if (!clase.esPracticasOServicio() && !clase.esOptativa()) {
					for (Sesion sesion : clase.getSesiones()) {
						horasDia[sesion.getInicio().getDia() - 1] += sesion.getDuracion();
						// sesionesDia[sesion.getInicio().getDia() - 1]++;
					}
				}
			}
			Integer horasSemana = 0;
			// Integer sesionesSemana = 0;
			for (int i = 0; i < horasDia.length; horasSemana += horasDia[i++])
				;
			// for (int i = 0; i < sesionesDia.length; sesionesSemana += sesionesDia[i++])
			;

			Integer diasDeClases;
			if (horasSemana <= 10) {
				diasDeClases = 2;
			} else if (horasSemana <= 15) {
				diasDeClases = 3;
			} else if (horasSemana <= 25) {
				diasDeClases = 4;
			} else {
				diasDeClases = 5;
			}
			Integer[] horasPermitidas = grupo.getHorasPermitidas();
			// Integer horasPermitidas = (int) Math.ceil(horasSemana / (diasDeClases +
			// 0.0));
			// Integer sesionesPermitidas = (int) Math.ceil(sesionesSemana / (diasDeClases +
			// 0.0));
			// Integer horasMinimas = horasPermitidas - 2;
			// Integer sesionesMinimas = (int) (sesionesPermitidas / 2);

			Integer diasCursados = 0;
			for (int i = 0; i < 5; i++) {
				// if ((horasDia[i] > horasPermitidas && sesionesDia[i] > sesionesPermitidas) ||
				// (sesionesDia[i] != 0 && sesionesDia[i] < sesionesMinimas )) {
				// clash++;
				// }
				if (horasDia[i] != 0) {
					diasCursados++;
					if (horasDia[i] > horasPermitidas[1] || horasDia[i] < horasPermitidas[0]) {
						clash++;
					}
				}
			}
			if (diasCursados > diasDeClases) {
				clash += diasCursados - diasDeClases;
			}
		}
		return clash;
	}

	public Sesion[][] getEstructura(Grupo grupo) {
		Sesion[][] estructura = new Sesion[5][14];
		for (Clase clase : grupo.getClases()) {
			if (!clase.esOptativa()) {
				for (Sesion sesion : clase.getSesiones()) {
					if (sesion != null && sesion.getInicio().getDia() != 6 && sesion.getInicio().getDia() != 7) {
						estructura[sesion.getInicio().getDia() - 1][sesion.getInicio().getHoraInicio() - 7] = sesion;
					}
				}
			}
		}
		return estructura;
	}

	public Sesion[][] getEstructuraSinPracticas(Grupo grupo) {
		Sesion[][] estructura = new Sesion[5][14];
		for (Clase clase : grupo.getClases()) {
			if (!clase.esPracticasOServicio() && !clase.esOptativa()) {
				for (Sesion sesion : clase.getSesiones()) {
					if (sesion != null && sesion.getInicio().getDia() != 6 && sesion.getInicio().getDia() != 7) {
						estructura[sesion.getInicio().getDia() - 1][sesion.getInicio().getHoraInicio() - 7] = sesion;
					}
				}
			}
		}
		return estructura;
	}

	public List<Sesion> getSesionesOrdenadas(Sesion[][] estructura) {
		List<Sesion> sesiones = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 14; j++) {
				if (estructura[i][j] != null) {
					sesiones.add(estructura[i][j]);
				}
			}
		}
		return sesiones;
	}

	public List<Sesion> getSesionesOrdenadas(Grupo grupo) {
		Sesion[][] estructura = getEstructura(grupo);
		return getSesionesOrdenadas(estructura);
	}

	public List<Sesion> getSesionesOrdenadasSinPracticas(Grupo grupo) {
		List<Sesion> sesiones = getSesionesOrdenadas(grupo);
		List<Sesion> sesionesFinal = new ArrayList<>();
		for (Sesion sesion : sesiones) {
			if (!sesion.getClase().getMateria().esPracticasOServicio()) {
				sesionesFinal.add(sesion);
			}
		}
		return sesionesFinal;
	}

}

// TODO: ASIGNACION DE DÍAS, HORAS LIMITE EN LOS HORARIOS (INF, SUP), OPTATIVAS,
// LABORATORIOS