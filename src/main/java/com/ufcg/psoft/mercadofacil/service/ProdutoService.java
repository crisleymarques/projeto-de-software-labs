package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository prodRep;

	@Autowired
	private LoteService loteServ;

	public List<Produto> listarProdutos() {
		return new ArrayList<>(prodRep.getAll());
	}

	public List<Produto> listarProdutosComLote() {
		List<Produto> prods = new ArrayList<>();
		for (Lote lote : this.loteRep.getAll()) {
			prods.add(lote.getProduto());
		}
		return(prods);
	}

	public List<Produto> listarProdutosByName(String nome) {
		return getProdsByName(nome, this.prodRep.getAll());
	}

	public List<Produto> listarProdsLoteByName(String nome) {
		List<Produto> prods = listarProdutosComLote();
		return getProdsByName(nome, prods);
	}

	private List<Produto> getProdsByName(String nome, Collection<Produto> prods) {
		List<Produto> prodsResult = new ArrayList<>();
		for (Produto produto : prods) {
			if(produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
				prodsResult.add(produto);
			}
		}	
		return(prodsResult);
	}

	public Produto getProdutoById(String id) throws ProductNotFoundException {
		Produto prod = this.prodRep.getProd(id);
		if (prod == null) {
			throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		}
		return(prod);
	}

	public String adicionarProduto(ProdutoDTO prodDTO) {
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		this.prodRep.addProduto(produto);
		return produto.getId();
	}

    public void editarProduto(String id, @NotNull ProdutoDTO prodDTO) throws ProductNotFoundException, LoteNotFoundException {
		Produto p = getProdutoById(id);
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		this.prodRep.editProd(id, produto);

		if (this.loteServ.existeLoteProd(p)) {
			for (Lote l : this.loteServ.getLoteByProd(p)) {
				Lote lote = new Lote(produto, l.getQuantidade(), l.getDataFabricacao(), l.getDataValidade());
				this.loteRep.editLote(l.getId(), lote);
			}
		}
    }

	public void deletarProduto(String id) throws ProductNotFoundException, LoteNotFoundException {
		Produto p = getProdutoById(id);
		this.prodRep.delProd(id);

		for (Lote l : this.loteServ.getLoteByProd(p)) {
			this.loteRep.delLot(l.getId());
		}
	}
}
