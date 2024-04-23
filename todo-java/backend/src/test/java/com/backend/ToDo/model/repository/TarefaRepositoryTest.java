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
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
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
    public void givenPersistedTarefa_whenDeleted_thenNotExists() {
        // Arrange
        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);
    
        // Act
        repository.delete(tarefa);
    
        // Assert
        assertThat(repository.existsByNome(TarefaTestHelper.NOME_TESTE)).isFalse();
    }

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

    @Test
    private Tarefa createTask() {
        return Tarefa.builder()
                     .nome(TarefaTestHelper.NOME_TESTE)
                     .descricao(TarefaTestHelper.DESCRICAO_TESTE)
                     .observacoes(TarefaTestHelper.OBSERVACAO_TESTE)
                     .build();
    }

// Teste para verificar se é possível encontrar uma tarefa por seu ID
    @Test
    public void givenPersistedTarefa_whenFindById_thenTarefaExists() {
        // Arrange
        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);
    
        // Act
        Tarefa foundTarefa = repository.findById(tarefa.getId()).orElse(null);
    
        // Assert
        assertNotNull(foundTarefa);
        assertEquals(tarefa.getId(), foundTarefa.getId());
        assertEquals(tarefa.getNome(), foundTarefa.getNome());
        // Adicione mais asserções conforme necessário para verificar outros campos
    }

// Teste para verificar se é possível encontrar todas as tarefas no repositório
    @Test
    public void givenPersistedTarefas_whenFindAll_thenAllTarefasExist() {
        // Arrange
        Tarefa tarefa1 = criarTarefa();
        Tarefa tarefa2 = criarTarefa();
        entityManager.persist(tarefa1);
        entityManager.persist(tarefa2);
    
        // Act
        List<Tarefa> tarefas = repository.findAll();
    
        // Assert
        assertEquals(2, tarefas.size());
        // Verifique se todas as tarefas persistidas estão presentes na lista
        assertTrue(tarefas.contains(tarefa1));
        assertTrue(tarefas.contains(tarefa2));
    }

}
