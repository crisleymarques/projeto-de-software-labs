package com.ufcg.psoft.mercadofacil.dto;

public class ClienteDTO {

	private String cpf;

	private String nome;

	private String telefone;

	private String endereco;

	public ClienteDTO(String cpf, String nome, String telefone, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() { return telefone; }

	public String getEndereco() {
		return endereco;
	}
}