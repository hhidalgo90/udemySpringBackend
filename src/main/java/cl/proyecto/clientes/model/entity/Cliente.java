package cl.proyecto.clientes.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

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

    /**
     * Este metodo se ejecuta justo antes de persistir en bd, es parte del ciclo de vida de las entidades @Entity
     */
   /* @PrePersist
    public void prePersist(){
        createAt = new Date();
    }*/

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
}
