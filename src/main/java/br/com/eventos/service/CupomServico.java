package br.com.eventos.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventos.model.Cupom;
import br.com.eventos.model.Evento;
import br.com.eventos.model.dto.CupomRequisicaoDTO;
import br.com.eventos.repository.CupomRepositorio;
import br.com.eventos.repository.EventoRepositorio;

@Service
public class CupomServico {
	
	@Autowired
	private CupomRepositorio cupomRepositorio;
	
	@Autowired
	private EventoRepositorio eventoRepositorio;
	
	
	public Cupom adicionaCupomAoEvento(Long idEvento, CupomRequisicaoDTO cupomDTO) {
		Evento evento = this.eventoRepositorio
				.findById(idEvento)
				.orElseThrow(() -> new IllegalArgumentException("Evento não encontrado!"));
		
		Cupom cupom = new Cupom();
		cupom.setCodigo(cupomDTO.codigo());
		cupom.setDesconto(cupomDTO.desconto());
		cupom.setValidade(obtemValidadeEvento(cupomDTO));
		cupom.setEvento(evento);
		
		return cupomRepositorio.save(cupom);
	}
	
	private OffsetDateTime obtemValidadeEvento(CupomRequisicaoDTO eventoDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime datetime = LocalDateTime.parse(eventoDTO.validade(), formatter);
		ZoneOffset offset = ZoneId.of("UTC").getRules().getOffset(datetime);
		return OffsetDateTime.of(datetime , offset);
	}

	public List<Cupom> buscaCuponsValidos(Long idEvento, OffsetDateTime offsetDateTime) {
		return this.cupomRepositorio.findByEventoIdAndValidadeAfter(idEvento, offsetDateTime);
	}

	
}
