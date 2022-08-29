package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ClienteRepository {

	private Map<String, Cliente> clientes;

	public ClienteRepository() {
		this.clientes = new HashMap<>();
	}

	public void addCliente(Cliente cliente) {
		this.clientes.put(cliente.getCpf(), cliente);
	}

	public void delCliente(String cpf) {
		this.clientes.remove(cpf);
	}

	public void editCliente(String cpf, Cliente clienteAtt) {
		this.clientes.replace(cpf, clienteAtt);
	}

	public Collection<Cliente> getAll() {
		return clientes.values();
	}

	public Cliente getCliente(String cpf) {
		return this.clientes.get(cpf);
	}

}
