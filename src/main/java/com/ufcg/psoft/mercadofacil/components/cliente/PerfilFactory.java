package com.ufcg.psoft.mercadofacil.components.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class PerfilFactory {

    private Map<ListagemPerfil, Perfil> perfis;

    @Autowired
    public PerfilFactory(Set<Perfil> perfisSet) {
        this.perfis = new HashMap<>();
        mapearClientes(perfisSet);
    }

    private void mapearClientes(Set<Perfil> perfisSet) {
        perfisSet.forEach(
                perfil -> perfis.put(perfil.getName(), perfil));
    }

    public Perfil getPerfil(ListagemPerfil listagemPerfil) {
        return perfis.get(listagemPerfil);
    }

    public Stream<Perfil> getAll() {
        return this.perfis.values().stream().sorted(
                Comparator.comparing(Perfil::getName)
        );
    }
}
