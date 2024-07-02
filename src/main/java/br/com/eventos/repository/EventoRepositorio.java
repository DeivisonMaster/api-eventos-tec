package br.com.eventos.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.eventos.model.Evento;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {

	@Query("select e from Evento e where e.data >= :dataAtual")
	List<Evento> eventosPorVir(@Param("dataAtual") OffsetDateTime dataAtual);

}
