package cl.proyecto.clientes.service.impl;

import cl.proyecto.clientes.model.dao.IClienteDao;
import cl.proyecto.clientes.model.dao.IProductoDao;
import cl.proyecto.clientes.model.entity.Cliente;
import cl.proyecto.clientes.model.entity.Producto;
import cl.proyecto.clientes.model.entity.Region;
import cl.proyecto.clientes.service.IClienteService;
import cl.proyecto.clientes.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService , IProductoService {

    @Autowired
    private IClienteDao iClienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) iClienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return iClienteDao.findAll(pageable);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return iClienteDao.save(cliente);
    }

    @Override
    public Cliente findById(Long id) {
        return iClienteDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        iClienteDao.deleteById(id);
    }

    @Override
    public void edit(Cliente cliente) {
        iClienteDao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegiones() {
        return iClienteDao.findAllRegiones();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String termino) {
        return productoDao.findByNombreContainingIgnoreCase(termino);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAlllProductos() {
        return (List<Producto>) productoDao.findAll();
    }
}
