package cl.proyecto.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = " no puede estar vacio")
    @Size(min = 4, max = 12, message = " El largo debe ser entre 4 y 12 caracteres")
    @Column(nullable = false)// no puede ser null
    private String nombre;

    @NotEmpty(message = " no puede estar vacio")
    private String apellido;

    @NotEmpty(message = " no puede estar vacio")
    @Email(message = " debe tener un formato valido")
    @Column(nullable = false, unique = false)// no puede ser null y debe ser unico
    private String email;

    @NotNull(message = " no puede estar vacio")
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String foto;

    @NotNull(message = "Region no puede estar vacio")
    @ManyToOne(fetch = FetchType.LAZY) //muchos clientes tienen una region, Lazy: se cargan datos cuando se llama al atributo
    @JoinColumn(name = "region_id") // le indicamos el nombre del campo que queremos en bd
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//ignora propiedades propias de hibernate que se trae por defecto al usar FetchType.LAZY
    private Region region;

    /**
     * mappedBy: atributo cliente en clase Factura
     * CascadeType.ALL: al eliminar un cliente elimina a todos sus elementos hijos(facturas) evitando problemas de fk
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"cliente", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<Factura> facturas;

    public Cliente() {
        this.facturas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
}
