-- Adiciona a nova coluna na tabela
ALTER TABLE restaurante ADD ativo  TINYINT(1) NOT NULL;

-- Atualiza os restauranes da base de dados deixando todos como ativos
UPDATE restaurante SET ativo = true;