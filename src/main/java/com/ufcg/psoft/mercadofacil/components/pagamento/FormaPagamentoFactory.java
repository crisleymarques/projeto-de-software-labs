package com.ufcg.psoft.mercadofacil.components.pagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class FormaPagamentoFactory {

    private Map<ListagemPagamentos, FormaPagamento> formasPagamento;

    @Autowired
    public FormaPagamentoFactory(Set<FormaPagamento> formasSet) {
        this.formasPagamento = new HashMap<>();
        mapearFormasPagamento(formasSet);
    }

    private void mapearFormasPagamento(Set<FormaPagamento> formasSet) {
        formasSet.forEach(
                forma -> formasPagamento.put(forma.getName(), forma));
    }

    public FormaPagamento getFormaPagamento(ListagemPagamentos listagemPagamentos) {
        return formasPagamento.get(listagemPagamentos);
    }

    public Stream<FormaPagamento> getAll() {
        return this.formasPagamento.values().stream().sorted(
                Comparator.comparing(FormaPagamento::getName)
        );
    }
}
