-- Adiciona a nova coluna na tabela
ALTER TABLE restaurante ADD aberto TINYINT(1) NOT NULL;

-- Atualiza os restauranes da base de dados deixando todos como fechados
UPDATE restaurante SET aberto = false;