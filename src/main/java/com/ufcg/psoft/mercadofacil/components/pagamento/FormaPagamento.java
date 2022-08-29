package com.ufcg.psoft.mercadofacil.components.pagamento;

public abstract class FormaPagamento {

    public abstract ListagemPagamentos getName();

    public double calculaValorAcrescimo(double valor) {
        return valor * getName().getTaxa();
    }

    public String toString() {
        return getName() + " - Taxa: " + getName().getTaxa() * 100 + "%";
    }
}
