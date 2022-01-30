package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scc.mantemCliente.model.Cliente;
import com.fatec.scc.mantemCliente.ports.MantemCliente;
@SpringBootTest
class REQ01PCadastrarClienteTests {

	@Autowired
	private MantemCliente mantemCliente;
	@Test
	void ct01_quando_cadastra_cliente_valido_retorna_detalhes_do_clientes_cadastrado() {
		//Dado que o cliente nao esta cadastrado
		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		
		Cliente cliente = new Cliente("Rodrigues Santos", "10/02/1960", "M", "34334778178", "04280130", "2983");
		//Quando confirma o cadastro
		Cliente c = mantemCliente.save(cliente);
		//Entao retorna os detalhes do cliente cadastrado
		assertNotNull(c);
		assertEquals (dataAtual.toString(fmt),c.getDataCadastro());
		cliente.setId(c.getId());
		assertTrue(cliente.equals(c));
	}
	@Test
	void ct02_quando_cadastra_cliente_ja_cadastrado_retorna_erro() {
		// Dado que o cliente esta cadastrado 
		Cliente cliente = new Cliente("Miguel Soares", "10/02/1960", "M", "99504993052", "04280130", "2983");
		//Quando confirma o cadastro
		Cliente c = mantemCliente.save(cliente);
		//Entao retorna nulo
		assertNull(c);
		
	}
	@Test
	void ct03_quando_cadastra_cliente_com_dados_invalidos_retorna_erro() {
		// Dado que o cpf do cliente nao esta cadastrado  
		Cliente cliente = new Cliente("", "10/02/1960", "M", "99504993052", "04280130", "2983");
		//Quando confirma o cadastro sem nome
		Cliente c = mantemCliente.save(cliente);
		//Entao retorna nulo
		assertNull(c);
		
	}
}
