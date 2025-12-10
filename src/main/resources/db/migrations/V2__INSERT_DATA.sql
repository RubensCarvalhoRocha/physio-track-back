
INSERT INTO dummy (name)
VALUES
    ('João'),
    ('Maria'),
    ('Pedro');

INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Alagoas', 'AL');
INSERT INTO estado (nome, sigla) VALUES ('Amapá', 'AP');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Bahia', 'BA');
INSERT INTO estado (nome, sigla) VALUES ('Ceará', 'CE');
INSERT INTO estado (nome, sigla) VALUES ('Distrito Federal', 'DF');
INSERT INTO estado (nome, sigla) VALUES ('Espírito Santo', 'ES');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Maranhão', 'MA');
INSERT INTO estado (nome, sigla) VALUES ('Mato Grosso', 'MT');
INSERT INTO estado (nome, sigla) VALUES ('Mato Grosso do Sul', 'MS');
INSERT INTO estado (nome, sigla) VALUES ('Minas Gerais', 'MG');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Paraíba', 'PB');
INSERT INTO estado (nome, sigla) VALUES ('Paraná', 'PR');
INSERT INTO estado (nome, sigla) VALUES ('Pernambuco', 'PE');
INSERT INTO estado (nome, sigla) VALUES ('Piauí', 'PI');
INSERT INTO estado (nome, sigla) VALUES ('Rio de Janeiro', 'RJ');
INSERT INTO estado (nome, sigla) VALUES ('Rio Grande do Norte', 'RN');
INSERT INTO estado (nome, sigla) VALUES ('Rio Grande do Sul', 'RS');
INSERT INTO estado (nome, sigla) VALUES ('Rondônia', 'RO');
INSERT INTO estado (nome, sigla) VALUES ('Roraima', 'RR');
INSERT INTO estado (nome, sigla) VALUES ('Santa Catarina', 'SC');
INSERT INTO estado (nome, sigla) VALUES ('São Paulo', 'SP');
INSERT INTO estado (nome, sigla) VALUES ('Sergipe', 'SE');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');


INSERT INTO cidade (nome, estado_id) VALUES
                   ('Goiânia', 1),
                   ('São Paulo', 2),
                   ('Belo Horizonte', 3);

INSERT INTO endereco (rua, estado, cep, cidade_id) VALUES
                     ('Rua das Flores, 123', 'Goiás', '74000-000',1),
                     ('Avenida Paulista, 456', 'São Paulo', '01310-000',2),
                     ('Praça Sete, 789', 'Minas Gerais', '30110-000', 3);


INSERT INTO pessoa (nome, cpf, telefone, endereco_id) VALUES
                    ('João da Silva', '123.456.789-00', '(62) 99999-0000',1),
                    ('Maria Souza', '987.654.321-00', '(62) 98888-1111',2),
                    ('Carlos Pereira', '111.222.333-44', '(62) 97777-2222',3);

INSERT INTO usuario (email, senha, role, tipo_usuario, pessoa_id) VALUES
                    ('joao.silva@example.com', '$2a$10$AUBNRWyAPiUg2U8NDt.tLeSkK507FD5kWBy0dzb8.2R6ZKu.ro/n.', 'ADMIN', 'Fisioterapeuta', 1),
                    ('maria.souza@example.com', '$2a$10$AUBNRWyAPiUg2U8NDt.tLeSkK507FD5kWBy0dzb8.2R6ZKu.ro/n.', 'USER', 'Fisioterapeuta', 2),
                    ('carlos.pereira@example.com', '$2a$10$AUBNRWyAPiUg2U8NDt.tLeSkK507FD5kWBy0dzb8.2R6ZKu.ro/n.', 'USER', 'Fisioterapeuta', 3);

INSERT INTO atendimento (usuario_id, paciente_id, tipo_atendimento, data_atendimento, descricao) VALUES
                        (1, 2, 'Consulta Inicial', NOW(), 'Consulta para avaliação inicial do paciente.'),
                        (1, 2, 'Consulta de acompanhamento', NOW(), 'Consulta para acompanhamento do paciente.'),
                        (1, 3, 'Reavaliação', NOW(), 'Reavaliação após um mês de tratamento.');

INSERT INTO imagem (url, descricao, tipo_imagem, atendimento_id) VALUES
                   ('https://example.com/imagem1.png', 'Imagem de raio-x', 'Raio-X', 1),
                   ('https://example.com/imagem2.png', 'Imagem de ressonância', 'Ressonância', 2);


