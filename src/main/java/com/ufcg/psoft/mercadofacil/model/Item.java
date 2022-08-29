package com.ufcg.psoft.mercadofacil.model;

public class Item {

	private Produto produto;

	private Lote lote;

	private int quantidade;

	public Item(Produto produto, Lote lote, int quantidade) {
		this.produto = produto;
		this.lote = lote;
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public Lote getLote() {
		return lote;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public String toString() {
		return "- Item: " + getProduto().getNome() + " - Fabricante: " + getProduto().getFabricante() + " - Preço: R$ "
				+ getProduto().getPreco() + " - Lote: " + getLote().getId() + " - Quantidade: " + getQuantidade();
	}
}
