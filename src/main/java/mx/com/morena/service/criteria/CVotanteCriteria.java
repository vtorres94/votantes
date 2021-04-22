package mx.com.morena.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link mx.com.morena.domain.CVotante} entity. This class is used
 * in {@link mx.com.morena.web.rest.CVotanteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /c-votantes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CVotanteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombreCompleto;

    private StringFilter nombre;

    private StringFilter segundoNombre;

    private StringFilter apellidoPaterno;

    private StringFilter segundoMaterno;

    private IntegerFilter edad;

    private StringFilter telefono;

    private StringFilter claveElector;

    private IntegerFilter seccionElectoral;

    private StringFilter calle;

    private StringFilter numExt;

    private StringFilter colonia;

    private IntegerFilter cp;

    private StringFilter municipio;

    private StringFilter estado;

    private IntegerFilter estatus;

    private IntegerFilter borrado;

    private LongFilter clienteId;

    private LongFilter defensorVotoId;

    public CVotanteCriteria() {}

    public CVotanteCriteria(CVotanteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombreCompleto = other.nombreCompleto == null ? null : other.nombreCompleto.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.segundoNombre = other.segundoNombre == null ? null : other.segundoNombre.copy();
        this.apellidoPaterno = other.apellidoPaterno == null ? null : other.apellidoPaterno.copy();
        this.segundoMaterno = other.segundoMaterno == null ? null : other.segundoMaterno.copy();
        this.edad = other.edad == null ? null : other.edad.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.claveElector = other.claveElector == null ? null : other.claveElector.copy();
        this.seccionElectoral = other.seccionElectoral == null ? null : other.seccionElectoral.copy();
        this.calle = other.calle == null ? null : other.calle.copy();
        this.numExt = other.numExt == null ? null : other.numExt.copy();
        this.colonia = other.colonia == null ? null : other.colonia.copy();
        this.cp = other.cp == null ? null : other.cp.copy();
        this.municipio = other.municipio == null ? null : other.municipio.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.borrado = other.borrado == null ? null : other.borrado.copy();
        this.clienteId = other.clienteId == null ? null : other.clienteId.copy();
        this.defensorVotoId = other.defensorVotoId == null ? null : other.defensorVotoId.copy();
    }

    @Override
    public CVotanteCriteria copy() {
        return new CVotanteCriteria(this);
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

    public StringFilter getNombreCompleto() {
        return nombreCompleto;
    }

    public StringFilter nombreCompleto() {
        if (nombreCompleto == null) {
            nombreCompleto = new StringFilter();
        }
        return nombreCompleto;
    }

    public void setNombreCompleto(StringFilter nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public StringFilter nombre() {
        if (nombre == null) {
            nombre = new StringFilter();
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getSegundoNombre() {
        return segundoNombre;
    }

    public StringFilter segundoNombre() {
        if (segundoNombre == null) {
            segundoNombre = new StringFilter();
        }
        return segundoNombre;
    }

    public void setSegundoNombre(StringFilter segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public StringFilter getApellidoPaterno() {
        return apellidoPaterno;
    }

    public StringFilter apellidoPaterno() {
        if (apellidoPaterno == null) {
            apellidoPaterno = new StringFilter();
        }
        return apellidoPaterno;
    }

    public void setApellidoPaterno(StringFilter apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public StringFilter getSegundoMaterno() {
        return segundoMaterno;
    }

    public StringFilter segundoMaterno() {
        if (segundoMaterno == null) {
            segundoMaterno = new StringFilter();
        }
        return segundoMaterno;
    }

    public void setSegundoMaterno(StringFilter segundoMaterno) {
        this.segundoMaterno = segundoMaterno;
    }

    public IntegerFilter getEdad() {
        return edad;
    }

    public IntegerFilter edad() {
        if (edad == null) {
            edad = new IntegerFilter();
        }
        return edad;
    }

    public void setEdad(IntegerFilter edad) {
        this.edad = edad;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public StringFilter telefono() {
        if (telefono == null) {
            telefono = new StringFilter();
        }
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getClaveElector() {
        return claveElector;
    }

    public StringFilter claveElector() {
        if (claveElector == null) {
            claveElector = new StringFilter();
        }
        return claveElector;
    }

    public void setClaveElector(StringFilter claveElector) {
        this.claveElector = claveElector;
    }

    public IntegerFilter getSeccionElectoral() {
        return seccionElectoral;
    }

    public IntegerFilter seccionElectoral() {
        if (seccionElectoral == null) {
            seccionElectoral = new IntegerFilter();
        }
        return seccionElectoral;
    }

    public void setSeccionElectoral(IntegerFilter seccionElectoral) {
        this.seccionElectoral = seccionElectoral;
    }

    public StringFilter getCalle() {
        return calle;
    }

    public StringFilter calle() {
        if (calle == null) {
            calle = new StringFilter();
        }
        return calle;
    }

    public void setCalle(StringFilter calle) {
        this.calle = calle;
    }

    public StringFilter getNumExt() {
        return numExt;
    }

    public StringFilter numExt() {
        if (numExt == null) {
            numExt = new StringFilter();
        }
        return numExt;
    }

    public void setNumExt(StringFilter numExt) {
        this.numExt = numExt;
    }

    public StringFilter getColonia() {
        return colonia;
    }

    public StringFilter colonia() {
        if (colonia == null) {
            colonia = new StringFilter();
        }
        return colonia;
    }

    public void setColonia(StringFilter colonia) {
        this.colonia = colonia;
    }

    public IntegerFilter getCp() {
        return cp;
    }

    public IntegerFilter cp() {
        if (cp == null) {
            cp = new IntegerFilter();
        }
        return cp;
    }

    public void setCp(IntegerFilter cp) {
        this.cp = cp;
    }

    public StringFilter getMunicipio() {
        return municipio;
    }

    public StringFilter municipio() {
        if (municipio == null) {
            municipio = new StringFilter();
        }
        return municipio;
    }

    public void setMunicipio(StringFilter municipio) {
        this.municipio = municipio;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public StringFilter estado() {
        if (estado == null) {
            estado = new StringFilter();
        }
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
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

    public LongFilter getClienteId() {
        return clienteId;
    }

    public LongFilter clienteId() {
        if (clienteId == null) {
            clienteId = new LongFilter();
        }
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
    }

    public LongFilter getDefensorVotoId() {
        return defensorVotoId;
    }

    public LongFilter defensorVotoId() {
        if (defensorVotoId == null) {
            defensorVotoId = new LongFilter();
        }
        return defensorVotoId;
    }

    public void setDefensorVotoId(LongFilter defensorVotoId) {
        this.defensorVotoId = defensorVotoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CVotanteCriteria that = (CVotanteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombreCompleto, that.nombreCompleto) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(segundoNombre, that.segundoNombre) &&
            Objects.equals(apellidoPaterno, that.apellidoPaterno) &&
            Objects.equals(segundoMaterno, that.segundoMaterno) &&
            Objects.equals(edad, that.edad) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(claveElector, that.claveElector) &&
            Objects.equals(seccionElectoral, that.seccionElectoral) &&
            Objects.equals(calle, that.calle) &&
            Objects.equals(numExt, that.numExt) &&
            Objects.equals(colonia, that.colonia) &&
            Objects.equals(cp, that.cp) &&
            Objects.equals(municipio, that.municipio) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(borrado, that.borrado) &&
            Objects.equals(clienteId, that.clienteId) &&
            Objects.equals(defensorVotoId, that.defensorVotoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nombreCompleto,
            nombre,
            segundoNombre,
            apellidoPaterno,
            segundoMaterno,
            edad,
            telefono,
            claveElector,
            seccionElectoral,
            calle,
            numExt,
            colonia,
            cp,
            municipio,
            estado,
            estatus,
            borrado,
            clienteId,
            defensorVotoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CVotanteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nombreCompleto != null ? "nombreCompleto=" + nombreCompleto + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (segundoNombre != null ? "segundoNombre=" + segundoNombre + ", " : "") +
            (apellidoPaterno != null ? "apellidoPaterno=" + apellidoPaterno + ", " : "") +
            (segundoMaterno != null ? "segundoMaterno=" + segundoMaterno + ", " : "") +
            (edad != null ? "edad=" + edad + ", " : "") +
            (telefono != null ? "telefono=" + telefono + ", " : "") +
            (claveElector != null ? "claveElector=" + claveElector + ", " : "") +
            (seccionElectoral != null ? "seccionElectoral=" + seccionElectoral + ", " : "") +
            (calle != null ? "calle=" + calle + ", " : "") +
            (numExt != null ? "numExt=" + numExt + ", " : "") +
            (colonia != null ? "colonia=" + colonia + ", " : "") +
            (cp != null ? "cp=" + cp + ", " : "") +
            (municipio != null ? "municipio=" + municipio + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (estatus != null ? "estatus=" + estatus + ", " : "") +
            (borrado != null ? "borrado=" + borrado + ", " : "") +
            (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
            (defensorVotoId != null ? "defensorVotoId=" + defensorVotoId + ", " : "") +
            "}";
    }
}
