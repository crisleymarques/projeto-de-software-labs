package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarrinhoService {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private LoteService loteService;

	@Autowired
	private ClienteService clienteService;

	public String adicionarProdutoCarrinho(String clienteCPF, String prodID) throws ProductNotFoundException, LoteNotFoundException, ClienteNotFoundException {
		Produto produto = this.produtoService.getProdutoById(prodID);
		List<Lote> lotes = this.loteService.getLoteByProd(produto);

		Lote menorLote = verificarLote(lotes);

		Carrinho carrinho = getCarrinhoByCpf(clienteCPF);
		carrinho = criarCarrinhoCliente(carrinho);

		Item item = new Item(produto, menorLote, 1);
		setItemCarrinho(carrinho, item);

		menorLote.setQuantidade(menorLote.getQuantidade() - 1);

		this.loteService.atualizarQtdLote(menorLote.getId(), menorLote);
		this.clienteService.atualizarCarrinho(clienteCPF, carrinho);

		return carrinho.getId();
	}

	private void setItemCarrinho(Carrinho carrinho, Item item) {
		boolean flag = false;
		for (Item i : carrinho.getItens()) {
			if (i.getLote().equals(item.getLote())) {
				i.setQuantidade(i.getQuantidade() + item.getQuantidade());
				flag = true;
			}
		}
		if (!flag) {
			carrinho.setItem(item);
		}
	}

	private Carrinho criarCarrinhoCliente(Carrinho carrinho) {
		if (carrinho == null) {
			carrinho = new Carrinho();
		}
		return carrinho;
	}

	public void verificarCarrinhoCliente(Carrinho carrinho) throws CarrinhoNotFoundException {
		if (carrinho == null) {
			throw new CarrinhoNotFoundException("Carrinho não encotrado.");
		}
	}

	private List<Item> getItensByProd(Carrinho carrinho, Produto produto) throws ProductNotFoundException {
		List<Item> itemList = new ArrayList<>();
		for (Item item : carrinho.getItens()) {
			if (item.getProduto().equals(produto)) {
				itemList.add(item);
			}
		}
		if (itemList.isEmpty()) { throw new ProductNotFoundException("Produto não existe no carrinho."); }
		return itemList;
	}

	public void removerProdutoCarrinho(String clienteCPF, String prodID) throws ClienteNotFoundException, ProductNotFoundException, LoteNotFoundException, CarrinhoNotFoundException {
		Carrinho carrinho = getCarrinhoByCpf(clienteCPF);
		verificarCarrinhoCliente(carrinho);

		Produto produto = this.produtoService.getProdutoById(prodID);

		List<Item> itensCarrinho = getItensByProd(carrinho, produto);
		Item item = getItemMaiorData(itensCarrinho);

		Lote lote = item.getLote();
		lote.setQuantidade(lote.getQuantidade() + 1);
		this.loteService.atualizarQtdLote(lote.getId(), lote);

		if (isLastItem(carrinho)) {
			removerCarrinho(clienteCPF);
		} else {
			decrementarItem(carrinho, item);
			this.clienteService.atualizarCarrinho(clienteCPF, carrinho);
		}
	}

	public void decrementarItem(Carrinho carrinho, Item item) {
		if (item.getQuantidade() > 1) {
			item.setQuantidade(item.getQuantidade() - 1);
		} else {
			carrinho.removeItem(item);
		}
	}

	public boolean isLastItem(Carrinho carrinho) {
		List<Item> itensCarrinho = carrinho.getItens();
		return itensCarrinho.size() == 1 && itensCarrinho.get(0).getQuantidade() == 1;
	}

	private Item getItemMaiorData(List<Item> itens) {
		Item maior = itens.get(0);
		for (Item i : itens ) {
			if (i.getLote().getDataValidade().compareTo(maior.getLote().getDataValidade()) > 0) {
				maior = i;
			}
		}
		return maior;
	}

	private Carrinho getCarrinhoByCpf(String cpf) throws ClienteNotFoundException {
		Cliente cliente = this.clienteService.getClienteByCpf(cpf);
		if (cliente.getCarrinho() != null) {
			return cliente.getCarrinho();
		}
		return null;
	}

	private Lote verificarLote(List<Lote> lotes) throws LoteNotFoundException {
		lotes.removeIf(lote -> lote.getQuantidade() < 1);
		if (lotes.isEmpty()) { throw new LoteNotFoundException("Quantidade de produtos no lote insuficiente!"); }

		return this.loteService.getLoteMenorData(lotes);
	}

	public void removerCarrinho(String clienteCPF) throws ClienteNotFoundException {
		this.clienteService.atualizarCarrinho(clienteCPF, null);
	}

	public Carrinho consultarCarrinho(String cpf) throws ClienteNotFoundException, CarrinhoNotFoundException {
		Carrinho carrinho = getCarrinhoByCpf(cpf);
		if (carrinho == null) {
			throw new CarrinhoNotFoundException("O cliente não tem nada no carrinho.");
		}
		return carrinho;
	}
}
