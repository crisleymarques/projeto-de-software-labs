package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.components.pagamento.FormaPagamento;
import com.ufcg.psoft.mercadofacil.components.pagamento.ListagemPagamentos;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {
	
	@Autowired
	private CompraRepository compraRep;

	@Autowired
	private CarrinhoService carrinhoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private  PagamentoService pagamentoService;

	public String finalizarCompra(String clienteCPF, ListagemPagamentos formaListagem) throws ClienteNotFoundException, CarrinhoNotFoundException {
		Cliente cliente = this.clienteService.getClienteByCpf(clienteCPF);
		FormaPagamento formaPagamento = pagamentoService.getFormaPagamentoByListagem(formaListagem);
		Carrinho carrinho = cliente.getCarrinho();

		this.carrinhoService.verificarCarrinhoCliente(carrinho);

		Compra compra = criarCompra(cliente, carrinho, formaPagamento);

		this.compraRep.addCompra(compra);
		this.carrinhoService.removerCarrinho(clienteCPF);

		return compra.toString();
	}

	private Compra criarCompra(Cliente cliente, Carrinho carrinho, FormaPagamento formaPagamento) {
		double valor = carrinho.getValor();
		double desconto = cliente.getPerfil().calculaDesconto(valor, carrinho.getQtdItens());
		double acrescimo = formaPagamento.calculaValorAcrescimo(valor - desconto);
		double total = valor - desconto + acrescimo;

		return new Compra(cliente, formaPagamento, total, desconto, acrescimo);
	}

	public void descartarCompra(String clienteCPF) throws ClienteNotFoundException, CarrinhoNotFoundException, ProductNotFoundException, LoteNotFoundException {
		Cliente cliente = this.clienteService.getClienteByCpf(clienteCPF);
		Carrinho carrinho = cliente.getCarrinho();
		this.carrinhoService.verificarCarrinhoCliente(carrinho);
		this.carrinhoService.removerCarrinho(clienteCPF);
	}

	public String consultarCompra(String cpf, String idCompra) throws CompraNotFoundException, ClienteNotFoundException {
		return getCompra(cpf, idCompra).toString();
	}

	private Compra getCompra(String cpf, String idCompra) throws CompraNotFoundException, ClienteNotFoundException {
		for (Compra c : getCompraByCpf(cpf)) {
			if (c.getId().equals(idCompra)) {
				return c;
			}
		}
		throw new CompraNotFoundException("Não existe nenhuma compra com esse ID nesse CPF.");
	}

	public StringBuilder consultarHistorico(String clienteCPF) throws CompraNotFoundException, ClienteNotFoundException {
		StringBuilder historico = new StringBuilder();
		for (Compra c : getCompraByCpf(clienteCPF)) {
			historico.append(c.toString());
		}
		return historico;
	}

	private List<Compra> getCompraByCpf(String cpf) throws ClienteNotFoundException, CompraNotFoundException {
		this.clienteService.getClienteByCpf(cpf);
		List<Compra> compras = new ArrayList<>();
		for (Compra c : this.compraRep.getAll()) {
			if (c.getCliente().getCpf().equals(cpf)) {
				compras.add(c);
			}
		}
		if (compras.isEmpty()) { throw new CompraNotFoundException("Não foi encontrada nenhuma compra nesse CPF."); }
		return compras;
	}
}
