package br.com.eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.eventos.model.Evento;
import br.com.eventos.model.dto.EventoRequisicaoDTO;
import br.com.eventos.model.dto.EventoRespostaDTO;
import br.com.eventos.model.dto.EventoDetalhesDTO;
import br.com.eventos.service.EventoServico;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
	
	@Autowired
	private EventoServico eventoService;
	
	
	@GetMapping
	public ResponseEntity<List<EventoRespostaDTO>> listar(){
		return ResponseEntity.ok(eventoService.listar());
	}
	
	@GetMapping("/paginado")
	public ResponseEntity<Page<EventoRespostaDTO>> listar(Pageable paginado){
		return ResponseEntity.ok(eventoService.listarPaginado(paginado));
	}
	
	@GetMapping("/eventos-por-vir")
	public ResponseEntity<List<EventoRespostaDTO>> eventosPorVir(){
		return ResponseEntity.ok(eventoService.eventosPorVir());
	}
	
	@PostMapping
	public ResponseEntity<Evento> criaEvento(
			@RequestParam("titulo") String titulo,
			@RequestParam("data") String data,
			@RequestParam("descricao") String descricao,
			@RequestParam("cidade") String cidade,
			@RequestParam("estado") String estado,
			@RequestParam("urlEvento") String urlEvento,
			@RequestParam("remoto") boolean remoto,
			@RequestParam(value = "imagem", required=false) MultipartFile imagem){
		EventoRequisicaoDTO eventoDTO = new EventoRequisicaoDTO(titulo, data, descricao, cidade, estado, urlEvento, remoto, imagem);
		return new ResponseEntity<>(eventoService.criaEvento(eventoDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EventoDetalhesDTO> buscaDetalhesEvento(@PathVariable Long id){	
		return ResponseEntity.ok(eventoService.buscaDetalhesEvento(id));
	}
	
	@GetMapping("/filtro")
	public ResponseEntity<List<Evento>> listarPorFiltro(@RequestParam("uf") String uf,
				@RequestParam("cidade") String cidade,
				@RequestParam("dataFim") String dataFim){
		return ResponseEntity.ok(eventoService.listarPorFiltro(uf, cidade, dataFim));
	}
	
}
