package cl.proyecto.clientes.model.dao;

import cl.proyecto.clientes.model.entity.Cliente;
import cl.proyecto.clientes.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//JpaRepository cuando se usa paginacion
public interface IClienteDao extends JpaRepository<Cliente, Long> {

    @Query("from Region")//jpa query, trabajamos con objetos no con tablas
    public List<Region> findAllRegiones();
}
