package com.ufcg.psoft.mercadofacil.dto;

import java.util.Date;

public class LoteDTO {
	
	private String idProduto;
	
	private int quantidade;

	private Date dataFabricacao;

	private Date dataValidade;

	public LoteDTO(String idProduto, int quantidade, Date dataFabricacao, Date dataValidade) {
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.dataFabricacao = dataFabricacao;
		this.dataValidade = dataValidade;
	}
	
	public String getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Date getDataFabricacao() {
		return dataFabricacao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}
}
