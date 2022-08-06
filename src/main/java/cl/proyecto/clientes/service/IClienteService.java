package cl.proyecto.clientes.service;

import cl.proyecto.clientes.model.entity.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAll();

    Cliente save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);

    void edit(Cliente cliente);
}
