package cl.proyecto.clientes.service.impl;

import cl.proyecto.clientes.model.dao.IFacturaDao;
import cl.proyecto.clientes.model.entity.Factura;
import cl.proyecto.clientes.service.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private IFacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public Factura findById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaDao.save(factura);
    }

    @Override
    public void delete(Long id) {
        facturaDao.deleteById(id);
    }
}
