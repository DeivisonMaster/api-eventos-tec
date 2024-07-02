package br.com.eventos.model.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record EventoDetalhesDTO(
		Long id, 
		String titulo, 
		String descricao, 
		String cidade,
		String uf,
		String urlImagem, 
		String urlEvento, 
		boolean remoto, 
		OffsetDateTime data,
		List<CupomDTO> cupons) {
		
		public record CupomDTO(
			String codigo,
			Integer desconto,
			OffsetDateTime validade
		){
	}
}
