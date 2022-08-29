package com.ufcg.psoft.mercadofacil.components.cliente;

public enum ListagemPerfil {
    NORMAL(0.0, 0),
    ESPECIAL(0.1, 10),
    PREMIUM(0.1, 5);

    private final double desconto;

    private final int quantidade;

    ListagemPerfil(double desconto, int quantidade) {
        this.desconto = desconto;
        this.quantidade = quantidade;
    }

    public double getDesconto() {
        return desconto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
