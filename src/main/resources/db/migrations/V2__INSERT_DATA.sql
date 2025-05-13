
INSERT INTO dummy (name)
VALUES
    ('João'),
    ('Maria'),
    ('Pedro');

INSERT INTO estado (nome, sigla) VALUES
                   ('Goiás', 'GO'),
                   ('São Paulo', 'SP'),
                   ('Minas Gerais', 'MG');

INSERT INTO cidade (nome, estado_id) VALUES
                   ('Goiânia', 1),
                   ('São Paulo', 2),
                   ('Belo Horizonte', 3);

INSERT INTO pessoa (nome, cpf, telefone) VALUES
                   ('João da Silva', '123.456.789-00', '(62) 99999-0000'),
                   ('Maria Souza', '987.654.321-00', '(62) 98888-1111'),
                   ('Carlos Pereira', '111.222.333-44', '(62) 97777-2222');

INSERT INTO endereco (rua, estado, cep, pessoa_id, cidade_id) VALUES
                     ('Rua das Flores, 123', 'Goiás', '74000-000', 1, 1),
                     ('Avenida Paulista, 456', 'São Paulo', '01310-000', 2, 2),
                     ('Praça Sete, 789', 'Minas Gerais', '30110-000', 3, 3);

INSERT INTO usuario (email, senha, role, tipo_usuario, pessoa_id) VALUES
                    ('joao.silva@example.com', 'senha123', 'ADMIN', 'Fisioterapeuta', 1),
                    ('maria.souza@example.com', 'senha456', 'USER', 'Paciente', 2),
                    ('carlos.pereira@example.com', 'senha789', 'USER', 'Paciente', 3);

INSERT INTO atendimento (usuario_id, paciente_id, tipo_atendimento, data, descricao) VALUES
                        (1, 2, 'Consulta Inicial', NOW(), 'Consulta para avaliação inicial do paciente.'),
                        (1, 3, 'Reavaliação', NOW(), 'Reavaliação após um mês de tratamento.');

INSERT INTO imagem (url, descricao, tipo_imagem, atendimento_id) VALUES
                   ('https://example.com/imagem1.png', 'Imagem de raio-x', 'Raio-X', 1),
                   ('https://example.com/imagem2.png', 'Imagem de ressonância', 'Ressonância', 2);

INSERT INTO avaliacao (
    atendimento_id
) VALUES
    (1);