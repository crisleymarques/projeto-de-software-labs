package com.ufcg.psoft.mercadofacil.components.cliente;

import org.springframework.stereotype.Component;

@Component
public class Normal extends Perfil {
    @Override
    public ListagemPerfil getName() {
        return ListagemPerfil.NORMAL;
    }

    @Override
    public double calculaDesconto(double valor, int qtdProdutos) {
        return 0;
    }

    @Override
    public String toString() {
        return "Cliente " + getName() + " não possui desconto na compra.";
    }
}
