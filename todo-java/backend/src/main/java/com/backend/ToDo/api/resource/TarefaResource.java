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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ToDo.api.dto.TarefaDTO;
import com.backend.ToDo.exception.RegraNegocioException;
import com.backend.ToDo.model.entity.Tarefa;
import com.backend.ToDo.model.enums.StatusTarefa;
import com.backend.ToDo.service.TarefaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/tasks") // Alteração da rota para "api/tasks"
@RequiredArgsConstructor
public class TarefaResource {

  private final TarefaService service;

  @GetMapping
  public ResponseEntity<List<Tarefa>> buscar(
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

  @PostMapping
  public ResponseEntity<Tarefa> salvar(@RequestBody TarefaDTO dto) {
    try {
      Tarefa tarefa = converter(dto);
      tarefa = service.salvar(tarefa);
      return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody TarefaDTO dto) {
    return service.obterPorId(id).map(entity -> {
      try {
        Tarefa tarefa = converter(dto);
        tarefa.setId(entity.getId());
        service.atualizar(tarefa);
        return ResponseEntity.ok(tarefa);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(tarefa -> {
      service.deletar(tarefa);
      return ResponseEntity.noContent().build();
    }).orElse(ResponseEntity.notFound().build());
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
