package br.com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eventos.model.Cupom;
import br.com.eventos.model.dto.CupomRequisicaoDTO;
import br.com.eventos.service.CupomServico;

@RestController
@RequestMapping("/api/cupons")
public class CupomController {
	
	@Autowired
	private CupomServico cupomServico;
	
	@PostMapping("/evento/{idEvento}")
	public ResponseEntity<Cupom> cadastra(@PathVariable Long idEvento, @RequestBody CupomRequisicaoDTO cupomDTO){
		return new ResponseEntity<>(cupomServico.adicionaCupomAoEvento(idEvento, cupomDTO), HttpStatus.CREATED);
	}
	
}	
