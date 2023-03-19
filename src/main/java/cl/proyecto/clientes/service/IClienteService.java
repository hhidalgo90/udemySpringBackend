package cl.proyecto.clientes.service;

import cl.proyecto.clientes.model.entity.Cliente;
import cl.proyecto.clientes.model.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    Cliente save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);

    void edit(Cliente cliente);

    public List<Region> findAllRegiones();
}
