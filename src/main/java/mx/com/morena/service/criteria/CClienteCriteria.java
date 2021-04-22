package mx.com.morena.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link mx.com.morena.domain.CCliente} entity. This class is used
 * in {@link mx.com.morena.web.rest.CClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter claveCliente;

    private StringFilter cliente;

    private IntegerFilter anioElectoral;

    private LongFilter idUsuarioCreacion;

    private InstantFilter fechaCreacion;

    private LongFilter idUsuarioActualizacion;

    private InstantFilter fechaActualizacion;

    private StringFilter notas;

    private IntegerFilter estatus;

    private IntegerFilter borrado;

    public CClienteCriteria() {}

    public CClienteCriteria(CClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.claveCliente = other.claveCliente == null ? null : other.claveCliente.copy();
        this.cliente = other.cliente == null ? null : other.cliente.copy();
        this.anioElectoral = other.anioElectoral == null ? null : other.anioElectoral.copy();
        this.idUsuarioCreacion = other.idUsuarioCreacion == null ? null : other.idUsuarioCreacion.copy();
        this.fechaCreacion = other.fechaCreacion == null ? null : other.fechaCreacion.copy();
        this.idUsuarioActualizacion = other.idUsuarioActualizacion == null ? null : other.idUsuarioActualizacion.copy();
        this.fechaActualizacion = other.fechaActualizacion == null ? null : other.fechaActualizacion.copy();
        this.notas = other.notas == null ? null : other.notas.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.borrado = other.borrado == null ? null : other.borrado.copy();
    }

    @Override
    public CClienteCriteria copy() {
        return new CClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClaveCliente() {
        return claveCliente;
    }

    public StringFilter claveCliente() {
        if (claveCliente == null) {
            claveCliente = new StringFilter();
        }
        return claveCliente;
    }

    public void setClaveCliente(StringFilter claveCliente) {
        this.claveCliente = claveCliente;
    }

    public StringFilter getCliente() {
        return cliente;
    }

    public StringFilter cliente() {
        if (cliente == null) {
            cliente = new StringFilter();
        }
        return cliente;
    }

    public void setCliente(StringFilter cliente) {
        this.cliente = cliente;
    }

    public IntegerFilter getAnioElectoral() {
        return anioElectoral;
    }

    public IntegerFilter anioElectoral() {
        if (anioElectoral == null) {
            anioElectoral = new IntegerFilter();
        }
        return anioElectoral;
    }

    public void setAnioElectoral(IntegerFilter anioElectoral) {
        this.anioElectoral = anioElectoral;
    }

    public LongFilter getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public LongFilter idUsuarioCreacion() {
        if (idUsuarioCreacion == null) {
            idUsuarioCreacion = new LongFilter();
        }
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(LongFilter idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public InstantFilter getFechaCreacion() {
        return fechaCreacion;
    }

    public InstantFilter fechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = new InstantFilter();
        }
        return fechaCreacion;
    }

    public void setFechaCreacion(InstantFilter fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LongFilter getIdUsuarioActualizacion() {
        return idUsuarioActualizacion;
    }

    public LongFilter idUsuarioActualizacion() {
        if (idUsuarioActualizacion == null) {
            idUsuarioActualizacion = new LongFilter();
        }
        return idUsuarioActualizacion;
    }

    public void setIdUsuarioActualizacion(LongFilter idUsuarioActualizacion) {
        this.idUsuarioActualizacion = idUsuarioActualizacion;
    }

    public InstantFilter getFechaActualizacion() {
        return fechaActualizacion;
    }

    public InstantFilter fechaActualizacion() {
        if (fechaActualizacion == null) {
            fechaActualizacion = new InstantFilter();
        }
        return fechaActualizacion;
    }

    public void setFechaActualizacion(InstantFilter fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public StringFilter getNotas() {
        return notas;
    }

    public StringFilter notas() {
        if (notas == null) {
            notas = new StringFilter();
        }
        return notas;
    }

    public void setNotas(StringFilter notas) {
        this.notas = notas;
    }

    public IntegerFilter getEstatus() {
        return estatus;
    }

    public IntegerFilter estatus() {
        if (estatus == null) {
            estatus = new IntegerFilter();
        }
        return estatus;
    }

    public void setEstatus(IntegerFilter estatus) {
        this.estatus = estatus;
    }

    public IntegerFilter getBorrado() {
        return borrado;
    }

    public IntegerFilter borrado() {
        if (borrado == null) {
            borrado = new IntegerFilter();
        }
        return borrado;
    }

    public void setBorrado(IntegerFilter borrado) {
        this.borrado = borrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CClienteCriteria that = (CClienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(claveCliente, that.claveCliente) &&
            Objects.equals(cliente, that.cliente) &&
            Objects.equals(anioElectoral, that.anioElectoral) &&
            Objects.equals(idUsuarioCreacion, that.idUsuarioCreacion) &&
            Objects.equals(fechaCreacion, that.fechaCreacion) &&
            Objects.equals(idUsuarioActualizacion, that.idUsuarioActualizacion) &&
            Objects.equals(fechaActualizacion, that.fechaActualizacion) &&
            Objects.equals(notas, that.notas) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(borrado, that.borrado)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            claveCliente,
            cliente,
            anioElectoral,
            idUsuarioCreacion,
            fechaCreacion,
            idUsuarioActualizacion,
            fechaActualizacion,
            notas,
            estatus,
            borrado
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CClienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (claveCliente != null ? "claveCliente=" + claveCliente + ", " : "") +
            (cliente != null ? "cliente=" + cliente + ", " : "") +
            (anioElectoral != null ? "anioElectoral=" + anioElectoral + ", " : "") +
            (idUsuarioCreacion != null ? "idUsuarioCreacion=" + idUsuarioCreacion + ", " : "") +
            (fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : "") +
            (idUsuarioActualizacion != null ? "idUsuarioActualizacion=" + idUsuarioActualizacion + ", " : "") +
            (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "") +
            (notas != null ? "notas=" + notas + ", " : "") +
            (estatus != null ? "estatus=" + estatus + ", " : "") +
            (borrado != null ? "borrado=" + borrado + ", " : "") +
            "}";
    }
}
