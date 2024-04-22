package com.backend.ToDo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.ToDo.exception.RegraNegocioException;
import com.backend.ToDo.model.entity.Tarefa;
import com.backend.ToDo.model.enums.StatusTarefa;
import com.backend.ToDo.model.repository.TarefaRepository;
import com.backend.ToDo.service.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService{
	
	private TarefaRepository repository;
	
	public TarefaServiceImpl(TarefaRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Tarefa salvar(Tarefa tarefa) {
		validar(tarefa);
		tarefa.setStatus(StatusTarefa.PENDENTE);
		return repository.save(tarefa);
	}

	@Override
	@Transactional
	public Tarefa atualizar(Tarefa tarefa) {
		Objects.requireNonNull(tarefa.getId());
		validar(tarefa);
		return repository.save(tarefa);
	}

	@Override
	@Transactional
	public void deletar(Tarefa tarefa) {
		Objects.requireNonNull(tarefa.getId());
		repository.delete(tarefa);
	}
	
	@Override
	public void validar(Tarefa tarefa) {
		if(tarefa.getNome().isEmpty()) {
			throw new RegraNegocioException("Insira um nome válido.");
		}
		
		if(tarefa.getDescricao().isEmpty()) {
			throw new RegraNegocioException("Insira uma descrição válida.");
		}
		
		if(tarefa.getObservacoes().isEmpty()) {
			throw new RegraNegocioException("Insira uma observação válida.");
		}
	}

	@Override
	public void atualizarStatus(Tarefa tarefa, StatusTarefa status) {
		tarefa.setStatus(status);
		atualizar(tarefa);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tarefa> buscar(Tarefa tarefa) {
		Example example = Example.of(tarefa,
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public Optional<Tarefa> obterPorId(Long id) {
		return repository.findById(id);
	}

}
