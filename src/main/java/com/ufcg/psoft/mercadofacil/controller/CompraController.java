package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.components.pagamento.ListagemPagamentos;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraController {
	
	@Autowired
	private CompraService compraService;

	@Autowired
	private PagamentoService pagamentoService;

	@RequestMapping(value = "/compra/{cpf}", method = RequestMethod.POST)
	public ResponseEntity<?> finalizarCompra(@PathVariable String cpf, @RequestParam ListagemPagamentos formaPagamento) {
		String descricao;
		try {
			descricao = compraService.finalizarCompra(cpf, formaPagamento);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		} catch (CarrinhoNotFoundException cnfe) {
			return new ResponseEntity<>("Não existem produtos no carrinho.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Compra efetuada com sucesso! \n \n" + descricao, HttpStatus.OK);
	}

	@RequestMapping(value = "/compras/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<?> descartarCompra(@PathVariable("cpf") String cpf) {
		try {
			compraService.descartarCompra(cpf);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException pnfe) {
			return new ResponseEntity<>("Produto não existe no carrinho.", HttpStatus.NOT_FOUND);
		} catch (LoteNotFoundException lnfe) {
			return new ResponseEntity<>("Lote não encontrado.", HttpStatus.NOT_FOUND);
		} catch (CarrinhoNotFoundException cnfe) {
			return new ResponseEntity<>("Não há uma compra a ser descartada.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Carrinho de compras descartado no CPF " + cpf, HttpStatus.OK);
	}

	@RequestMapping(value = "/compra/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarHistoricoCompras(@PathVariable("cpf") String cpf) {
		StringBuilder compras;
		try {
			compras = compraService.consultarHistorico(cpf);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		} catch (CompraNotFoundException cnfe) {
			return new ResponseEntity<>("Não foi encontrada nenhuma compra nesse CPF.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(compras, HttpStatus.OK);
	}

	@RequestMapping(value = "/compras/{cpf}/{idCompra}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarCompra(@PathVariable("cpf") String cpf, @PathVariable("idCompra") String idCompra) {
		String compra;
		try {
			compra = compraService.consultarCompra(cpf, idCompra);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		} catch (CompraNotFoundException cnfe) {
			return new ResponseEntity<>("Não foi encontrada nenhuma compra nesse CPF.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(compra, HttpStatus.OK);
	}

	@RequestMapping(value = "/compras/formaPagamento", method = RequestMethod.GET)
	public ResponseEntity<?> listarFormasPagamento() {
		StringBuilder formas = pagamentoService.listarFormasPagamento();
		return new ResponseEntity<>(formas, HttpStatus.OK);
	}
}