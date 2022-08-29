package com.ufcg.psoft.mercadofacil.components.pagamento;

public enum ListagemPagamentos {
    BOLETO(0.00),
    PAYPAL(0.02),
    CARTAO(0.05);

    private final double taxa;

    ListagemPagamentos(double taxa) {
        this.taxa = taxa;
    }

    public double getTaxa() {
        return taxa;
    }
}
