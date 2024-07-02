package br.com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eventos.model.Endereco;

@Repository
public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

}
