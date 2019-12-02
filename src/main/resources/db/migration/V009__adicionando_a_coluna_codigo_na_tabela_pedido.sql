-- Adicionando a nova coluna para representar o UUID do pedido
ALTER TABLE pedido ADD codigo VARCHAR(36) NOT NULL AFTER id;

-- Gerando um UUID para os pedidos já existentes na base de dados
UPDATE pedido SET codigo = uuid();

-- Adicionando a restrição na coluna codigo para não permitir valores duplicados
ALTER TABLE pedido ADD CONSTRAINT UK_PEDIDO_CODIGO UNIQUE(codigo);