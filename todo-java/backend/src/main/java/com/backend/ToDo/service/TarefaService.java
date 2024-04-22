package com.backend.ToDo.service;

import java.util.List;
import java.util.Optional;

import com.backend.ToDo.model.entity.Tarefa;
import com.backend.ToDo.model.enums.StatusTarefa;

public interface TarefaService {
	
	Tarefa salvar(Tarefa tarefa);
	
	Tarefa atualizar(Tarefa tarefa);
	
	void deletar(Tarefa tarefa);
	
	List<Tarefa> buscar(Tarefa tarfa);
	
	void atualizarStatus(Tarefa tarefa, StatusTarefa status);

	void validar(Tarefa tarefa);
	
	Optional<Tarefa> obterPorId(Long id);
}
