package br.com.eventos.model;

import java.time.OffsetDateTime;

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
@Table(name = "cupom")
@EqualsAndHashCode(of = "id")
public class Cupom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cupom_seq_gerador")
	@SequenceGenerator(name = "cupom_seq_gerador", sequenceName = "cupom_id_seq", allocationSize = 1)
	private Long id;
	
	private String codigo;
	
	private Integer desconto;
	
	private OffsetDateTime validade;
	
	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento;
}
