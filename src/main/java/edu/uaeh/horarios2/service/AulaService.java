package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.Aula;

public interface AulaService {
    public List<Aula> getAulas();

    public void guardar(Aula aula);

    public void eliminar(Aula aula);

    public Aula getAula(Aula aula);

    public Aula getAula(Long idAula);

    public Aula getAula(Integer idAula);

    List<Aula> getAulasNormales();

    List<Aula> getLaboratorios();

    List<Aula> getAulasComputo();

    List<Aula> getAulasEspeciales();
}
