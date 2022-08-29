package com.ufcg.psoft.mercadofacil.components.cliente;

public abstract class Perfil {

    public abstract ListagemPerfil getName();

    public double calculaDesconto(double valor, int qtdProdutos) {
        if (qtdProdutos > getName().getQuantidade()) {
            return valor * getName().getDesconto();
        }
        return 0;
    }

    public String toString() {
        return "Cliente " + getName() +
                " possui " + getName().getDesconto() * 100 +
                "% de desconto acima de " + getName().getQuantidade() + " itens.";
    }
}
