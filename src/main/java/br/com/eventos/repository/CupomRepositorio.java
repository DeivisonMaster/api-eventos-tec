package br.com.eventos.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eventos.model.Cupom;

@Repository	
public interface CupomRepositorio extends JpaRepository<Cupom, Long> {

	List<Cupom> findByEventoIdAndValidadeAfter(Long idEvento, OffsetDateTime offsetDateTime);

}
