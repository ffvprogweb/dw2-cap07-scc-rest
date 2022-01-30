package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemCliente;

@SpringBootTest
class REQ02PConsultarClienteTests {
	@Autowired
	private MantemCliente mantemCliente;

	@Test
	void ct01_quando_consulta_todos_retorna_a_lista_de_clientes_cadastrados() {
		// Dado que existem 2 clientes cadastrados
		// Quando consulta todos
		List<Cliente> lista = mantemCliente.consultaTodos();
		// Entao retorna todos os clientes cadastrados
		assertTrue(lista.size() >= 2);
	}

	@Test
	void ct02_quando_consulta_por_id_cadastrado_retorna_detalhes_do_cliente() {
		// Dado que existem 2 clientes cadastrados
		// Quando consulta
		Optional<Cliente> c = mantemCliente.consultaPorId(1L);
		// Entao retorna os detalhes do cliente cadastrado
		assertTrue(c.isPresent());
	}

	@Test
	void ct03_quando_consulta_por_cpf_cadastrado_retorna_detalhes_do_cliente() {
		// Dado que existem 2 clientes cadastrados
		// Quando consulta
		Optional<Cliente> c = mantemCliente.consultaPorCpf("99504993052");
		// Entao retorna os detalhes do cliente cadastrado
		assertTrue(c.isPresent());
	}
	@Test
	void ct04_quando_consulta_cpf_valido_nao_cadastrado_retorna_nulo() {
		// Dado que o cpf nao esta cadastrado
		// Quando consulta
		Optional<Cliente> c = mantemCliente.consultaPorCpf("42365532608");
		// Entao retorna nulo
		assertTrue(c.isEmpty());
	}
}
