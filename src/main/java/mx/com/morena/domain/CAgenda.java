package mx.com.morena.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CAgenda.
 */
@Entity
@Table(name = "c_agenda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CAgenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendaSequenceGenerator")
    @SequenceGenerator(name = "agendaSequenceGenerator")
    private Long id;

    @Size(max = 80)
    @Column(name = "direccion", length = 80)
    private String direccion;

    @Column(name = "fecha")
    private Instant fecha;

    @Size(max = 5)
    @Column(name = "hora", length = 5)
    private String hora;

    @Size(max = 80)
    @Column(name = "encargado", length = 80)
    private String encargado;

    @Max(value = 1)
    @Column(name = "estatus")
    private Integer estatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CAgenda id(Long id) {
        this.id = id;
        return this;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public CAgenda direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public CAgenda fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return this.hora;
    }

    public CAgenda hora(String hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEncargado() {
        return this.encargado;
    }

    public CAgenda encargado(String encargado) {
        this.encargado = encargado;
        return this;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Integer getEstatus() {
        return this.estatus;
    }

    public CAgenda estatus(Integer estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CAgenda)) {
            return false;
        }
        return id != null && id.equals(((CAgenda) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CAgenda{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", encargado='" + getEncargado() + "'" +
            ", estatus=" + getEstatus() +
            "}";
    }
}
