package com.ufcg.psoft.mercadofacil.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Carrinho {

	private String id;

	private List<Item> itens;

	private double valor;

	public Carrinho() {
		this.id = UUID.randomUUID().toString();
		this.itens = new ArrayList<>();
		this.valor = 0;
	}

	public String getId() {
		return id;
	}

	public List<Item> getItens() {
		return itens;
	}

	public int getQtdItens() {
		int qtd = 0;
		for (Item i : getItens()) {
			qtd += i.getQuantidade();
		}
		return qtd;
	}

	public double getValor() {
		calcularValor();
		return this.valor;
	}

	public void setItem(Item item) {
		this.itens.add(item);
	}

	public void removeItem(Item item) { this.itens.remove(item); }

	private void calcularValor() {
		double total = 0;
		for (Item i : getItens()) {
			total += i.getProduto().getPreco() * i.getQuantidade();
		}
		this.valor = total;
	}

	@Override
	public String toString() {
		return "Carrinho de ID: " + getId() + " - ";
	}
}
