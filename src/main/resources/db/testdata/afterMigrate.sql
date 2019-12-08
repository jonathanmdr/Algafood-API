SET foreign_key_checks = 0;

TRUNCATE TABLE cidade;
TRUNCATE TABLE cozinha;
TRUNCATE TABLE estado;
TRUNCATE TABLE forma_pagamento;
TRUNCATE TABLE grupo;
TRUNCATE TABLE grupo_permissao;
TRUNCATE TABLE permissao;
TRUNCATE TABLE produto;
TRUNCATE TABLE restaurante;
TRUNCATE TABLE restaurante_forma_pagamento;
TRUNCATE TABLE usuario;
TRUNCATE TABLE usuario_grupo;
TRUNCATE TABLE restaurante_usuario_responsavel;
TRUNCATE TABLE pedido;
TRUNCATE TABLE item_pedido;

SET foreign_key_checks = 1;

ALTER TABLE cidade auto_increment = 1;
ALTER TABLE cozinha auto_increment = 1;
ALTER TABLE estado auto_increment = 1;
ALTER TABLE forma_pagamento auto_increment = 1;
ALTER TABLE grupo auto_increment = 1;
ALTER TABLE permissao auto_increment = 1;
ALTER TABLE produto auto_increment = 1;
ALTER TABLE restaurante auto_increment = 1;
ALTER TABLE usuario auto_increment = 1;
ALTER TABLE pedido auto_increment = 1;
ALTER TABLE item_pedido auto_increment = 1;

INSERT INTO cozinha(id, nome) VALUES(1, 'Tailandesa'),
									(2, 'Indiana'),
									(3, 'Argentina'),
									(4, 'Brasileira');

INSERT INTO estado(id, nome) VALUES(1, 'Minas Gerais'),
								   (2, 'São Paulo'),
								   (3, 'Ceará');

INSERT INTO cidade(id, nome, estado_id) VALUES(1, 'Uberlândia', 1),
											  (2, 'Belo Horizonte', 1),
											  (3, 'São Paulo', 2),
											  (4, 'Campinas', 2),
											  (5, 'Fortaleza', 3);

INSERT INTO restaurante(id, nome, taxa_frete, cozinha_id, data_criacao, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, ativo, aberto) VALUES(1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', true, true);
INSERT INTO restaurante(id, nome, taxa_frete, cozinha_id, data_criacao, data_atualizacao, ativo, aberto) VALUES(2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true),
																											   (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true),
																											   (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true),
																											   (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true),
																											   (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

INSERT INTO forma_pagamento(id, nome) VALUES(1, 'Cartão de crédito'),
											(2, 'Cartão de débito'),
											(3, 'Dinheiro');

INSERT INTO permissao(id, nome, descricao) VALUES(1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
												 (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) VALUES(1, 1),
																				  (1, 2),
																				  (1, 3),
																				  (2, 3),
																				  (3, 2),
																				  (3, 3),
																				  (4, 1),
																				  (4, 2),
																				  (5, 1),
																				  (5, 2),
																				  (6, 3);

INSERT INTO produto(nome, descricao, preco, ativo, restaurante_id) VALUES('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1),
																		 ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

INSERT INTO produto(nome, descricao, preco, ativo, restaurante_id) VALUES('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

INSERT INTO produto(nome, descricao, preco, ativo, restaurante_id) VALUES('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3),
																		 ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

INSERT INTO produto(nome, descricao, preco, ativo, restaurante_id) VALUES('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4),
																		 ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

INSERT INTO produto(nome, descricao, preco, ativo, restaurante_id) VALUES('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

INSERT INTO produto(nome, descricao, preco, ativo, restaurante_id) VALUES('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

INSERT INTO grupo(nome) VALUES('Gerente'), 
							  ('Vendedor'), 
							  ('Secretária'), 
							  ('Cadastrador');
							  
INSERT INTO grupo_permissao(grupo_id, permissao_id) VALUES(1, 1), 
														  (1, 2), 
														  (2, 1), 
														  (2, 2), 
														  (3, 1); 							  
							  
INSERT INTO usuario(id, nome, email, senha, data_criacao) VALUES(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
																(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
																(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
																(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp);
																
INSERT INTO usuario_grupo(usuario_id, grupo_id) VALUES(1, 1), 
													  (1, 2), 
													  (2, 2);
													  
INSERT INTO restaurante_usuario_responsavel(restaurante_id, usuario_id) VALUES(5, 1), 
																			  (5, 3);
-- Pedido 1																			  
INSERT INTO pedido(id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES(1, '2cb92fca-52cf-4463-a133-fba2f52006e6', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CREATED', utc_timestamp, 298.90, 10, 308.90);

INSERT INTO item_pedido(id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES(1, 1, 1, 1, 78.9, 78.9, null),
																											  (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

-- Pedido 2
INSERT INTO pedido(id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES(2, 'e106c2e9-e613-4728-9f13-6c06d1a4eb58', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CREATED', utc_timestamp, 79, 0, 79);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES(3, 2, 6, 1, 79, 79, 'Ao ponto');

-- Pedido 3
INSERT INTO pedido(id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
VALUES(3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil', 'DELIVERED', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

INSERT INTO item_pedido(id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES(4, 3, 2, 1, 110, 110, null);

-- Pedido 4
INSERT INTO pedido(id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
VALUES(4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro', 'DELIVERED', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

INSERT INTO item_pedido(id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES(5, 4, 3, 2, 87.2, 174.4, null);

-- Pedido 5
INSERT INTO pedido(id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
VALUES(5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins', 'DELIVERED', '2019-11-02 21:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2);

INSERT INTO item_pedido(id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) VALUES(6, 5, 3, 1, 87.2, 87.2, null);











