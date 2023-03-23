package cl.proyecto.clientes.service.impl;

import cl.proyecto.clientes.model.dao.IUsuarioDAO;
import cl.proyecto.clientes.model.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserDetailsService {// UserDetailsService interfaz que se usa de spring security para el logueo de usuarios

    private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Override
    @Transactional(readOnly = true)//es una consulta asi que debe ser readonly
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByUserName(username);
        if (usuario == null){
            logger.error("Error en el login: no existe usuario: " + username + " en el sistema.");
            throw new UsernameNotFoundException("Error en el login: no existe usuario: " + username + " en el sistema.");
        }
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(simpleGrantedAuthority -> logger.info(simpleGrantedAuthority.getAuthority()))//sirve para mostrar por consola
                .collect(Collectors.toList());//convertimos la clase Role a GrantedAuthority

        return new User(usuario.getUserName(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
}
