package edu.uaeh.horarios2.service;

import java.util.List;

import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;

public interface TipoEmpleadoService {
    public List<TipoEmpleado> getTiposEmpleado();

    public void guardar(TipoEmpleado tipoEmpleado);

    public void eliminar(TipoEmpleado tipoEmpleado);

    public TipoEmpleado getTipoEmpleado(TipoEmpleado tipoEmpleado);

    public TipoEmpleado getTipoEmpleado(Long idTipoEmpleado);

    public TipoEmpleado getTipoEmpleado(Integer idTipoEmpleado);
}
