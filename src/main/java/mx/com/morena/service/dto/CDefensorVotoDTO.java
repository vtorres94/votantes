package mx.com.morena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mx.com.morena.domain.CDefensorVoto} entity.
 */
public class CDefensorVotoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String nombreCompleto;

    @NotNull
    @Size(max = 30)
    private String nombre;

    @Size(max = 30)
    private String segundoNombre;

    @NotNull
    @Size(max = 30)
    private String apellidoPaterno;

    @Size(max = 30)
    private String segundoMaterno;

    @Max(value = 999)
    private Integer edad;

    @Size(max = 16)
    private String telefono;

    @Size(max = 30)
    private String claveElector;

    @Max(value = 999999)
    private Integer seccionElectoral;

    @Size(max = 80)
    private String calle;

    @Size(max = 30)
    private String numExt;

    @Size(max = 80)
    private String colonia;

    @Max(value = 999999)
    private Integer cp;

    @Size(max = 80)
    private String municipio;

    @Size(max = 80)
    private String estado;

    @NotNull
    @Max(value = 1)
    private Integer estatus;

    @NotNull
    @Max(value = 1)
    private Integer borrado;

    private CClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getSegundoMaterno() {
        return segundoMaterno;
    }

    public void setSegundoMaterno(String segundoMaterno) {
        this.segundoMaterno = segundoMaterno;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClaveElector() {
        return claveElector;
    }

    public void setClaveElector(String claveElector) {
        this.claveElector = claveElector;
    }

    public Integer getSeccionElectoral() {
        return seccionElectoral;
    }

    public void setSeccionElectoral(Integer seccionElectoral) {
        this.seccionElectoral = seccionElectoral;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExt() {
        return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getBorrado() {
        return borrado;
    }

    public void setBorrado(Integer borrado) {
        this.borrado = borrado;
    }

    public CClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(CClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CDefensorVotoDTO)) {
            return false;
        }

        CDefensorVotoDTO cDefensorVotoDTO = (CDefensorVotoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cDefensorVotoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CDefensorVotoDTO{" +
            "id=" + getId() +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", segundoMaterno='" + getSegundoMaterno() + "'" +
            ", edad=" + getEdad() +
            ", telefono='" + getTelefono() + "'" +
            ", claveElector='" + getClaveElector() + "'" +
            ", seccionElectoral=" + getSeccionElectoral() +
            ", calle='" + getCalle() + "'" +
            ", numExt='" + getNumExt() + "'" +
            ", colonia='" + getColonia() + "'" +
            ", cp=" + getCp() +
            ", municipio='" + getMunicipio() + "'" +
            ", estado='" + getEstado() + "'" +
            ", estatus=" + getEstatus() +
            ", borrado=" + getBorrado() +
            ", cliente=" + getCliente() +
            "}";
    }
}
