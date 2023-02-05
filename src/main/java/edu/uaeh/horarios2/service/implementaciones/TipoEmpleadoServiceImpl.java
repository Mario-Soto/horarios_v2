package edu.uaeh.horarios2.service.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uaeh.horarios2.db.TipoEmpleadoDAO;
import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;
import edu.uaeh.horarios2.service.TipoEmpleadoService;

@Service
public class TipoEmpleadoServiceImpl implements TipoEmpleadoService{

    @Autowired
    private TipoEmpleadoDAO tipoEmpleadoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<TipoEmpleado> getTiposEmpleado() {
        return (List<TipoEmpleado>) tipoEmpleadoDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(TipoEmpleado tipoEmpleado) {
        tipoEmpleadoDAO.save(tipoEmpleado);
    }

    @Override
    @Transactional
    public void eliminar(TipoEmpleado tipoEmpleado) {
        tipoEmpleadoDAO.delete(tipoEmpleado);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEmpleado getTipoEmpleado(TipoEmpleado tipoEmpleado) {
        return tipoEmpleadoDAO.findById(tipoEmpleado.getIdTipoEmpleado()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEmpleado getTipoEmpleado(Long idTipoEmpleado) {
        return tipoEmpleadoDAO.findById(idTipoEmpleado).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEmpleado getTipoEmpleado(Integer idTipoEmpleado) {
        return tipoEmpleadoDAO.findById(idTipoEmpleado.longValue()).orElse(null);
    }
    
}
