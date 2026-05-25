package dao;

import model.NotasFaltas;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotasFaltasDAO { 

    private String calcularStatus(double media, double af) {
        if (media >= 6.0) {
            return "Aprovado";
        } else if (af >= 6.0) {
            return "Aprovado";
        } else if (media >= 4.0) {
            return "AF";
        } else {
            return "Reprovado";
        }
    }

    public List<String> listarDisciplinas() throws Exception {
        List<String> disciplinas = new ArrayList<>();
        String sql = "SELECT nome_disciplina FROM tb_disciplina ORDER BY nome_disciplina";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                disciplinas.add(rs.getString("nome_disciplina"));
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao listar disciplinas: " + e.getMessage());
        }

        return disciplinas;
    }

    public int buscarIdDisciplinaPorNome(String nomeDisciplina) throws Exception {
        String sql = "SELECT id_disciplina FROM tb_disciplina WHERE nome_disciplina = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeDisciplina);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_disciplina");
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar disciplina: " + e.getMessage());
        }

        throw new Exception("Disciplina não encontrada: " + nomeDisciplina);
    }

    public void salvar(NotasFaltas nf) throws Exception {
        String sql = "INSERT INTO tb_notas_faltas " +
                "(rgm, id_disciplina, semestre, a1, a2, media, af, faltas, status_aluno) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String status = nf.getStatusAluno();
        if (status == null || status.trim().isEmpty()) {
            status = calcularStatus(nf.getMedia(), nf.getAf());
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nf.getRgm());
            ps.setInt(2, nf.getIdDisciplina());
            ps.setInt(3, nf.getSemestre());
            ps.setDouble(4, nf.getA1());
            ps.setDouble(5, nf.getA2());
            ps.setDouble(6, nf.getMedia());
            ps.setDouble(7, nf.getAf());
            ps.setInt(8, nf.getFaltas());
            ps.setString(9, status);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Erro ao salvar notas e faltas: " + e.getMessage());
        }
    }

    public void alterar(NotasFaltas nf) throws Exception {
        String sql = "UPDATE tb_notas_faltas " +
                "SET a1 = ?, a2 = ?, media = ?, af = ?, faltas = ?, status_aluno = ? " +
                "WHERE rgm = ? AND id_disciplina = ? AND semestre = ?";

        String status = nf.getStatusAluno();
        if (status == null || status.trim().isEmpty()) {
            status = calcularStatus(nf.getMedia(), nf.getAf());
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nf.getA1());
            ps.setDouble(2, nf.getA2());
            ps.setDouble(3, nf.getMedia());
            ps.setDouble(4, nf.getAf());
            ps.setInt(5, nf.getFaltas());
            ps.setString(6, status);
            ps.setInt(7, nf.getRgm());
            ps.setInt(8, nf.getIdDisciplina());
            ps.setInt(9, nf.getSemestre());

            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                throw new Exception("Registro não encontrado para alterar.");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao alterar notas e faltas: " + e.getMessage());
        }
    }

    public void excluir(int rgm, int idDisciplina, int semestre) throws Exception {
        String sql = "DELETE FROM tb_notas_faltas WHERE rgm = ? AND id_disciplina = ? AND semestre = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rgm);
            ps.setInt(2, idDisciplina);
            ps.setInt(3, semestre);

            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                throw new Exception("Registro não encontrado para excluir.");
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao excluir notas e faltas: " + e.getMessage());
        }
    }

    public NotasFaltas consultar(int rgm, int idDisciplina, int semestre) throws Exception {
        String sql = "SELECT * FROM tb_notas_faltas WHERE rgm = ? AND id_disciplina = ? AND semestre = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rgm);
            ps.setInt(2, idDisciplina);
            ps.setInt(3, semestre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NotasFaltas nf = new NotasFaltas();
                    nf.setRgm(rs.getInt("rgm"));
                    nf.setIdDisciplina(rs.getInt("id_disciplina"));
                    nf.setSemestre(rs.getInt("semestre"));
                    nf.setA1(rs.getDouble("a1"));
                    nf.setA2(rs.getDouble("a2"));
                    nf.setMedia(rs.getDouble("media"));
                    nf.setAf(rs.getDouble("af"));
                    nf.setFaltas(rs.getInt("faltas"));
                    nf.setStatusAluno(rs.getString("status_aluno"));
                    return nf;
                }
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao consultar notas e faltas: " + e.getMessage());
        }

        return null;
    }
}
