package com.ufcg.psoft.mercadofacil.components.pagamento;

import org.springframework.stereotype.Component;

@Component
public class Paypal extends FormaPagamento{
    @Override
    public ListagemPagamentos getName() {
        return ListagemPagamentos.PAYPAL;
    }
}
