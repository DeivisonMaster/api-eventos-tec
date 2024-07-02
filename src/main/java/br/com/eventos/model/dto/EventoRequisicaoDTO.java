package br.com.eventos.model.dto;

import org.springframework.web.multipart.MultipartFile;

public record EventoRequisicaoDTO(String titulo, String data, String descricao, String cidade, String estado, String urlEvento, boolean remoto, MultipartFile imagem) {}
