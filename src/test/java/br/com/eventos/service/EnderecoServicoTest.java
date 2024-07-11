package br.com.eventos.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.eventos.model.Endereco;
import br.com.eventos.model.Evento;
import br.com.eventos.model.dto.EventoRequisicaoDTO;
import br.com.eventos.repository.EnderecoRepositorio;

class EnderecoServicoTest {
	
	@Mock
	private EnderecoRepositorio enderecoRepositorio;
	
	@InjectMocks
	private EnderecoServico enderecoServicoMock;
	
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		BDDMockito
			.when(enderecoRepositorio.save(ArgumentMatchers.any(Endereco.class))).thenReturn(geraEndereco());
	}
	
	@Test
	void deveCadastrarNovoEndereco() {
		Endereco endereco = geraEndereco();
		
		Endereco enderecoSalvo = enderecoServicoMock
				.cadastrar(new EventoRequisicaoDTO("frontin-sp", "2022-11-02T20:10:10", "evento", "são paulo", "mg", "www.frontinsp.com.br", false, null), new Evento());
		
		Assertions.assertThat(enderecoSalvo).isNotNull();
		Assertions.assertThat(enderecoSalvo.getCidade()).isEqualTo(endereco.getCidade());
	}

	private Endereco geraEndereco() {
		Endereco endereco = new Endereco();
		endereco.setCidade("Cuiabá");
		endereco.setEvento(new Evento());
		endereco.setUf("MT");
		return endereco;
	}
	
	

}
