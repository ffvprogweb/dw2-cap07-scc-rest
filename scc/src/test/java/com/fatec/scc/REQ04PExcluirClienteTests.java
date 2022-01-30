package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemCliente;

@SpringBootTest
class REQ04PExcluirClienteTests {
	@Autowired
	private MantemCliente mantemCliente;

	@Test
	public void ct01_dado_que_o_id_cliente_existe_na_exclusao_retorna_no_content() {
		//Dado que o id esta cadastrado
		Optional<Cliente> c = mantemCliente.consultaPorCpf("99504993052");
		assertTrue(c.isPresent());
		//Quando exclui pelo id
		mantemCliente.delete(c.get().getId());
		c = mantemCliente.consultaPorCpf("99504993052");
		// Entao retorna nulo
		assertTrue(c.isEmpty());
	}

}
