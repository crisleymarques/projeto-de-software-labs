package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.CarrinhoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
		String prodID = produtoService.adicionarProduto(produtoDTO);
		return new ResponseEntity<>("Produto cadastrado com ID:" + prodID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarProduto(@PathVariable("id") String id) {
		try {
			produtoService.deletarProduto(id);
		} catch (ProductNotFoundException pnfe) {
			return new ResponseEntity<>("Produto não encontrado.", HttpStatus.NOT_FOUND);
		} catch (LoteNotFoundException lnfe) {
			return new ResponseEntity<>("Lote não encontrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Produto deletado.", HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> editarProduto(@PathVariable("id") String id, @RequestBody ProdutoDTO produtoDTO) {
		try {
			produtoService.editarProduto(id, produtoDTO);
		} catch (ProductNotFoundException pnfe) {
			return new ResponseEntity<>("Produto não encontrado.", HttpStatus.NOT_FOUND);
		} catch (LoteNotFoundException lnfe) {
			return new ResponseEntity<>("Lote não encontrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Produto atualizado.", HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") String id) {
		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos() {
		List<Produto> produtos = produtoService.listarProdutos();
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos-lote", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosComLote() {
		List<Produto> produtos = produtoService.listarProdutosComLote();
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosPeloNome(@PathVariable("nome") String nome) {
		List<Produto> produtos = produtoService.listarProdutosByName(nome);
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos-lote{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosComLotePeloNome(@PathVariable("nome") String nome) {
		List<Produto> produtos = produtoService.listarProdsLoteByName(nome);
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
}
