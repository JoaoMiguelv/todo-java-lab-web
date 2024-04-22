package com.backend.ToDo.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.ToDo.model.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	boolean existsByNome(String nome);
	
	Optional<Tarefa> findByNomeTarefa(String nome);
}
