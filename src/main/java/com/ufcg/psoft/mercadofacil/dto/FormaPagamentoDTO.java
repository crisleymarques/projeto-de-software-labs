package com.ufcg.psoft.mercadofacil.dto;

import com.ufcg.psoft.mercadofacil.components.pagamento.ListagemPagamentos;

public class FormaPagamentoDTO {
    private ListagemPagamentos formaPagamento;

    public FormaPagamentoDTO() {}

    public FormaPagamentoDTO(ListagemPagamentos formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public ListagemPagamentos getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(ListagemPagamentos formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
