package com.backend.ToDo.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TarefaDTO {
	private Long id;
	private String nome;
	private String descricao;
	private String observacoes;
	private String status;
	private LocalDate dataAtualizacao;
}
