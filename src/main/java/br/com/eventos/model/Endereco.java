package br.com.eventos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "endereco")
@EqualsAndHashCode(of = "id")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq_gerador")
	@SequenceGenerator(name = "endereco_seq_gerador", sequenceName = "endereco_id_seq", allocationSize = 1)
	private Long id;
	
	private String cidade;
	
	private String uf;
	
	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento;
}
