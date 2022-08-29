package com.ufcg.psoft.mercadofacil.repository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Produto;

@Repository
public class ProdutoRepository {
	
	private Map<String, Produto> produtos;
	
	public ProdutoRepository() {
		this.produtos = new HashMap<>();
	}

	public Collection<Produto> getAll() {
		return produtos.values();
	}
	
	public Produto getProd(String id) {
		return this.produtos.get(id);
	}
	
	public void delProd(String id) {
		this.produtos.remove(id);
	}
	
	public void editProd(String id, Produto prodAtt) {
		this.produtos.replace(id, prodAtt);
	}
	
	public void addProduto(Produto prod) {
		this.produtos.put(prod.getId(), prod);
	}
}
