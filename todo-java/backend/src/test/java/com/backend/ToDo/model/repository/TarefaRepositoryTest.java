package com.backend.ToDo.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.backend.ToDo.model.entity.Tarefa;
import com.backend.ToDo.model.repository.TarefaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TarefaRepositoryTest {

  @Autowired
  TarefaRepository repository;

  @Autowired
  TestEntityManager entityManager;

  @Test
  public void deveVerificarAExistenciaDeUmaTarefa() {
    // Cenário
    Tarefa tarefa = criarTarefa();
    entityManager.persist(tarefa);

    // Ação / Execução
    boolean result = repository.existsByNome("Nome: TESTE");

    // Verificação
    Assertions.assertThat(result).isTrue();
  }

  public static Tarefa criarTarefa() {
    return Tarefa
        .builder()
        .nome("Nome: TESTE")
        .descricao("Descrição: TESTE")
        .observacoes("Observação: TESTE")
        .build();
  }
}
