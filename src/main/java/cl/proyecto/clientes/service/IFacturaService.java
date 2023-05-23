package cl.proyecto.clientes.service;

import cl.proyecto.clientes.model.entity.Factura;

public interface IFacturaService {

    Factura findById(Long id);

    Factura save(Factura factura);

    void delete(Long id);
}
