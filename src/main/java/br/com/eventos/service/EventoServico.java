package br.com.eventos.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.AmazonS3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.eventos.model.Cupom;
import br.com.eventos.model.Evento;
import br.com.eventos.model.dto.EventoDetalhesDTO;
import br.com.eventos.model.dto.EventoRequisicaoDTO;
import br.com.eventos.model.dto.EventoRespostaDTO;
import br.com.eventos.repository.EventoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventoServico {
	
	@Value("${aws.bucket.name}")
	private String bucketName;
	
	@Autowired
	private EventoRepositorio eventoRepositorio;
	
	@Autowired
	private AmazonS3 amazonS3Client;
	
	@Autowired
	private EnderecoServico enderecoServico;
	
	@Autowired
	private CupomServico cupomServico;
	
	
	
	public Evento criaEvento(EventoRequisicaoDTO eventoDTO) {
		String imgUrl = null;
		
		if(Objects.nonNull(eventoDTO.imagem())) {
			imgUrl = this.uploadImagem(eventoDTO.imagem());
		}
		
		Evento evento = new Evento();
		evento.setTitulo(eventoDTO.titulo());
		evento.setData(obtemDataParaEvento(eventoDTO));
		evento.setDescricao(eventoDTO.descricao());
		evento.setRemoto(eventoDTO.remoto());
		evento.setUrlEvento(eventoDTO.urlEvento());
		evento.setUrlImagem(imgUrl);
		
		eventoRepositorio.save(evento);
		
		if(!eventoDTO.remoto()) {
			this.enderecoServico.cadastrar(eventoDTO, evento);
		}
		
		return evento;
	}

	private OffsetDateTime obtemDataParaEvento(EventoRequisicaoDTO eventoDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime datetime = LocalDateTime.parse(eventoDTO.data(), formatter);
		ZoneOffset offset = ZoneId.of("UTC").getRules().getOffset(datetime);
		return OffsetDateTime.of(datetime , offset);
	}

	private String uploadImagem(MultipartFile imagem) {
		String nome = UUID.randomUUID() + " - " + imagem.getOriginalFilename();
		
		try {
			File arquivo = this.conversorArquivo(imagem);
			amazonS3Client.putObject(bucketName, nome, arquivo);
			arquivo.delete();
			
			return amazonS3Client.getUrl(bucketName, nome).toString();
		} catch (IOException e) {
			log.error("Erro ao realizar Upload do Arquivo", e);
			return null;
		}
	}

	private File conversorArquivo(MultipartFile imagem) throws IOException {
		File conversor = new File(imagem.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(conversor);
		fos.write(imagem.getBytes());
		fos.close();
		
		return conversor;
	}

	public List<EventoRespostaDTO> listar() {
		List<Evento> eventos = this.eventoRepositorio.findAll();
		return eventos.stream()
				.map(
					evento -> new EventoRespostaDTO(evento.getId(), 
					evento.getTitulo(), 
					evento.getDescricao(), 
					evento.getData(), 
					Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getUf() : "", 
					Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getCidade() : "", 
					evento.isRemoto(), 
					evento.getUrlEvento(), 
					evento.getUrlImagem()))
				.collect(Collectors.toList());
	}

	public Page<EventoRespostaDTO> listarPaginado(Pageable paginado) {
		Page<Evento> eventos = this.eventoRepositorio.findAll(paginado);
		return eventos.map(evento -> new EventoRespostaDTO(
					evento.getId(), 
					evento.getTitulo(), 
					evento.getDescricao(), 
					evento.getData(), 
					Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getUf() : "", 
					Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getCidade() : "", 
					evento.isRemoto(), 
					evento.getUrlEvento(), 
					evento.getUrlImagem())
				);
	}

	public List<EventoRespostaDTO> eventosPorVir() {
		List<Evento> eventos = this.eventoRepositorio.eventosPorVir(OffsetDateTime.now());
		return eventos.stream()
				.map(
					evento -> new EventoRespostaDTO(evento.getId(), 
					evento.getTitulo(), 
					evento.getDescricao(), 
					evento.getData(), 
					Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getUf() : "", 
					Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getCidade() : "", 
					evento.isRemoto(), 
					evento.getUrlEvento(), 
					evento.getUrlImagem()))
				.collect(Collectors.toList());
	}

	public EventoDetalhesDTO buscaDetalhesEvento(Long id) {
		Evento evento = this.eventoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Evento não encontrado!"));
		
		List<Cupom> cupons = this.cupomServico.buscaCuponsValidos(evento.getId(), OffsetDateTime.now());
		
		List<EventoDetalhesDTO.CupomDTO> cuponsDTO = cupons.stream()
				.map(cupom -> new EventoDetalhesDTO.CupomDTO(
						cupom.getCodigo(), 
						cupom.getDesconto(), 
						cupom.getValidade()))
				.collect(Collectors.toList());
		
		return new EventoDetalhesDTO( 
				evento.getId(), 
				evento.getTitulo(), 
				evento.getDescricao(), 
				Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getCidade() : "",
				Objects.nonNull(evento.getEndereco()) ? evento.getEndereco().getUf() : "",
				evento.getUrlImagem(), 
				evento.getUrlEvento(),
				evento.isRemoto(),
				evento.getData(),
				cuponsDTO);
	}

}
