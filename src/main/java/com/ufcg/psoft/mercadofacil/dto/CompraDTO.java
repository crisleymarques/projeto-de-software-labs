package com.ufcg.psoft.mercadofacil.dto;

import com.ufcg.psoft.mercadofacil.model.Carrinho;

public class CompraDTO {

	private String id;

	private Carrinho carrinho;

	private double valorTotal;

	public CompraDTO(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public String getId() { return this.id; }

	public Carrinho getCarrinho() {
		return this.carrinho;
	}

	public double getValorTotal() {
		return valorTotal;
	}
}