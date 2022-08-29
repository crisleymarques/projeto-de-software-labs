package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.exception.CarrinhoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoController {
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	@RequestMapping(value = "/carrinho/{cpf}", method = RequestMethod.POST)
	public ResponseEntity<?> adicionarProdutoCarrinho(@PathVariable("cpf") String cpf, @RequestParam String prodID) {
		String carrID;
		try {
			carrID = carrinhoService.adicionarProdutoCarrinho(cpf, prodID);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		} catch (LoteNotFoundException lnfe) {
			return new ResponseEntity<>("Lote não encontrado.", HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException pnfe) {
			return new ResponseEntity<>("Produto não encontrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Produto adicionado no carrinho de ID:" + carrID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/carrinho/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerProdutoCarrinho(@PathVariable("cpf") String cpf, @RequestParam String prodID) {
		try {
			carrinhoService.removerProdutoCarrinho(cpf, prodID);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException pnfe) {
			return new ResponseEntity<>("Produto não existe no carrinho.", HttpStatus.NOT_FOUND);
		} catch (LoteNotFoundException lnfe) {
			return new ResponseEntity<>("Lote não encontrado.", HttpStatus.NOT_FOUND);
		} catch (CarrinhoNotFoundException cnfe) {
			return new ResponseEntity<>("Não há produtos a serem removidos.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Produto removido do carrinho do cliente de CPF: " + cpf, HttpStatus.OK);
	}

	@RequestMapping(value = "/carrinho/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarCarrinho(@PathVariable("cpf") String cpf) {
		Carrinho carrinho;
		try {
			carrinho = carrinhoService.consultarCarrinho(cpf);
		} catch (ClienteNotFoundException cnfe) {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
		} catch (CarrinhoNotFoundException cnfe) {
			return new ResponseEntity<>("O cliente não tem nenhum produto no carrinho.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(carrinho, HttpStatus.OK);
	}

}
