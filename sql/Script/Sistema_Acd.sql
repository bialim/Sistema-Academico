-- =====================================================
-- CRIAÇÃO DO BANCO DE DADOS
-- =====================================================

CREATE DATABASE sistema_academico;
USE sistema_academico;

-- =====================================================
-- TABELA DE CURSOS
-- Armazena os cursos disponíveis no sistema
-- =====================================================

CREATE TABLE tb_curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    nome_curso VARCHAR(100) NOT NULL UNIQUE,
    campus VARCHAR(100),
    periodo_curso ENUM('Matutino', 'Vespertino', 'Noturno')
);

-- =====================================================
-- TABELA DE ALUNOS
-- Dados cadastrais dos alunos
-- =====================================================

CREATE TABLE tb_aluno (
    rgm INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    endereco VARCHAR(150),
    municipio VARCHAR(100),
    uf CHAR(2),
    celular VARCHAR(15),
    id_curso INT,

    FOREIGN KEY (id_curso)
    REFERENCES tb_curso(id_curso)
);

-- =====================================================
-- TABELA DE DISCIPLINAS
-- Disciplinas vinculadas aos cursos
-- =====================================================

CREATE TABLE tb_disciplina (
    id_disciplina INT AUTO_INCREMENT PRIMARY KEY,
    nome_disciplina VARCHAR(150) NOT NULL UNIQUE,
    id_curso INT NOT NULL,

    FOREIGN KEY (id_curso)
    REFERENCES tb_curso(id_curso)
);

-- =====================================================
-- TABELA DE NOTAS E FALTAS
-- Relaciona alunos às disciplinas
-- =====================================================

CREATE TABLE tb_notas_faltas (
    id_nota_falta INT AUTO_INCREMENT PRIMARY KEY,
    rgm INT NOT NULL,
    id_disciplina INT NOT NULL,
    semestre INT NOT NULL,
    nota DECIMAL(4,2),
    faltas INT DEFAULT 0,

    FOREIGN KEY (rgm)
    REFERENCES tb_aluno(rgm)
    ON DELETE CASCADE,

    FOREIGN KEY (id_disciplina)
    REFERENCES tb_disciplina(id_disciplina),

    CHECK (nota >= 0 AND nota <= 10),
    CHECK (faltas >= 0)
);