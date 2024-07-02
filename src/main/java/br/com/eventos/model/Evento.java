package br.com.eventos.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evento")
@EqualsAndHashCode(of = "id")
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evento_seq_gerador")
	@SequenceGenerator(name = "evento_seq_gerador", sequenceName = "evento_id_seq", allocationSize = 1)
	private Long id;
	
	private String titulo;
	
	private String descricao;
	
	@Column(name = "url_imagem")
	private String urlImagem;
	
	@Column(name = "url_evento")
	private String urlEvento;
	
	private boolean remoto;
	
	private OffsetDateTime data;
	
	@JsonInclude(Include.NON_NULL)
	@OneToOne(mappedBy = "evento", cascade = CascadeType.ALL)
	private Endereco endereco;
}
