package com.backend.ToDo.api.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ToDo.api.dto.TarefaDTO;
import com.backend.ToDo.exception.RegraNegocioException;
import com.backend.ToDo.model.entity.Tarefa;
import com.backend.ToDo.model.enums.StatusTarefa;
import com.backend.ToDo.service.TarefaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/tarefa")
@RequiredArgsConstructor
public class TarefaResource {

  private final TarefaService service;

  @PostMapping
  public ResponseEntity salvar(@RequestBody TarefaDTO dto) {
    try {
      Tarefa entidade = converter(dto);
      entidade = service.salvar(entidade);
      return new ResponseEntity(entidade, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TarefaDTO dto) {
    return service.obterPorId(id).map(entity -> {
      try {
        Tarefa tarefa = converter(dto);
        tarefa.setId(entity.getId());
        service.atualizar(tarefa);
        return ResponseEntity.ok(tarefa);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElseGet(() -> new ResponseEntity("Nenhuma tarefa encontrada!", HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("{id}")
  public ResponseEntity deletar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(entidade -> {
      service.deletar(entidade);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> new ResponseEntity("Nenhuma tarefa encontrada!", HttpStatus.BAD_REQUEST));
  }

  @GetMapping
  public ResponseEntity buscar(
      @RequestParam(value = "nome", required = false) String nome,
      @RequestParam(value = "descricao", required = false) String descricao,
      @RequestParam(value = "observacoes", required = false) String observacoes) {
    Tarefa tarefaFiltro = new Tarefa();
    tarefaFiltro.setNome(nome);
    tarefaFiltro.setDescricao(descricao);
    tarefaFiltro.setObservacoes(observacoes);

    List<Tarefa> tarefas = service.buscar(tarefaFiltro);
    return ResponseEntity.ok(tarefas);
  }

  private Tarefa converter(TarefaDTO dto) {
    Tarefa tarefa = new Tarefa();

    tarefa.setId(dto.getId());
    tarefa.setNome(dto.getNome());
    tarefa.setDescricao(dto.getDescricao());
    tarefa.setObservacoes(dto.getObservacoes());
    tarefa.setDataAtualizacao(dto.getDataAtualizacao());
    tarefa.setStatus(StatusTarefa.valueOf(dto.getStatus()));

    return tarefa;
  }

}
