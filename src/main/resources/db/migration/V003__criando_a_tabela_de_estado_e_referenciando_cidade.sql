CREATE TABLE estado (
	id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    PRIMARY KEY(id)
) engine=InnoDB default charset=utf8;

INSERT INTO estado(nome) SELECT DISTINCT nome_estado FROM cidade;

ALTER TABLE cidade ADD COLUMN estado_id BIGINT NOT NULL;

UPDATE cidade SET estado_id = (SELECT id FROM estado WHERE nome = cidade.nome_estado);

ALTER TABLE cidade ADD CONSTRAINT fk_cidade_estado FOREIGN KEY(estado_id) REFERENCES estado(id);

ALTER TABLE cidade DROP COLUMN nome_estado;

ALTER TABLE cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;