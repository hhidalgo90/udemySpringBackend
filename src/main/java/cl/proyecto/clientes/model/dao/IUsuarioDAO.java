package cl.proyecto.clientes.model.dao;

import cl.proyecto.clientes.model.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {

    public Usuario findByUserName(String userName);//por convencion jpa hace la consulta automatica siempre y cuando el metodo empieza con findByAlgo, donde algo es un atributo de la clase o entidad

    @Query("select u from Usuario u where u.userName=?1")//query jpa , hace referencia a la clase no a la tabla,  por eso el Usuario
    public Usuario findByUsername2(String userName);
}
