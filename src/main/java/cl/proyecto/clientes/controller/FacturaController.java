package cl.proyecto.clientes.controller;

import cl.proyecto.clientes.model.entity.Factura;
import cl.proyecto.clientes.model.entity.Producto;
import cl.proyecto.clientes.service.IFacturaService;
import cl.proyecto.clientes.service.IProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8089", "https://angular-clientes-udemy.web.app"})
@RestController
@Slf4j
@RequestMapping("/apiCliente")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IProductoService productoService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Factura findById(@PathVariable Long id){
        return facturaService.findById(id);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        facturaService.delete(id);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/facturas/filtrar-productos/{termino}")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> filtrarProductos(@PathVariable String termino){
        return productoService.findByNombre(termino);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/facturas/obtener-productos")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAllProductos(){
        return productoService.findAlllProductos();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/facturas")
    @ResponseStatus(HttpStatus.CREATED)
    public Factura save(@RequestBody Factura factura){
        return facturaService.save(factura);

    }
}
