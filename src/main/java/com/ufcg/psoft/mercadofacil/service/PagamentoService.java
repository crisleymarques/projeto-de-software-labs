package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.components.pagamento.Boleto;
import com.ufcg.psoft.mercadofacil.components.pagamento.FormaPagamento;
import com.ufcg.psoft.mercadofacil.components.pagamento.FormaPagamentoFactory;
import com.ufcg.psoft.mercadofacil.components.pagamento.ListagemPagamentos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

	@Autowired
	private FormaPagamentoFactory formaPagamentoFactory;

	public StringBuilder listarFormasPagamento() {
		StringBuilder formas = new StringBuilder();
		for (FormaPagamento f : formaPagamentoFactory.getAll().toArray(FormaPagamento[]::new)) {
			formas.append(f.toString()).append("\n");
		}
		return formas;
	}

	public FormaPagamento getFormaPagamentoByListagem(ListagemPagamentos forma) {
		if (forma == null) {
			return new Boleto();
		}
		return this.formaPagamentoFactory.getFormaPagamento(forma);
	}
}
