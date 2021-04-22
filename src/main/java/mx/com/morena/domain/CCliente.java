package mx.com.morena.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CCliente.
 */
@Entity
@Table(name = "c_cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientesSequenceGenerator")
    @SequenceGenerator(name = "clientesSequenceGenerator")
    private Long id;

    @Size(max = 10)
    @Column(name = "clave_cliente", length = 10)
    private String claveCliente;

    @Size(max = 60)
    @Column(name = "cliente", length = 60)
    private String cliente;

    @Max(value = 9999)
    @Column(name = "anio_electoral")
    private Integer anioElectoral;

    @NotNull
    @Max(value = 999999999999L)
    @Column(name = "id_usuario_creacion", nullable = false)
    private Long idUsuarioCreacion;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @Max(value = 999999999999L)
    @Column(name = "id_usuario_actualizacion")
    private Long idUsuarioActualizacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    @Size(max = 300)
    @Column(name = "notas", length = 300)
    private String notas;

    @NotNull
    @Max(value = 1)
    @Column(name = "estatus", nullable = false)
    private Integer estatus;

    @NotNull
    @Max(value = 1)
    @Column(name = "borrado", nullable = false)
    private Integer borrado;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CCliente id(Long id) {
        this.id = id;
        return this;
    }

    public String getClaveCliente() {
        return this.claveCliente;
    }

    public CCliente claveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
        return this;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getCliente() {
        return this.cliente;
    }

    public CCliente cliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getAnioElectoral() {
        return this.anioElectoral;
    }

    public CCliente anioElectoral(Integer anioElectoral) {
        this.anioElectoral = anioElectoral;
        return this;
    }

    public void setAnioElectoral(Integer anioElectoral) {
        this.anioElectoral = anioElectoral;
    }

    public Long getIdUsuarioCreacion() {
        return this.idUsuarioCreacion;
    }

    public CCliente idUsuarioCreacion(Long idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
        return this;
    }

    public void setIdUsuarioCreacion(Long idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public Instant getFechaCreacion() {
        return this.fechaCreacion;
    }

    public CCliente fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdUsuarioActualizacion() {
        return this.idUsuarioActualizacion;
    }

    public CCliente idUsuarioActualizacion(Long idUsuarioActualizacion) {
        this.idUsuarioActualizacion = idUsuarioActualizacion;
        return this;
    }

    public void setIdUsuarioActualizacion(Long idUsuarioActualizacion) {
        this.idUsuarioActualizacion = idUsuarioActualizacion;
    }

    public Instant getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public CCliente fechaActualizacion(Instant fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
        return this;
    }

    public void setFechaActualizacion(Instant fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getNotas() {
        return this.notas;
    }

    public CCliente notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getEstatus() {
        return this.estatus;
    }

    public CCliente estatus(Integer estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getBorrado() {
        return this.borrado;
    }

    public CCliente borrado(Integer borrado) {
        this.borrado = borrado;
        return this;
    }

    public void setBorrado(Integer borrado) {
        this.borrado = borrado;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CCliente)) {
            return false;
        }
        return id != null && id.equals(((CCliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CCliente{" +
            "id=" + getId() +
            ", claveCliente='" + getClaveCliente() + "'" +
            ", cliente='" + getCliente() + "'" +
            ", anioElectoral=" + getAnioElectoral() +
            ", idUsuarioCreacion=" + getIdUsuarioCreacion() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", idUsuarioActualizacion=" + getIdUsuarioActualizacion() +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            ", notas='" + getNotas() + "'" +
            ", estatus=" + getEstatus() +
            ", borrado=" + getBorrado() +
            "}";
    }
}
