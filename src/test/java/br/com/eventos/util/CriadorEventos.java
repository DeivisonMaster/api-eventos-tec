package br.com.eventos.util;

import java.time.OffsetDateTime;

import br.com.eventos.model.Evento;

public class CriadorEventos {
	
	public Evento criadorDeEventoPor(OffsetDateTime dataAtual) {
		Evento evento = new Evento();
		
		evento.setData(dataAtual);
		evento.setDescricao("evento front-end");
		evento.setEndereco(null);
		evento.setRemoto(false);
		evento.setTitulo("frontin-sampa");
		evento.setUrlEvento("");
		evento.setUrlImagem("");
		return evento;
	}
}
