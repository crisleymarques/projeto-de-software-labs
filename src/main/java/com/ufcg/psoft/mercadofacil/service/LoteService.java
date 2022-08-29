package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class LoteService {

	@Autowired
	private LoteRepository loteRep;

	@Autowired
	private ProdutoService produtoServ;
	
	private Gson gson = new Gson();
	
	public String adicionarLote(LoteDTO loteDTO) throws ProductNotFoundException {
		Produto produto = this.produtoServ.getProdutoById(loteDTO.getIdProduto());

		Lote lote = new Lote(produto, loteDTO.getQuantidade(), loteDTO.getDataFabricacao(), loteDTO.getDataValidade());
		this.loteRep.addLote(lote);

		return lote.getId();
	}

	public Lote getLoteById(String id) throws LoteNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		if(lote == null) throw new LoteNotFoundException("Lote: " + id + " não encontrado");

		return(lote);
	}

	public List<Lote> getLoteByProd(Produto prod) throws LoteNotFoundException  {
		List<Lote> lotes = new ArrayList<>();
		for (Lote l : this.loteRep.getAll()) {
			if (l.getProduto().equals(prod)) {
				lotes.add(l);
			}
		}
		if (lotes.isEmpty()) { throw new LoteNotFoundException("Nenhum lote com o produto " + prod + " foi encontrado"); }
		return lotes;
	}

	public void editarLote(String id, LoteDTO loteDTO) throws LoteNotFoundException, ProductNotFoundException {
		Lote lote = getLoteById(id);
		produtoServ.getProdutoById(loteDTO.getIdProduto());
		transformaLote(loteDTO, lote);
		this.loteRep.editLote(id, lote);
	}

	private void transformaLote(LoteDTO loteDTO, Lote lote) throws ProductNotFoundException {
		Produto prod = this.produtoServ.getProdutoById(loteDTO.getIdProduto());
		lote.setProduto(prod);
		lote.setQuantidade(loteDTO.getQuantidade());
		lote.setDataFabricacao(loteDTO.getDataFabricacao());
		lote.setDataValidade(loteDTO.getDataValidade());
	}

	public void atualizarQtdLote(String id, Lote loteAtt) throws LoteNotFoundException {
		Lote lote = getLoteById(id);
		lote.setQuantidade(loteAtt.getQuantidade());
		this.loteRep.editLote(id, lote);
	}

	public List<Lote> listarLotes() {
		return new ArrayList<>(loteRep.getAll());
	}

	public void deletarLote(String id) throws LoteNotFoundException {
		getLoteById(id);
		loteRep.delLot(id);
	}

	public Lote getLoteMenorData(List<Lote> lotes) {
		return Collections.min(lotes, Comparator.comparing(Lote::getDataValidade));
	}

	public boolean existeLoteProd(Produto produto) {
		for (Lote l : listarLotes()) {
			if (l.getProduto().equals(produto)) {
				return true;
			}
		}
		return false;
	}
}
