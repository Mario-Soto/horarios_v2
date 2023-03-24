package edu.uaeh.horarios2.GA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import edu.uaeh.horarios2.generacionHorarios.Generacion;
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
	private HashMap<Long, List<Timeslot>> disponibilidadGrupos;

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
		this.sesiones = clonable.sesiones;
		this.clases = clonable.clases;
		this.disponibilidadGrupos = clonable.disponibilidadGrupos;
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

	private Boolean verificaSlotTiempoCompleto4738(Timeslot slot) {
		if (slot.getHoraInicio() >= 9) {
			return true;
		}
		return false;
	}

	private Boolean verificaSlotTiempoCompleto(Timeslot slot) {
		if (slot.getHoraFin() <= 18) {
			return true;
		}
		return false;
	}

	private Boolean verificaSlotFin(Timeslot slot, Integer duracion, Integer horaSalida) {
		if (slot.getHoraInicio() + duracion <= horaSalida) {
			return true;
		}
		return false;
	}

	public List<Timeslot> getTimeslotsDisponibles(Grupo grupo, Docente docente) {
		List<Timeslot> slots = this.getDisponibilidadGrupos().get(grupo.getIdGrupo());

		if (docente.esTiempoCompleto()) {
			slots = slots.stream().filter(slot -> verificaSlotTiempoCompleto4738(slot)).collect(Collectors.toList());
			if (docente.getTipoEmpleado().getTipo().contains("Tiempo Completo")) {
				slots = slots.stream().filter(slot -> verificaSlotTiempoCompleto(slot)).collect(Collectors.toList());
			}
		}

		return slots;
	}

	public Timeslot getRandomTimeslot(Grupo grupo, Docente docente) {
		List<Timeslot> slots = this.getTimeslotsDisponibles(grupo, docente);
		return slots.get((int) (Math.random() * slots.size()));
	}

	public Timeslot getRandomTimeslot(Grupo grupo, Docente docente, Integer duracion) {
		List<Timeslot> slots = this.getTimeslotsDisponibles(grupo, docente);
		slots = slots.stream().filter(slot -> verificaSlotFin(slot, duracion, grupo.getHoraSalida())).collect(Collectors.toList());
		return slots.get((int) (Math.random() * slots.size()));
	}

	public Integer getNumeroSesiones() {
		Integer sesiones = this.sesiones.size();
		// for (Sesion sesion : this.sesiones) {
		// if (sesion.getClase().esOptativa()) {
		// sesiones--;
		// }
		// }

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

		// clashUltimaClase = clashUltimaClase();
		clashMismaHora = clashClaseMismaHora();
		clashHorasMuertas = clashHorasMuertas();
		clashClaseALas7 = clashClaseALas7();
		clashSesionAlDia = clashSesionAlDia();
		clashProfesorDisponible = clashProfesorDisponible();
		//clashJornadaHomogeneaGrupos = clashJornadasGrupos();
		// clashAulaOcupada = clashAulaOcupada();
		// clashClaseALas18 = clashClaseALas18();

		clashes = clashAulaOcupada + clashMismaHora + clashProfesorDisponible + clashUltimaClase + clashSesionAlDia
				+ clashHorasMuertas + clashClaseALas7 + clashClaseALas18 + clashJornadaHomogeneaGrupos;

		return clashes;
	}

	private Integer clashUltimaClase() {
		Integer clash = 0;
		for (Sesion sesionA : this.getSesiones()) {
			if (sesionA.getDuracion() <= 3) {
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
				if (sesionA.getClase().getGrupo().getIdGrupo() == sesionB.getClase().getGrupo().getIdGrupo()
						&& sesionA.equalsInTime(sesionB)) {
					if (sesionA.getClase().esOptativa() && sesionB.getClase().esOptativa()) {
						if (sesionA.getClase().getMateriaFinal().getIdMateria() == sesionB.getClase().getMateriaFinal()
								.getIdMateria() && sesionA.getIdSesion() != sesionB.getIdSesion()) {
							clash += 5;
							break;
						}

					} else {
						if (sesionA.getIdSesion() != sesionB.getIdSesion()) {
							clash += 5;
							break;
						}
					}
				}
			}
		}
		return clash;
	}

	private Integer clashHorasMuertas() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			List<Sesion> sesiones = getSesionesTimeslotsGrupo(grupo);
			for (int i = 0; i < sesiones.size() - 1; i++) {
				if (sesiones.get(i).getInicio().getDia() == sesiones.get(i + 1).getInicio().getDia()) {
					if (sesiones.get(i).getInicio().getHoraInicio() + sesiones.get(i).getDuracion() != sesiones
							.get(i + 1).getInicio().getHoraInicio()) {
						clash += 3;
					}
				}
			}
		}
		return clash;
	}

	private Integer clashClaseALas7() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			List<Timeslot> slots = getTimeslotsGrupo(grupo);
			Integer horaInicio = this.getDisponibilidadGrupos().get(grupo.getIdGrupo()).get(0).getHoraInicio();
			Integer dia = 0;
			int i = 0;
			while (dia < 5 && i < slots.size()) {
				if (slots.get(i).getDia() != dia) {
					if (slots.get(i).getHoraInicio() != horaInicio) {
						clash += 2;
					}
					dia = slots.get(i).getDia();
				}
				i++;
			}
		}
		return clash;
	}

	private Integer clashSesionAlDia() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			List<Sesion> sesiones = getSesionesTimeslotsGrupo(grupo);
			for (int i = 0; i < sesiones.size() - 1; i++) {
				Sesion sesionA = sesiones.get(i);
				for (int j = i + 1; j < sesiones.size()
						&& sesionA.getInicio().getDia() == sesiones.get(j).getInicio().getDia(); j++) {
					Sesion sesionB = sesiones.get(j);
					if (sesionA.getClase().getMateriaFinal().getIdMateria() == sesionB.getClase().getMateriaFinal()
							.getIdMateria() && sesionA.getDuracion() + sesionB.getDuracion() >= 4) {
						clash += 5;
						break;
					}
				}
			}
		}
		return clash;
	}

	private Integer clashProfesorDisponible() {
		Integer clash = 0;
		for (Sesion sesionA : this.getSesiones()) {
			// Check if professor is available
			for (Sesion sesionB : this.getSesiones()) {
				if (sesionA.getClase().getDocente().equals(sesionB.getClase().getDocente())
						&& sesionA.equalsInTime(sesionB) && sesionA.getIdSesion() != sesionB.getIdSesion()) {
					clash += 5;
					break;
				}
			}
		}
		return clash;
	}

	private Integer clashJornadasGrupos() {
		Integer clash = 0;
		for (Grupo grupo : this.grupos) {
			List<Sesion> sesiones = getSesionesTimeslotsGrupo(grupo);
			Integer dia = 0;
			if(grupo.getTurno() == 1){
				for (int i = 0; i < sesiones.size() - 1; i++) {
					if (sesiones.get(i).getInicio().getDia() != dia) {
						if(sesiones.get(i).getInicio().getHoraInicio() - grupo.getHoraEntrada() > 1){
							clash += 5;
						}
						if(dia != 0 && sesiones.get(i-1).getInicio().getHoraInicio() + sesiones.get(i-1).getDuracion() - grupo.getHoraSalida() > 0){
							clash += 5;
						}
					}
				}
			}
		}
		return clash;
	}

	// private Integer clashAulaOcupada() {
	// Integer clash = 0;
	// for (Sesion sesionA : this.getSesiones()) {

	// // Check if room is taken
	// for (Sesion sesionB : this.getSesiones()) {
	// if (!sesionA.getClase().esOptativa() && !sesionB.getClase().esOptativa()) {
	// if (sesionA.getAula().equals(sesionB.getAula()) &&
	// sesionA.equalsInTime(sesionB)
	// && sesionA.getIdSesion() != sesionB.getIdSesion()) {
	// clash++;
	// break;
	// }
	// }
	// }
	// }
	// return clash;
	// }

	// private Integer clashClaseALas18() {
	// Integer clash = 0;
	// for (Grupo grupo : this.grupos) {
	// if (grupo.getTurno() == 2) {
	// Sesion[][] sesiones = getEstructura(grupo);
	// for (int i = 0; i < 5; i++) {
	// Boolean claseALas18 = false;
	// Boolean diaLibre = true;

	// for (int j = 0; j < 14; j++) {
	// if (sesiones[i][j] != null && !sesiones[i][j].getClase().esOptativa()) {
	// if (sesiones[i][j] != null
	// && !sesiones[i][j].getClase().getMateria().esPracticasOServicio()) {
	// diaLibre = false;
	// if (sesiones[i][j].getInicio().getHoraInicio() == 18) {
	// claseALas18 = true;
	// break;
	// } else if (sesiones[i][j].getInicio().getHoraInicio() == 19
	// && sesiones[i][j].getDuracion() == 2) {
	// claseALas18 = true;
	// break;
	// }
	// }
	// }
	// }
	// if (!claseALas18 && !diaLibre) {
	// clash++;
	// }
	// }
	// }
	// }
	// return clash;
	// }

	private Integer clashJornadaHomogeneaGrupos() {
		Integer clash = 0;
		for (Grupo grupo : grupos) {
			Integer[] horasDia = { 0, 0, 0, 0, 0 };
			// Integer[] sesionesDia = { 0, 0, 0, 0, 0 };
			for (Clase clase : grupo.getClases()) {
				if (!clase.esPracticasOServicio() && !clase.esOptativa()) {
					// log.info("Clase: " + clase.getMateriaFinal().getMateria());
					for (Sesion sesion : clase.getSesiones()) {
						// log.info("Sesion: " + sesion.getInicio().getDia() + " " +
						// sesion.getInicio().getHoraInicio() + " "
						// + sesion.getDuracion());
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

	public List<Sesion> getSesionesTimeslotsGrupo(Grupo grupo) {
		List<Sesion> sesiones = new ArrayList<>();
		Boolean agregarOptativa = true;
		for (Clase clase : grupo.getClases()) {
			if (!clase.esPracticasOServicio()) {
				if (clase.esOptativa()) {
					if (agregarOptativa) {
						for (Sesion sesion : clase.getSesiones()) {
							sesiones.add(sesion);
						}
						agregarOptativa = false;
					}
				} else {
					for (Sesion sesion : clase.getSesiones()) {
						sesiones.add(sesion);
					}
				}
			}
		}

		sesiones.sort((t1, t2) -> {
			return t1.getInicio().getDia() * 100 + t1.getInicio().getHoraInicio() - t2.getInicio().getDia() * 100
					- t2.getInicio().getHoraInicio();
		});
		return sesiones;
	}

	public List<Timeslot> getTimeslotsGrupo(Grupo grupo) {
		List<Timeslot> timeslots = new ArrayList<>();
		Boolean agregarOptativa = true;
		for (Clase clase : grupo.getClases()) {
			if (!clase.esPracticasOServicio()) {
				if (clase.esOptativa()) {
					if (agregarOptativa) {
						for (Sesion sesion : clase.getSesiones()) {
							if (sesion.getInicio().getDia() != 6 && sesion.getInicio().getDia() != 7) {
								timeslots.add(sesion.getInicio());
							}
						}
						agregarOptativa = false;
					}
				} else {
					for (Sesion sesion : clase.getSesiones()) {
						if (sesion.getInicio().getDia() != 6 && sesion.getInicio().getDia() != 7) {
							timeslots.add(sesion.getInicio());
						}
					}
				}
			}
		}

		timeslots.sort((t1, t2) -> {
			return t1.getDia() * 100 + t1.getHoraInicio() - t2.getDia() * 100 - t2.getHoraInicio();
		});
		return timeslots;
	}
}

// TODO: ASIGNACION DE DÍAS, HORAS LIMITE EN LOS HORARIOS (INF, SUP), OPTATIVAS,
// LABORATORIOS