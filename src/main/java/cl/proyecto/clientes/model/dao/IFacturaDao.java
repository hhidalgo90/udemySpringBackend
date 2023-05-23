package cl.proyecto.clientes.model.dao;

import cl.proyecto.clientes.model.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
}
