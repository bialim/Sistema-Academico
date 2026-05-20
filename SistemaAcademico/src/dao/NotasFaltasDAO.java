package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.ConnectionFactory; 
import model.NotasFaltas;

public class NotasFaltasDAO {

    // MÉTODO SALVAR
    public void salvar(NotasFaltas notasFaltas) throws Exception {
        String sql = "INSERT INTO notas_faltas (rgm_aluno, disciplina, semestre, nota, faltas) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, notasFaltas.getRgm());
            ps.setString(2, notasFaltas.getDisciplina());
            ps.setString(3, notasFaltas.getSemestre());
            ps.setDouble(4, notasFaltas.getNota());
            ps.setInt(5, notasFaltas.getFaltas());

            ps.executeUpdate();
            System.out.println("Notas e faltas salvas com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar notas e faltas: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // MÉTODO ALTERAR
    public void alterar(NotasFaltas nf) throws Exception {
        Connection conn = null;
        PreparedStatement psBusca = null;
        PreparedStatement psHist = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            
            // 1. BUSCA OS DADOS ATUAIS ANTES DE ALTERAR (Para saber o valor antigo)
            String sqlBusca = "SELECT nota, faltas FROM notas_faltas WHERE rgm_aluno = ? AND disciplina = ? AND semestre = ?";
            psBusca = conn.prepareStatement(sqlBusca);
            psBusca.setString(1, nf.getRgm());
            psBusca.setString(2, nf.getDisciplina());
            psBusca.setString(3, nf.getSemestre());
            rs = psBusca.executeQuery();

            double notaAntiga = 0.0;
            int faltasAntigas = 0;

            if (rs.next()) {
                notaAntiga = rs.getDouble("nota");
                faltasAntigas = rs.getInt("faltas");
            }

            // GRAVA NO HISTÓRICO A MUDANÇA
            String sqlHist = "INSERT INTO historico_notas_faltas (rgm_aluno, disciplina, semestre, nota_antiga, nota_nova, faltas_antigas, faltas_novas) VALUES (?, ?, ?, ?, ?, ?, ?)";
            psHist = conn.prepareStatement(sqlHist);
            psHist.setString(1, nf.getRgm());
            psHist.setString(2, nf.getDisciplina());
            psHist.setString(3, nf.getSemestre());
            psHist.setDouble(4, notaAntiga);
            psHist.setDouble(5, nf.getNota()); 
            psHist.setInt(6, faltasAntigas);
            psHist.setInt(7, nf.getFaltas()); 
            psHist.executeUpdate();

            // FAZ O UPDATE REAL NA TABELA PRINCIPAL
            String sqlUpdate = "UPDATE notas_faltas SET nota = ?, faltas = ? WHERE rgm_aluno = ? AND disciplina = ? AND semestre = ?";
            psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setDouble(1, nf.getNota());
            psUpdate.setInt(2, nf.getFaltas());
            psUpdate.setString(3, nf.getRgm());
            psUpdate.setString(4, nf.getDisciplina());
            psUpdate.setString(5, nf.getSemestre());
            psUpdate.executeUpdate();

        } catch (Exception e) {
            throw new Exception("Erro ao atualizar e gerar histórico: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (psBusca != null) psBusca.close(); } catch (Exception e) {}
            try { if (psHist != null) psHist.close(); } catch (Exception e) {}
            try { if (psUpdate != null) psUpdate.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // MÉTODO EXCLUIR
    public void excluir(String rgm, String disciplina, String semestre) throws Exception {
        String sql = "DELETE FROM notas_faltas WHERE rgm_aluno = ? AND disciplina = ? AND semestre = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, rgm);
            ps.setString(2, disciplina);
            ps.setString(3, semestre);

            ps.executeUpdate();
            System.out.println("Notas e faltas excluídas com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir notas e faltas: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // MÉTODO CONSULTAR
    public NotasFaltas consultar(String rgm, String disciplina, String semestre) throws Exception {
        String sql = "SELECT * FROM notas_faltas WHERE rgm_aluno = ? AND disciplina = ? AND semestre = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        NotasFaltas notasFaltas = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, rgm);
            ps.setString(2, disciplina);
            ps.setString(3, semestre);
            
            rs = ps.executeQuery();

            if (rs.next()) {
                notasFaltas = new NotasFaltas();
                notasFaltas.setRgm(rs.getString("rgm_aluno"));
                notasFaltas.setDisciplina(rs.getString("disciplina"));
                notasFaltas.setSemestre(rs.getString("semestre"));
                notasFaltas.setNota(rs.getDouble("nota"));
                notasFaltas.setFaltas(rs.getInt("faltas"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar notas e faltas: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return notasFaltas;
    }

    // BUSCA O HISTÓRICO DE ALTERAÇÃO
    public String consultarHistorico(String rgm, String disciplina, String semestre) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder historicoTexto = new StringBuilder();

        try {
            conn = ConnectionFactory.getConnection();
            
            String sql = "SELECT nota_antiga, nota_nova, faltas_antigas, faltas_novas, data_alteracao " +
                         "FROM historico_notas_faltas " +
                         "WHERE rgm_aluno = ? AND disciplina = ? AND semestre = ? " +
                         "ORDER BY data_alteracao DESC";
                         
            ps = conn.prepareStatement(sql);
            ps.setString(1, rgm);
            ps.setString(2, disciplina);
            ps.setString(3, semestre);
            rs = ps.executeQuery();

            historicoTexto.append("--- HISTÓRICO DE ALTERAÇÕES ---\n\n");
            
            boolean temHistorico = false;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            while (rs.next()) {
                temHistorico = true;
                String data = sdf.format(rs.getTimestamp("data_alteracao"));
                double notaAntiga = rs.getDouble("nota_antiga");
                double notaNova = rs.getDouble("nota_nova");
                int faltasAntigas = rs.getInt("faltas_antigas");
                int faltasNovas = rs.getInt("faltas_novas");

                historicoTexto.append(String.format(
                    "Data: %s\n» Nota: %.2f -> %.2f\n» Faltas: %d -> %d\n---------------------------------\n", 
                    data, notaAntiga, notaNova, faltasAntigas, faltasNovas
                ));
            }

            if (!temHistorico) {
                historicoTexto.append("Nenhuma alteração anterior registrada.");
            }

            return historicoTexto.toString();

        } catch (SQLException e) {
            throw new Exception("Erro ao consultar histórico no banco: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
