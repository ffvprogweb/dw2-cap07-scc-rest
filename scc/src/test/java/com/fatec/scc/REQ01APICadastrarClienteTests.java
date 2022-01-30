package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ01APICadastrarClienteTests {
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private MantemClienteRepository clienteRepository;
	Logger log = LogManager.getLogger(this.getClass());

	@Test
	void ct01_quando_cadastra_cliente_valido_retorna_clientes_cadastrado() {
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

		// Quando solicita consulta por id
//				{
//				    "cpf": "99504993052",
//				    "nome": "Jose Antonio",
//				    "email": "jose@gmail.com",
//				    "cep": "04280130"
//				}
		String urlBase = "/api/v1/clientes/";
		Cliente cliente = new Cliente("Carlos Miranda", "20/10/1961", "M", "60545111579", "04280130", "123");
		HttpEntity<Cliente> httpEntity2 = new HttpEntity<Cliente>(cliente, headers);
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity2, String.class);
		// Entao retorna os detalhes do cliente
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		Optional<Cliente> re = clienteRepository.findByCpf("60545111579");
		assertTrue(re.isPresent());
		Gson g = new Gson();
		Cliente c = g.fromJson(resposta.getBody(), Cliente.class);
		cliente.setId(c.getId());
		cliente.setDataCadastro(c.getDataCadastro());
		cliente.setEndereco(c.getEndereco());
		log.info("Resultado esperado = " + cliente.toString());
		log.info("Resultado obitdo   = " + c.toString());
		assertTrue(cliente.equals(c));
	}

}
