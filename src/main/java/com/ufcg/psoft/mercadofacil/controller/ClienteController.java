package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.components.cliente.ListagemPerfil;
import com.ufcg.psoft.mercadofacil.components.pagamento.ListagemPagamentos;
import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ClienteAlredyExistsException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value = "/cliente/", method = RequestMethod.POST)
	public ResponseEntity<?> criarCliente(@RequestBody ClienteDTO clienteDTO) {
		try {
			clienteService.adicionarCliente(clienteDTO);
		} catch (ClienteAlredyExistsException e) {
			return new ResponseEntity<>("O cliente já existe no sistema.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("Cliente cadastrado com CPF:" + clienteDTO.getCpf(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/cliente/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarCliente(@PathVariable("cpf") String cpf) {
		try {
			clienteService.deletarCliente(cpf);
		} catch (ClienteNotFoundException e) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Cliente deletado.", HttpStatus.OK);
	}

	@RequestMapping(value = "/cliente/{cpf}", method = RequestMethod.PUT)
	public ResponseEntity<?> editarCliente(@PathVariable("cpf") String cpf, @RequestBody ClienteDTO clienteDTO) {
		try {
			clienteService.editarCliente(cpf, clienteDTO);
		} catch (ClienteNotFoundException e) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Cliente atualizado.", HttpStatus.OK);
	}

	@RequestMapping(value = "/cliente/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarCliente(@PathVariable("cpf") String cpf) {
		Cliente cliente;
		try {
			cliente = clienteService.getClienteByCpf(cpf);
		} catch (ClienteNotFoundException e) {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public ResponseEntity<?> listarClientes() {
		List<String> clientes = clienteService.listarClientes();
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@RequestMapping(value = "/clientes/perfil/{cpf}", method = RequestMethod.PUT)
	public ResponseEntity<?> estabelecerPerfil(@PathVariable String cpf, @RequestParam ListagemPerfil perfil) {
		try {
			clienteService.estabelecerPerfil(cpf, perfil);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Perfil do cliente de CPF: "+ cpf + " alterado com sucesso.", HttpStatus.OK);
	}
}
