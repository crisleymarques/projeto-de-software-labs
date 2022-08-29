package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.components.cliente.ListagemPerfil;
import com.ufcg.psoft.mercadofacil.components.cliente.Perfil;
import com.ufcg.psoft.mercadofacil.components.cliente.PerfilFactory;
import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteAlredyExistsException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRep;

	@Autowired
	private PerfilFactory perfilFactory;

	public void adicionarCliente(ClienteDTO clienteDTO) throws ClienteAlredyExistsException {
		if (!hasCliente(clienteDTO.getCpf())) {
			Cliente cliente = new Cliente(clienteDTO.getCpf(), clienteDTO.getNome(), clienteDTO.getTelefone(), clienteDTO.getEndereco());
			this.clienteRep.addCliente(cliente);
		}
		else { throw new ClienteAlredyExistsException("Cliente de CPF " + clienteDTO.getCpf() + " já existe."); }
	}

	public void editarCliente(String cpf, ClienteDTO clienteDTO) throws ClienteNotFoundException {
		Cliente cliente = getClienteByCpf(cpf);
		alteraCliente(clienteDTO, cliente);
		this.clienteRep.editCliente(cpf, cliente);
	}

	public void deletarCliente(String cpf) throws ClienteNotFoundException {
		getClienteByCpf(cpf);
		this.clienteRep.delCliente(cpf);
	}

	public Cliente getClienteByCpf(String cpf) throws ClienteNotFoundException {
		Cliente cliente = this.clienteRep.getCliente(cpf);
		if(cliente == null) throw new ClienteNotFoundException("Cliente de CPF " + cpf + " não encontrado");

		return(cliente);
	}

	public List<String> listarClientes() {
		List<String> clientes = new ArrayList<>();
		for (Cliente cli : this.clienteRep.getAll()) {
			String info = "CPF: " + cli.getCpf() + ", Nome: " + cli.getNome() + " | " + cli.getPerfil();
			clientes.add(info);
		}
		return(clientes);
	}

	private void alteraCliente(ClienteDTO clienteDTO, Cliente cliente) {
		cliente.setTelefone(clienteDTO.getTelefone());
		cliente.setEndereco(clienteDTO.getEndereco());
	}

	public void atualizarCarrinho(String cpf, Carrinho carrinho) throws ClienteNotFoundException {
		Cliente cliente = getClienteByCpf(cpf);
		cliente.setCarrinho(carrinho);
		this.clienteRep.editCliente(cpf, cliente);
	}

	public boolean hasCliente(String cpf) {
		Cliente cliente = this.clienteRep.getCliente(cpf);
		return cliente != null;
	}

	public void estabelecerPerfil(String cpf, ListagemPerfil listagemPerfil) throws ClienteNotFoundException {
		Cliente cliente = getClienteByCpf(cpf);
		Perfil perfil = this.perfilFactory.getPerfil(listagemPerfil);

		cliente.setPerfil(perfil);
		this.clienteRep.editCliente(cpf, cliente);
	}
}
