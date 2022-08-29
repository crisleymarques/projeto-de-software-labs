package com.ufcg.psoft.mercadofacil.dto;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDTO {

	private Cliente cliente;

	private List<Produto> produtos;

	public CarrinhoDTO(Cliente cliente) {
		this.cliente = cliente;
		this.produtos = new ArrayList<>();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
}