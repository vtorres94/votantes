package mx.com.morena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CVotante.
 */
@Entity
@Table(name = "c_votante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CVotante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "votantesSequenceGenerator")
    @SequenceGenerator(name = "votantesSequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "nombre_completo", length = 60, nullable = false)
    private String nombreCompleto;

    @NotNull
    @Size(max = 30)
    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;

    @Size(max = 30)
    @Column(name = "segundo_nombre", length = 30)
    private String segundoNombre;

    @NotNull
    @Size(max = 30)
    @Column(name = "apellido_paterno", length = 30, nullable = false)
    private String apellidoPaterno;

    @Size(max = 30)
    @Column(name = "segundo_materno", length = 30)
    private String segundoMaterno;

    @Max(value = 999)
    @Column(name = "edad")
    private Integer edad;

    @Size(max = 16)
    @Column(name = "telefono", length = 16)
    private String telefono;

    @Size(max = 30)
    @Column(name = "clave_elector", length = 30)
    private String claveElector;

    @Max(value = 999999)
    @Column(name = "seccion_electoral")
    private Integer seccionElectoral;

    @Size(max = 80)
    @Column(name = "calle", length = 80)
    private String calle;

    @Size(max = 30)
    @Column(name = "num_ext", length = 30)
    private String numExt;

    @Size(max = 80)
    @Column(name = "colonia", length = 80)
    private String colonia;

    @Max(value = 999999)
    @Column(name = "cp")
    private Integer cp;

    @Size(max = 80)
    @Column(name = "municipio", length = 80)
    private String municipio;

    @Size(max = 80)
    @Column(name = "estado", length = 80)
    private String estado;

    @NotNull
    @Max(value = 1)
    @Column(name = "estatus", nullable = false)
    private Integer estatus;

    @NotNull
    @Max(value = 1)
    @Column(name = "borrado", nullable = false)
    private Integer borrado;

    @ManyToOne
    private CCliente cliente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cliente" }, allowSetters = true)
    private CDefensorVoto defensorVoto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CVotante id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public CVotante nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public CVotante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public CVotante segundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
        return this;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public CVotante apellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getSegundoMaterno() {
        return this.segundoMaterno;
    }

    public CVotante segundoMaterno(String segundoMaterno) {
        this.segundoMaterno = segundoMaterno;
        return this;
    }

    public void setSegundoMaterno(String segundoMaterno) {
        this.segundoMaterno = segundoMaterno;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public CVotante edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public CVotante telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClaveElector() {
        return this.claveElector;
    }

    public CVotante claveElector(String claveElector) {
        this.claveElector = claveElector;
        return this;
    }

    public void setClaveElector(String claveElector) {
        this.claveElector = claveElector;
    }

    public Integer getSeccionElectoral() {
        return this.seccionElectoral;
    }

    public CVotante seccionElectoral(Integer seccionElectoral) {
        this.seccionElectoral = seccionElectoral;
        return this;
    }

    public void setSeccionElectoral(Integer seccionElectoral) {
        this.seccionElectoral = seccionElectoral;
    }

    public String getCalle() {
        return this.calle;
    }

    public CVotante calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExt() {
        return this.numExt;
    }

    public CVotante numExt(String numExt) {
        this.numExt = numExt;
        return this;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getColonia() {
        return this.colonia;
    }

    public CVotante colonia(String colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCp() {
        return this.cp;
    }

    public CVotante cp(Integer cp) {
        this.cp = cp;
        return this;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public CVotante municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return this.estado;
    }

    public CVotante estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEstatus() {
        return this.estatus;
    }

    public CVotante estatus(Integer estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getBorrado() {
        return this.borrado;
    }

    public CVotante borrado(Integer borrado) {
        this.borrado = borrado;
        return this;
    }

    public void setBorrado(Integer borrado) {
        this.borrado = borrado;
    }

    public CCliente getCliente() {
        return this.cliente;
    }

    public CVotante cliente(CCliente cCliente) {
        this.setCliente(cCliente);
        return this;
    }

    public void setCliente(CCliente cCliente) {
        this.cliente = cCliente;
    }

    public CDefensorVoto getDefensorVoto() {
        return this.defensorVoto;
    }

    public CVotante defensorVoto(CDefensorVoto cDefensorVoto) {
        this.setDefensorVoto(cDefensorVoto);
        return this;
    }

    public void setDefensorVoto(CDefensorVoto cDefensorVoto) {
        this.defensorVoto = cDefensorVoto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CVotante)) {
            return false;
        }
        return id != null && id.equals(((CVotante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CVotante{" +
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
            "}";
    }
}
