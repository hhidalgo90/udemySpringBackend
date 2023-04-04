package cl.proyecto.clientes.service;

import cl.proyecto.clientes.model.entity.Usuario;

public interface IUsuarioService {

    public Usuario findByUsername(String username);
}
