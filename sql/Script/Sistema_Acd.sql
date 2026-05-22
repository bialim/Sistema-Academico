-- =====================================================
-- CRIAÇÃO DO BANCO DE DADOS
-- =====================================================

CREATE DATABASE sistema_academico;

USE sistema_academico;

-- =====================================================
-- TABELA CURSO
-- =====================================================

CREATE TABLE tb_curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    nome_curso VARCHAR(100) NOT NULL UNIQUE,
    campus VARCHAR(100),
    periodo_curso ENUM('Matutino', 'Vespertino', 'Noturno')
);

-- =====================================================
-- TABELA UF
-- =====================================================

CREATE TABLE tb_uf (
    uf CHAR(2) PRIMARY KEY,
    nome_estado VARCHAR(50) NOT NULL UNIQUE
);

-- =====================================================
-- TABELA ALUNO
-- =====================================================

CREATE TABLE tb_aluno (
    rgm INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    endereco VARCHAR(150),
    municipio VARCHAR(100),
    uf CHAR(2),
    celular VARCHAR(15),
    id_curso INT NOT NULL,

    CONSTRAINT fk_aluno_curso
    FOREIGN KEY (id_curso)
    REFERENCES tb_curso(id_curso),

    CONSTRAINT fk_aluno_uf
    FOREIGN KEY (uf)
    REFERENCES tb_uf(uf)
);

-- =====================================================
-- TABELA DISCIPLINA
-- =====================================================

CREATE TABLE tb_disciplina (
    id_disciplina INT AUTO_INCREMENT PRIMARY KEY,
    nome_disciplina VARCHAR(150) NOT NULL UNIQUE,
    id_curso INT NOT NULL,

    CONSTRAINT fk_disciplina_curso
    FOREIGN KEY (id_curso)
    REFERENCES tb_curso(id_curso)
);

-- =====================================================
-- TABELA NOTAS E FALTAS
-- =====================================================

CREATE TABLE tb_notas_faltas (
    id_nota_falta INT AUTO_INCREMENT PRIMARY KEY,
    rgm INT NOT NULL,
    id_disciplina INT NOT NULL,
    semestre INT NOT NULL,

    -- Notas
    a1 DECIMAL(3,2),
    a2 DECIMAL(3,2),
    media DECIMAL(4,2),
    af DECIMAL(3,2),

    -- Frequência e status
    faltas INT DEFAULT 0,
    status_aluno ENUM('Aprovado', 'AF', 'Reprovado'),

    -- Foreign Keys
    CONSTRAINT fk_notas_aluno
    FOREIGN KEY (rgm)
    REFERENCES tb_aluno(rgm)
    ON DELETE CASCADE,

    CONSTRAINT fk_notas_disciplina
    FOREIGN KEY (id_disciplina)
    REFERENCES tb_disciplina(id_disciplina),

    -- Checks
    CONSTRAINT chk_a1
    CHECK (a1 >= 0 AND a1 <= 5),

    CONSTRAINT chk_a2
    CHECK (a2 >= 0 AND a2 <= 5),

    CONSTRAINT chk_media
    CHECK (media >= 0 AND media <= 10),

    CONSTRAINT chk_af
    CHECK (af >= 0 AND af <= 5),

    CONSTRAINT chk_faltas
    CHECK (faltas >= 0),

    -- Não permite disciplina repetida
    CONSTRAINT uq_aluno_disciplina_semestre
    UNIQUE (rgm, id_disciplina, semestre)
);