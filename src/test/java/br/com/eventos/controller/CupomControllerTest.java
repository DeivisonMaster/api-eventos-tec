package br.com.eventos.controller;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.eventos.model.Cupom;
import br.com.eventos.model.Evento;
import br.com.eventos.model.dto.CupomRequisicaoDTO;
import br.com.eventos.repository.EventoRepositorio;
import br.com.eventos.service.CupomServico;

class CupomControllerTest {

	@Mock
	private CupomServico cupomServicoMock;
	
	@Mock
	private EventoRepositorio eventoRepositorioMock;
	
	@InjectMocks
	private CupomController cupomControllerMock;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		
		BDDMockito.when(cupomServicoMock.adicionaCupomAoEvento(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CupomRequisicaoDTO.class)))
			.thenReturn(criaNovoCupom());
	}
	
	@Test
	void deveCadastrarNovoCupom() {
		Long idEvento = 1L;
		ResponseEntity<Cupom> responseEntity = cupomControllerMock.cadastra(idEvento, criaCupomRequisicaoDTO());
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.hasBody());
		assertThat(responseEntity.getBody().getId()).isEqualTo(criaNovoCupom().getId());
	}

	private CupomRequisicaoDTO criaCupomRequisicaoDTO() {
		return new CupomRequisicaoDTO("12345", 10, "2027-11-02T20:10:10");
	}
	
	private Cupom criaNovoCupom() {
		Cupom cupom = new Cupom();
		cupom.setId(11L);
		cupom.setEvento(new Evento());
		return cupom;
	}
}
