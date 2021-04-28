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
 * Criteria class for the {@link mx.com.morena.domain.CAgenda} entity. This class is used
 * in {@link mx.com.morena.web.rest.CAgendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-agenda?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CAgendaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter direccion;

    private InstantFilter fecha;

    private StringFilter hora;

    private StringFilter encargado;

    private IntegerFilter estatus;

    public CAgendaCriteria() {}

    public CAgendaCriteria(CAgendaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.hora = other.hora == null ? null : other.hora.copy();
        this.encargado = other.encargado == null ? null : other.encargado.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
    }

    @Override
    public CAgendaCriteria copy() {
        return new CAgendaCriteria(this);
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

    public StringFilter getDireccion() {
        return direccion;
    }

    public StringFilter direccion() {
        if (direccion == null) {
            direccion = new StringFilter();
        }
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public InstantFilter fecha() {
        if (fecha == null) {
            fecha = new InstantFilter();
        }
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getHora() {
        return hora;
    }

    public StringFilter hora() {
        if (hora == null) {
            hora = new StringFilter();
        }
        return hora;
    }

    public void setHora(StringFilter hora) {
        this.hora = hora;
    }

    public StringFilter getEncargado() {
        return encargado;
    }

    public StringFilter encargado() {
        if (encargado == null) {
            encargado = new StringFilter();
        }
        return encargado;
    }

    public void setEncargado(StringFilter encargado) {
        this.encargado = encargado;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CAgendaCriteria that = (CAgendaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(hora, that.hora) &&
            Objects.equals(encargado, that.encargado) &&
            Objects.equals(estatus, that.estatus)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, direccion, fecha, hora, encargado, estatus);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CAgendaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (direccion != null ? "direccion=" + direccion + ", " : "") +
            (fecha != null ? "fecha=" + fecha + ", " : "") +
            (hora != null ? "hora=" + hora + ", " : "") +
            (encargado != null ? "encargado=" + encargado + ", " : "") +
            (estatus != null ? "estatus=" + estatus + ", " : "") +
            "}";
    }
}
