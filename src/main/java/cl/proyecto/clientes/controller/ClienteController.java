package cl.proyecto.clientes.controller;

import cl.proyecto.clientes.model.entity.Cliente;
import cl.proyecto.clientes.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:65163"})
@RestController
@RequestMapping("/apiCliente")
public class ClienteController {

    @Autowired
    private IClienteService iClienteService;
    private static final Integer SIZE_PAGES = 4;

    @GetMapping("/clientes")
    public List<Cliente> findAll(){
        return iClienteService.findAll();
    }

    @GetMapping("/clientes/page/{nroPagina}") //Implementacion para usar paginacion desde el backend
    public Page<Cliente> findAll(@PathVariable Integer nroPagina){
        return iClienteService.findAll(PageRequest.of(nroPagina, SIZE_PAGES));
    }

    //ResponseEntity<?> permite enviar mensaje de error desde el servidor
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Map<String, Object> respuesta = new HashMap<>();
        Cliente retorno = null;
        try {
            retorno = iClienteService.findById(id);
        } catch (DataAccessException e){
            respuesta.put("mensaje" , "Error al consultar cliente: ".concat(e.getMessage()));
            respuesta.put("error" , "Descripcion error: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(retorno == null){

            respuesta.put("mensaje" , "El cliente ID: ".concat(id.toString()).concat(" no existe!!"));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Cliente>(retorno, HttpStatus.OK);
        }
    }

    @PostMapping("/clientes")
    public  ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        //con la etiqueta @Valid se intercepta el objeto para validarlo, se configuro previamente en el objeto Cliente
        // Se debe inyectar objeto BindingResult para ver si tiene errores de validacion
        Map<String, Object> respuesta = new HashMap<>();
        Cliente nuevoCliente = null;
        if(result.hasErrors()){
            List errores = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' ," + err.getDefaultMessage())
                    .collect(Collectors.toList());

            respuesta.put("errores" , errores);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try {
            nuevoCliente = iClienteService.save(cliente);
        }catch (DataAccessException e){
            respuesta.put("mensaje" , "Error al registrar cliente: ".concat(e.getMessage()));
            respuesta.put("error" , "Descripcion error: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje" , "Cliente creado con exito");
        respuesta.put("cliente" , nuevoCliente);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);  //Responde 201, objeto creado
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, @PathVariable Long id, BindingResult result){

        Map<String, Object> respuesta = new HashMap<>();

        if(result.hasErrors()){
            List errores = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            respuesta.put("errores" , errores);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        Cliente clienteActualizado = null;
        Cliente clienteActual = iClienteService.findById(id);

        if(clienteActual == null){
            respuesta.put("mensaje" , "No se pudo actualizar, el cliente ID: ".concat(id.toString()).concat(" no existe!!"));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }
        try {
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());

        clienteActualizado = iClienteService.save(clienteActual);

        }catch (DataAccessException e){
            respuesta.put("mensaje" , "Error al actualizar cliente: ".concat(e.getMessage()));
            respuesta.put("error" , "Descripcion error: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje" , "Cliente actualizado con exito");
        respuesta.put("cliente" , clienteActualizado);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);  //Responde 201, objeto creado
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Responde 204, sin contenido
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> respuesta = new HashMap<>();
        try {
            iClienteService.delete(id);
        }catch (DataAccessException e){
            respuesta.put("mensaje" , "Error al eliminar cliente: ".concat(e.getMessage()));
            respuesta.put("error" , "Descripcion error: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje" , "Cliente eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    }
}
