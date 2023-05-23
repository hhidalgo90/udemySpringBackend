package cl.proyecto.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)//formato de la fecha
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)//solo ejecuta las llamadas a las relaciones cuando se le llama, evitando uso innecesario de memoria
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties(value = {"facturas", "hibernateLazyInitializer", "handler"}, allowSetters = true)//allowSetters permite editar al cliente evitando error de recursividad
    private Cliente cliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id") //este campo se crea automaticamente en ItemFactura, ya que no es relacion bidireccional
    private List<ItemFactura> items;

    public Factura() {
        this.items = new ArrayList<>();
    }

    /**
     * Este metodo se ejecuta justo antes de persistir en bd, es parte del ciclo de vida de las entidades @Entity
     */
    @PrePersist
    public void preGuardar(){
        this.createAt = new Date();
    }

    public Double getTotal(){
        Double total = 0.00;
        for (ItemFactura itemFactura : items){
            total += itemFactura.getImporte();
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }
}
