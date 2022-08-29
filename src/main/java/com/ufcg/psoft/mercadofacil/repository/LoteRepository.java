package com.ufcg.psoft.mercadofacil.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Lote;

@Repository
public class LoteRepository {
	
	private Map<String, Lote> lotes = new HashMap<>();
	
	public Collection<Lote> getAll() {
		return this.lotes.values();
	}
	
	public Lote getLote(String id) {
		return this.lotes.get(id);
	}
	
	public void delLot(String id) {
		this.lotes.remove(id);
	}
	
	public void editLote(Lote lote) {
		this.lotes.replace(lote.getId(), lote);
	}
	
	public void addLote(Lote lote) {
		this.lotes.put(lote.getId(), lote);
	}

    public void editLote(String id, Lote lote) {
		this.lotes.replace(id, lote);
    }
}
