package cl.proyecto.clientes.model.dao;

import cl.proyecto.clientes.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long> {
}
