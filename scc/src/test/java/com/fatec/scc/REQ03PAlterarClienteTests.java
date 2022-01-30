package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemCliente;

@SpringBootTest
class REQ03PAlterarClienteTests {
	@Autowired
	private MantemCliente mantemCliente;

	@Test
	void ct01_quando_cliente_eh_alterado_com_dados_validos_retorna_alteracao_com_sucesso() {
		// Dado que existem 2 clientes cadastrados
		// Quando consulta
		Optional<Cliente> c = mantemCliente.consultaPorCpf("99504993052");
		Cliente cliente = c.get();
		cliente.setNome("Novo Nome");
		Cliente clienteModificado = mantemCliente.altera(cliente);
		assertEquals("Novo Nome", clienteModificado.getNome());
	}

}
