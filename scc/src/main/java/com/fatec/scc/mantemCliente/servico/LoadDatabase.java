package com.fatec.scc.mantemCliente.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemCliente;
import com.fatec.scc.seguranca.model.ApplicationUser;
import com.fatec.scc.seguranca.ports.ApplicationUserRepository;

@Configuration
class LoadDatabase {

	Logger log = LogManager.getLogger(this.getClass());
	@Autowired
	private ApplicationUserRepository applicationUserRepository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Bean
	CommandLineRunner initDatabase(MantemCliente mantemCliente) {

		return args -> {
			Cliente cliente1 = new Cliente("Miguel Soares", "10/02/1960", "M", "99504993052", "04280130", "2983");
			log.info("Preloading - cliente => " + mantemCliente.save(cliente1));
			Cliente cliente2 = new Cliente("Marcos Silva", "04/10/1974", "M", "43011831084", "08545160", "2983");
			log.info("Preloading " + mantemCliente.save(cliente2));
			
			ApplicationUser user = new ApplicationUser();
			user.setUsername("jose");
			user.setPassword("123");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        log.info("Preloading - usuario => " + applicationUserRepository.save(user).getUsername());
			
		};
	}
}