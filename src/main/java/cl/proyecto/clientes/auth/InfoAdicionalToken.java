package cl.proyecto.clientes.auth;

import cl.proyecto.clientes.model.entity.Usuario;
import cl.proyecto.clientes.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Agregamos mas info del usuario al token
 */
@Component
public class InfoAdicionalToken implements TokenEnhancer {

    @Autowired
    private IUsuarioService iUsuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Usuario usuario = iUsuarioService.findByUsername(oAuth2Authentication.getName());

        Map<String, Object> infoExtra = new HashMap<>();
        infoExtra.put("info_adicional", "Hola que tal: ".concat(oAuth2Authentication.getName()));
        infoExtra.put("nombre", usuario.getNombre());
        infoExtra.put("apellido", usuario.getApellido());
        infoExtra.put("email", usuario.getEmail());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(infoExtra);//usamos clase que implementa la interfaz para setear el hash infoextra
        return oAuth2AccessToken;
    }
}
