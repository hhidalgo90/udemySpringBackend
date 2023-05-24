package cl.proyecto.clientes.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/*
 * Se encarga de dar acceso a los recursos de la aplicacion siempre y cuando el token sea valido
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/apiCliente/clientes", "/apiCliente/clientes/page/**", "/apiCliente/uploads/img/**", "/images/**").permitAll().//decimos que a esta ruta todos los usuarios autenticados la pueden usar
               /* antMatchers(HttpMethod.GET, "/apiCliente/clientes/{id}").hasAnyRole("USER", "ADMIN").
                antMatchers(HttpMethod.POST, "/apiCliente/clientes/upload").hasAnyRole("USER", "ADMIN").
                antMatchers(HttpMethod.POST, "/apiCliente/clientes").hasRole("ADMIN").
                antMatchers("/apiCliente/clientes/**").hasRole("ADMIN"). */ //CUALQUIER ENDPOINT QUE TENGA EL ID (PUT,DELETE) SE NECESITARA ROL ADMIN, no necesario metodo http
                anyRequest().authenticated().
                and().cors().configurationSource(corsConfigurationSource());//agregamos configuracion cors
    }


    /**
     * Configuracion CORS, permite compartir recursos con otro dominio (front end)
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8089", "https://angular-clientes-udemy.web.app"));//dominio app angular
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT","DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();//registramos la configuracion
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Registramos el filtro de cors y le damos la prioridad mas alta para que spring security lo tome de los primeros filtros al recibir una peticion
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        corsFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsFilterFilterRegistrationBean;
    }
}
