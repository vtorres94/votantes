package mx.com.morena.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mx.com.morena.domain.CAgenda} entity.
 */
public class CAgendaDTO implements Serializable {

    private Long id;

    @Size(max = 80)
    private String direccion;

    private Instant fecha;

    @Size(max = 5)
    private String hora;

    @Size(max = 80)
    private String encargado;

    @Max(value = 1)
    private Integer estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CAgendaDTO)) {
            return false;
        }

        CAgendaDTO cAgendaDTO = (CAgendaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cAgendaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CAgendaDTO{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", encargado='" + getEncargado() + "'" +
            ", estatus=" + getEstatus() +
            "}";
    }
}
