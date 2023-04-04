package cl.proyecto.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ClientesApplication implements CommandLineRunner {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ClientesApplication.class, args);
	}

	/**
	 * Ejecuta una tarea antes de iniciar la aplicacion
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		String password = "123456";

		for(int i = 0; i< 4; i++){
			String passwordEncriptada = passwordEncoder.encode(password);
			System.out.println(passwordEncriptada);
		}
	}
}
