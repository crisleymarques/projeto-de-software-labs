package com.ufcg.psoft.mercadofacil.components.cliente;

import org.springframework.stereotype.Component;

@Component
public class Especial extends Perfil {
    @Override
    public ListagemPerfil getName() {
        return ListagemPerfil.ESPECIAL;
    }

}