INSERT INTO avaliacao (
    atendimento_id, data, altura, peso, imc, esporte, queixas, historico_saude, medicamentos,
    cirurgia, tratamento_anterior, exame_imagem, diagnostico_medico, obj_tratamento, obs_gerais,

    perimetria_medida1d, perimetria_medida2d, perimetria_medida3d, perimetria_panturrilhad,
    perimetria_assimetria_medida1e, perimetria_assimetria_medida2e, perimetria_assimetria_medida3e, perimetria_panturrilhae,

    perimetria_medida_ass1, perimetria_medida_ass2, perimetria_medida_ass3, perimetria_panturrilha_ass,

    lunged, lungee, lunge_ass,

    rot_quad_interd, rot_quad_intere, rot_quad_inter_ass,
    rot_quad_exterd, rot_quad_extere, rot_quad_exter_ass,

    ext_joelhod, ext_joelhoe, ext_joelho_ass,
    flex_joelhod, flex_joelhoe, flex_joelho_ass,

    sh_test1d, sh_test2d, sh_test3d, sh_test_mediad,
    sh_test1e, sh_test2e, sh_test3e, sh_test_mediae,

    sh_test_score, slb_testd, slb_teste
) VALUES (
             1, '2025-06-15', 1.75, 72.0, 23.5, 'Futebol', 'Dor media no joelho ao correr', 'Nenhum problema sério prévio', 'Ibuprofeno',
             'Meniscectomia em 2022', 'Fisioterapia em 2023', 'Ressonância magnética do joelho', 'Lesão meniscal medial', 'Recuperar mobilidade e reduzir dor', 'Paciente ativo e motivado',

             45.0, 44.5, 45.2, 38.0,
             2.0, 1.5, 1.8, 36.5,

             43.0, 42.8, 43.5, 37.0,

             30.0, 29.5, 29.8,

             10.0, 10.5, 10.2,
             12.0, 11.5, 11.8,

             5.0, 5.2, 5.1,
             6.0, 6.2, 6.1,

             8.0, 8.5, 8.2, 8.23,
             7.5, 7.8, 7.9, 7.73,

             7.98, 12.0, 11.5
         );

 INSERT INTO avaliacao (
      atendimento_id, data, altura, peso, imc, esporte, queixas, historico_saude, medicamentos,
      cirurgia, tratamento_anterior, exame_imagem, diagnostico_medico, obj_tratamento, obs_gerais,

      perimetria_medida1d, perimetria_medida2d, perimetria_medida3d, perimetria_panturrilhad,
      perimetria_assimetria_medida1e, perimetria_assimetria_medida2e, perimetria_assimetria_medida3e, perimetria_panturrilhae,

      perimetria_medida_ass1, perimetria_medida_ass2, perimetria_medida_ass3, perimetria_panturrilha_ass,

      lunged, lungee, lunge_ass,

      rot_quad_interd, rot_quad_intere, rot_quad_inter_ass,
      rot_quad_exterd, rot_quad_extere, rot_quad_exter_ass,

      ext_joelhod, ext_joelhoe, ext_joelho_ass,
      flex_joelhod, flex_joelhoe, flex_joelho_ass,

      sh_test1d, sh_test2d, sh_test3d, sh_test_mediad,
      sh_test1e, sh_test2e, sh_test3e, sh_test_mediae,

      sh_test_score, slb_testd, slb_teste
  ) VALUES (
               2, '2025-09-15', 1.75, 70.0, 22.8, 'Futebol', 'Sem dor ao correr, apenas leve desconforto ocasional', 'Recuperado da cirurgia, sem limitações funcionais', 'sem uso de remédio',
               'Meniscectomia em 2022', 'Fisioterapia concluída com sucesso', 'Ressonância mostra joelho sem inflamação', 'Recuperação completa da lesão meniscal', 'Aprimorar força e desempenho físico', 'Paciente evoluiu bem, sem queixas atuais',

               46.5, 45.0, 45.5, 38.5,
               1.0, 0.8, 0.9, 37.5,

               44.0, 43.8, 44.2, 37.8,

               33.0, 32.8, 32.9,

               12.0, 12.2, 12.1,
               13.5, 13.2, 13.4,

               6.0, 6.1, 6.05,
               7.0, 7.1, 7.05,

               9.0, 9.2, 9.1, 9.1,
               8.8, 8.9, 9.0, 8.9,

               8.95, 14.0, 13.8
           );

           INSERT INTO avaliacao (
               atendimento_id, data, altura, peso, imc, esporte, queixas, historico_saude, medicamentos,
               cirurgia, tratamento_anterior, exame_imagem, diagnostico_medico, obj_tratamento, obs_gerais,

               perimetria_medida1d, perimetria_medida2d, perimetria_medida3d, perimetria_panturrilhad,
               perimetria_assimetria_medida1e, perimetria_assimetria_medida2e, perimetria_assimetria_medida3e, perimetria_panturrilhae,
               perimetria_medida_ass1, perimetria_medida_ass2, perimetria_medida_ass3, perimetria_panturrilha_ass,

               lunged, lungee, lunge_ass,
               lunged_2, lungee_2, lunge_ass_2,

               rot_quad_interd, rot_quad_intere, rot_quad_inter_ass,
               rot_quad_exterd, rot_quad_extere, rot_quad_exter_ass,

               ext_joelhod, ext_joelhoe, ext_joelho_ass,
               flex_joelhod, flex_joelhoe, flex_joelho_ass,

               sh_test1d, sh_test2d, sh_test3d, sh_test_mediad,
               sh_test1e, sh_test2e, sh_test3e, sh_test_mediae,

               sdh_test1d, sdh_test2d, sdh_test3d, sdh_test_mediad,
               sdh_test1e, sdh_test2e, sdh_test3e, sdh_test_mediae,

               sh_test_score, slb_testd, slb_teste,
               observacao
           )
           VALUES (
               2, '2025-12-15', 1.78, 82.5, 26.0, 'musculação e Futebol', 'dor lombar leve', 'Recuperado da cirurgia, sem limitações funcionais',
               'nenhum', 'apendicectomia em 2025', 'fisioterapia em 2023', 'RX lombar normal', 'disfunção postural',
               'melhorar mobilidade e reduzir dor', 'paciente colaborativo e ativo',

               52.3, 54.1, 55.8, 36.2,
               51.8, 53.7, 55.1, 35.9,
               0.5, 0.4, 0.7, 0.3,

               42.1, 43.5, 1.4,
               43.0, 44.2, 1.2,

               35.2, 36.1, 0.9,
               28.5, 29.4, 0.9,

               120.5, 118.3, 2.2,
               102.1, 100.8, 1.3,

               11.2, 11.5, 11.1, 11.27,
               10.9, 10.7, 11.0, 10.87,

               29.4, 30.1, 29.8, 29.77,
               28.7, 28.1, 28.9, 28.57,

               58.3, 31.2, 29.9,
               '3 avaliacao sem intercorrências'
           );



