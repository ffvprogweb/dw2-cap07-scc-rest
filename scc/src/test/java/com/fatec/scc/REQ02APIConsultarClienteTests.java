package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemClienteRepository;
import com.fatec.scc.seguranca.model.ApplicationUser;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ02APIConsultarClienteTests {
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private MantemClienteRepository clienteRepository;
	Logger log = LogManager.getLogger(this.getClass());

	@Test
	void test() {
		// Dado que o cliente nao esta cadastrado e o login esta cadastrado
		// configura o header com username e senha
		ApplicationUser user = new ApplicationUser();
		user.setUsername("jose");
		user.setPassword("123");
		HttpEntity<ApplicationUser> httpEntity1 = new HttpEntity<>(user);

		// tenta autenticar o usuario para obter o token
		ResponseEntity<String> resposta1 = testRestTemplate.exchange("/login", HttpMethod.POST, httpEntity1,
				String.class);
		assertEquals(HttpStatus.OK, resposta1.getStatusCode());
		// armazena o token no header
		HttpHeaders headers = resposta1.getHeaders();
		
		String urlBase = "/api/v1/clientes/";
		
		HttpEntity<Cliente> httpEntity2 = new HttpEntity<Cliente>(headers);
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.GET, httpEntity2, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(0,resposta.getBody().getClass());
	}

}
