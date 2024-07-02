package br.com.eventos.model.dto;

import java.time.OffsetDateTime;

public record EventoRespostaDTO(Long id, String titulo, String descricao, OffsetDateTime data, String cidade, String uf, boolean remote, String urlEvento, String urlImagem) {
}
