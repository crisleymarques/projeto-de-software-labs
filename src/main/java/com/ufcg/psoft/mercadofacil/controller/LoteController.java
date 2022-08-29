package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {

    @Autowired
    private LoteService loteService;

    @RequestMapping(value = "/lote", method = RequestMethod.POST)
    public ResponseEntity<?> criarLote(@RequestBody LoteDTO loteDTO) {
        String loteID;
        try {
            loteID = loteService.adicionarLote(loteDTO);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Lote criado com ID:" + loteID, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/lote/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarLote(@PathVariable("id") String id) {
        Lote lote;
        try {
            lote = loteService.getLoteById(id);
        } catch (LoteNotFoundException e) {
            return new ResponseEntity<>("Lote não encontrado", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lote, HttpStatus.OK);
    }

    @RequestMapping(value = "/lote/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editarLote(@PathVariable("id") String id, @RequestBody LoteDTO loteDTO) {
        try {
            loteService.editarLote(id, loteDTO);
        } catch (LoteNotFoundException lnfe) {
            return new ResponseEntity<>("Lote não encontrado.", HttpStatus.NOT_FOUND);
        } catch (ProductNotFoundException pnfe) {
            return new ResponseEntity<>("Produto não encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Lote atualizado.", HttpStatus.OK);
    }

    @RequestMapping(value = "/lotes", method = RequestMethod.GET)
    public ResponseEntity<?> listarLotes() {
        List<Lote> lotes = loteService.listarLotes();
        return new ResponseEntity<>(lotes, HttpStatus.OK);
    }

    @RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletarLote(@PathVariable("id") String id) {
        try {
            loteService.deletarLote(id);
        } catch (LoteNotFoundException e) {
            return new ResponseEntity<>("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Lote deletado.", HttpStatus.OK);
    }
}