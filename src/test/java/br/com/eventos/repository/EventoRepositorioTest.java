package br.com.eventos.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.eventos.model.Evento;
import br.com.eventos.util.CriadorEventos;

@DataJpaTest
@ActiveProfiles("test")
class EventoRepositorioTest {
	
	@Autowired
	private EventoRepositorio eventoRepositorio;

	
	@Test
	@DisplayName("Deve listar eventos por vir")
	void deveListarEventosApartirDaDataAtual() {
		OffsetDateTime dataAtual = OffsetDateTime.now();

		Evento evento = new CriadorEventos().criadorDeEventoPor(dataAtual);
		eventoRepositorio.save(evento);
		
		List<Evento> eventos = eventoRepositorio.eventosPorVir(dataAtual);
		
		assertThat(eventos.size()).isPositive();
		assertThat(eventos.isEmpty()).isFalse();
		assertThat(eventos.contains(evento)).isTrue();
	}
	
//	@Test
//	@DisplayName("Não Deve listar eventos por vir")
//	void naoDeveListarEventosSeDataForInferiorADataAtual() {
//		DateTimeFormatter formata = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//		LocalDateTime data = LocalDateTime.parse("2022-11-02T20:10:10", formata);
//		OffsetDateTime dataPassada = OffsetDateTime.of(data, ZoneOffset.of("UTC").getRules().getOffset(data));
//		
//		Evento evento = new CriadorEventos().criadorDeEventoPor(dataPassada);
//		eventoRepositorio.save(evento);
//		
//		List<Evento> eventos = eventoRepositorio.eventosPorVir(dataPassada);
//		
//		assertThat(eventos.size()).isEqualTo(0);
//		assertThat(eventos.isEmpty()).isTrue();
//		assertThat(eventos.contains(evento)).isFalse();
//	}

}
