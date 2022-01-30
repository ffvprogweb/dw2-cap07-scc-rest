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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemClienteRepository;
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
		String urlBase = "/api/v1/clientes/";
		Cliente cliente = new Cliente("Carlos Miranda","20/10/1961","M", "60545111579",  "04280130", "123");
		HttpEntity<Cliente> httpEntity = new HttpEntity<Cliente>(cliente);
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity, String.class);
		//Entao retorna os detalhes do cliente
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		Optional<Cliente>  re = clienteRepository.findByCpf("60545111579");
		assertTrue(re.isPresent());
		Gson g = new Gson();  
		Cliente c = g.fromJson(resposta.getBody(), Cliente.class) ; 
		cliente.setId(c.getId());
		cliente.setDataCadastro(c.getDataCadastro());
		cliente.setEndereco(c.getEndereco());
		log.info("Resultado esperado = " + cliente.toString());
		log.info("Resultado obitdo   = " + c.toString());
		assertTrue(cliente.equals(c));
	}

}
