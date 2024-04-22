package com.backend.ToDo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void givenPersistedTarefa_whenExistsByNome_thenTrue() {
        // Arrange
        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);

        // Act
        boolean exists = repository.existsByNome(TarefaTestHelper.NOME_TESTE);

        // Assert
        assertThat(exists).isTrue();
    }

    private Tarefa criarTarefa() {
        return Tarefa.builder()
                     .nome(TarefaTestHelper.NOME_TESTE)
                     .descricao(TarefaTestHelper.DESCRICAO_TESTE)
                     .observacoes(TarefaTestHelper.OBSERVACAO_TESTE)
                     .build();
    }
}
