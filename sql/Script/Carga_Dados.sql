-- =====================================================
-- 1. CARGA DE DADOS: TABELA UF
-- =====================================================
INSERT INTO tb_uf (uf, nome_estado) VALUES
('SP', 'São Paulo'),
('RJ', 'Rio de Janeiro'),
('MG', 'Minas Gerais'),
('PR', 'Paraná');

-- =====================================================
-- 2. CARGA DE DADOS: TABELA CURSO
-- =====================================================
INSERT INTO tb_curso (nome_curso, campus, periodo_curso) VALUES
('Análise e Desenvolvimento de Sistemas', 'Tatuapé', 'Noturno'),
('Ciência da Computação', 'Tatuapé', 'Matutino'),
('Engenharia de Software', 'Tatuapé', 'Vespertino');

-- =====================================================
-- 3. CARGA DE DADOS: TABELA ALUNO
-- =====================================================
-- id_curso 1 = ADS, 2 = CC, 3 = ES
INSERT INTO tb_aluno (rgm, nome, data_nascimento, cpf, email, endereco, municipio, uf, celular, id_curso) VALUES
(1001, 'Ana Silva', '2002-05-15', '12345678901', 'ana.silva@email.com', 'Rua das Flores, 123', 'São Paulo', 'SP', '11999998888', 1),
(1002, 'Carlos Oliveira', '2001-08-22', '23456789012', 'carlos.o@email.com', 'Av. Paulista, 1500', 'São Paulo', 'SP', '11988887777', 1),
(1003, 'Mariana Souza', '2003-01-10', '34567890123', 'mari.souza@email.com', 'Rua Copacabana, 45', 'Rio de Janeiro', 'RJ', '21977776666', 2),
(1004, 'Lucas Santos', '2000-11-30', '45678901234', 'lucas.santos@email.com', 'Alameda das Américas, 88', 'Curitiba', 'PR', '41966665555', 3);

-- =====================================================
-- 4. CARGA DE DADOS: TABELA DISCIPLINA
-- =====================================================
INSERT INTO tb_disciplina (nome_disciplina, id_curso) VALUES
-- Disciplinas de ADS (id_curso = 1)
('Algoritmos e Programação', 1),
('Bancos de Dados', 1),
-- Disciplinas de Ciência da Computação (id_curso = 2)
('Cálculo Diferencial e Integral', 2),
('Estruturas de Dados', 2),
-- Disciplinas de Engenharia de Software (id_curso = 3)
('Arquitetura de Software', 3);

-- =====================================================
-- 5. CARGA DE DADOS: TABELA NOTAS E FALTAS
-- =====================================================
INSERT INTO tb_notas_faltas (rgm, id_disciplina, semestre, a1, a2, media, af, faltas, status_aluno) VALUES
-- Aluno 1001 (Ana) - Aprovada direto em Algoritmos (id_disciplina = 1)
(1001, 1, 1, 4.50, 4.00, 8.50, NULL, 2, 'Aprovado'),

-- Aluno 1001 (Ana) - Ficou de AF em Banco de Dados (id_disciplina = 2)
(1001, 2, 1, 2.00, 3.00, 5.00, 4.50, 4, 'AF'),

-- Aluno 1002 (Carlos) - Reprovado por falta/nota em Algoritmos (id_disciplina = 1)
(1002, 1, 1, 1.50, 2.00, 3.50, NULL, 22, 'Reprovado'),

-- Aluno 1003 (Mariana) - Aprovada em Cálculo (id_disciplina = 3)
(1003, 3, 1, 5.00, 4.80, 9.80, NULL, 0, 'Aprovado');