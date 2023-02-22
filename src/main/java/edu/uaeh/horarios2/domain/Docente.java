package edu.uaeh.horarios2.domain;

import java.io.Serializable;
import java.util.List;

import edu.uaeh.horarios2.domain.catalogos.Disponibilidad;
import edu.uaeh.horarios2.domain.catalogos.TipoEmpleado;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocente;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellidoPaterno;
    private String apellidoMaterno;
    @ManyToOne
    @JoinColumn(name = "tipo_empleado")
    private TipoEmpleado tipoEmpleado;
    @NotNull
    @Digits(integer = 6, fraction = 0)
    private Integer numeroEmpleado;
    @NotNull
    @Digits(integer = 2, fraction = 0)
    private Integer horasPermitidas = 40;
    private Integer estatus = 1;

    @OneToMany(mappedBy = "docente")
    private List<Disponibilidad> disponibilidad;
    
    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    public String getNombreCompletoPorApellidos() {
        return apellidoPaterno + " " + apellidoMaterno + ", " + nombre;
    }

    public Boolean esTiempoCompleto() {
        return tipoEmpleado.getIdTipoEmpleado() == 2 || this.getNumeroEmpleado().equals(4738);
    }
}
