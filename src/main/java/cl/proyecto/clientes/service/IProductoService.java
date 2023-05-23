package cl.proyecto.clientes.service;

import cl.proyecto.clientes.model.entity.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> findByNombre(String termino);

    public List<Producto> findAlllProductos();
}
