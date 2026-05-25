# Sistema Acadêmico

Projeto desenvolvido em **Java** para simular o funcionamento de um **sistema de gerenciamento acadêmico**, utilizando conceitos de **Programação Orientada a Objetos (POO)** e integração com banco de dados **MySQL**.

---

# 📚 Sobre o Projeto

O sistema realiza o controle acadêmico de uma instituição de ensino, sendo capaz de:

- Cadastrar, consultar, alterar e excluir alunos
- Vincular alunos a cursos e campus
- Registrar notas e faltas por disciplina
- Calcular média e situação do aluno automaticamente
- Exibir boletim completo do aluno
- Gerenciar disciplinas e períodos disponíveis

O projeto foi desenvolvido utilizando interface gráfica e conexão com banco de dados via **JDBC**.

---

# 🛠️ Tecnologias Utilizadas

- Java
- Programação Orientada a Objetos
- MySQL
- JDBC
- Lógica de Algoritmos

---

# Funcionalidades

## Cadastro de Alunos
O sistema permite o cadastro completo do aluno contendo:
- RGM, Nome, CPF, Data de Nascimento
- E-mail, Endereço, Município, UF e Celular

---

## Vinculação de Curso
O aluno pode ser vinculado a um curso, campus e período:
- Matutino
- Vespertino
- Noturno

---

## Registro de Notas e Faltas
O sistema registra por disciplina e semestre:
- Nota A1
- Nota A2
- Avaliação Final (AF)
- Quantidade de Faltas

### Cálculo de Situação
| Média | Situação |
|-------|----------|
| Maior ou igual a 6,0 | Aprovado |
| Entre 5,0 e 5,9 | AF |
| Menor que 5,0 | Reprovado |

---

## Boletim do Aluno
Exibe um resumo completo contendo:
- Nome e curso do aluno
- Todas as disciplinas cursadas
- Notas, médias e situação por disciplina

---

# Conceitos Aplicados

- Encapsulamento
- Herança
- Interfaces
- Conexão com banco de dados (JDBC)
- Manipulação de Strings
- Estruturas de decisão
- Estruturas de repetição
- Integração com GUI
- Padrão DAO (Data Access Object)

---
