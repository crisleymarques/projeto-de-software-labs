package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.components.pagamento.FormaPagamento;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.text.DecimalFormat;
import java.util.UUID;

public class Compra {

	private String id;

	private Carrinho carrinho;

	private Cliente cliente;

	private double valorTotal;

	private double desconto;

	private double acrescimo;

	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;

	private DecimalFormat df;

	public Compra(Cliente cliente, FormaPagamento formaPagamento, double valorTotal, double desconto, double acrescimo) {
		this.id = UUID.randomUUID().toString();
		this.cliente = cliente;
		this.carrinho = cliente.getCarrinho();
		this.valorTotal = valorTotal;
		this.desconto = desconto;
		this.acrescimo = acrescimo;
		this.formaPagamento = formaPagamento;
		this.df = new DecimalFormat("#.##");
	}

	public String getId() {
		return id;
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public String getDescricaoItens() {
		String itens = "";
		for (Item i : this.carrinho.getItens()) {
			itens = itens.concat(i.toString() + "\n");
		}
		return itens;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public double getDesconto() {
		return desconto;
	}

	public double getAcrescimos() {
		return acrescimo;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	@Override
	public String toString() {
		return "=> Compra de ID: " + getId() +
				" feita por " + getCliente().getNome() +
				" de CPF " + getCliente().getCpf() +
				". \nLista de itens adquiridos: \n" + getDescricaoItens() +
				"\nA forma de pagamento foi: " + getFormaPagamento() +
				"\nValor dos produtos do carrinho  R$ " + df.format(carrinho.getValor()) +
				"\nDesconto pelo perfil de cliente R$-" + df.format(getDesconto()) +
				"\nAcréscimo da forma de pagamento R$ " + df.format(getAcrescimos()) +
				"\n---------> O valor total foi de R$ " + df.format(getValorTotal()) + "\n\n";
	}
}
