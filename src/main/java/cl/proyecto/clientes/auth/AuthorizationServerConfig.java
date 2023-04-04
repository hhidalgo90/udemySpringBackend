package cl.proyecto.clientes.auth;

import cl.proyecto.clientes.util.GeneradorLlaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;


@Configuration
@EnableAuthorizationServer //implementacion autorization server deprecada
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //obtenemos los beans declararos en SpringSecurityConfig
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAdicionalToken InfoAdicionalToken;

    /**
     * Permisos de nuestros endpoints
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")//acceso para todos para la ruta donde se inicia sesion, ruta publica que da spring security
                .checkTokenAccess("isAuthenticated()");//dar permiso para checkear los tokens enviados
    }

    /**
     * Registramos aplicacion angular y le damos el secreto y los permisos.
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("angularapp")//cliente id, esto tiene que venir en la cabecera de la peticion
                .secret(passwordEncoder.encode("123456")) //decodificamos el secreto
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)//tiempo que va a durar el token
                .refreshTokenValiditySeconds(3600);
    }

    @Override //autenticar y validar el token
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();//aqui registramos la info adicional del token
        enhancerChain.setTokenEnhancers(Arrays.asList(InfoAdicionalToken, convertidorToken()));

        //se registra el authenticationManager
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(convertidorToken())
                .tokenEnhancer(enhancerChain);//registramos la cadena TokenEnhancerChain

    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(convertidorToken());
    }



    @Bean
    public JwtAccessTokenConverter convertidorToken() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        jwtAccessTokenConverter.setSigningKey("1234567");//con esta llave se crea el token, llave secreta del tipo MAC, llave que firma
        //jwtAccessTokenConverter.setVerifierKey(generadorLlaves.getPublicKey());//llave que verifica

        return jwtAccessTokenConverter;
    }
}
