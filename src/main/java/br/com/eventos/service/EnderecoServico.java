package br.com.eventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventos.model.Endereco;
import br.com.eventos.model.Evento;
import br.com.eventos.model.dto.EventoRequisicaoDTO;
import br.com.eventos.repository.EnderecoRepositorio;

@Service
public class EnderecoServico {
	
	
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	
	
	public Endereco cadastrar(EventoRequisicaoDTO eventoDTO, Evento evento) {
		Endereco endereco = new Endereco();
		endereco.setCidade(eventoDTO.cidade());
		endereco.setUf(eventoDTO.estado());
		endereco.setEvento(evento);
		
		return enderecoRepositorio.save(endereco);
	}
	
}
