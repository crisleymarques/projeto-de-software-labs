package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.components.cliente.Normal;
import com.ufcg.psoft.mercadofacil.components.cliente.Perfil;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Cliente {

	private String cpf;

	private String nome;

	private String telefone;

	private String endereco;

	private Carrinho carrinho;

	@Enumerated(EnumType.STRING)
	private Perfil perfil;

	public Cliente(String cpf, String nome, String telefone, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.carrinho = null;
		this.perfil = new Normal();
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

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public String toString() {
		return "Cliente: " + getNome() + " - Endereço: " + getEndereco() + " - Telefone: " + getTelefone() + " | Perfil: " + getPerfil();
	}
}
