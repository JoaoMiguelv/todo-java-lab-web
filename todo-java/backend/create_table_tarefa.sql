CREATE SCHEMA tarefa;

CREATE TABLE tarefa.tarefa
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	descricao character varying(150),
	status character varying(20) CHECK (status IN('CONCLUIDO', 'PENDENTE')) NOT NULL,
	observacoes character varying(150),
	data_criacao date DEFAULT NOW(),
	data_atualizacao date DEFAULT NOW()
)

SELECT * FROM tarefa.tarefa