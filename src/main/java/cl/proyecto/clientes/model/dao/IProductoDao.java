package cl.proyecto.clientes.model.dao;

import cl.proyecto.clientes.model.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoDao extends CrudRepository<Producto, Long> {

    @Query("select p from Producto p where p.nombre like %?1%")//los % indican si busca al final de la palabra o al principio, en este caso que contenga en toda la palabra
    public List<Producto> findByNombre(String termino);

    public List<Producto> findByNombreContainingIgnoreCase(String termino);//Containing y IgnoreCase es una nomenclatura de spring jpa

    public List<Producto> findByNombreStartingWithIgnoreCase(String termino);

}
