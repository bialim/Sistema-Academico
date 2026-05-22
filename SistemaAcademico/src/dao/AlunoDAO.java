package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aluno; //importando a classe que está na model
import util.ConnectionFactory; //importando a classe de conexão

public class AlunoDAO {

    // MÉTODO SALVAR COM VALIDAÇÃO DE CPF
	// MÉTODO SALVAR COM VALIDAÇÃO DE CPF
    public void salvar(Aluno aluno) throws Exception {
        //cpf como string, aqui ele "transforma" para apenas os números
        String cpfLimpo = aluno.getCpf() != null ? aluno.getCpf().replaceAll("[.-]", "").trim() : "";

        //verificação se é válido, números repetidos ou com mais dígitos
        if (cpfLimpo.length() != 11 || cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new Exception("CPF inválido! O número informado possui formato incorreto ou dígitos repetidos.");
        }

        String sql = "INSERT INTO tb_aluno (rgm, nome, data_nascimento, cpf, email, endereco, municipio, uf, celular, id_curso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, aluno.getRgm());
            ps.setString(2, aluno.getNome());
            
            if (aluno.getDataNascimento() != null) {
                ps.setDate(3, Date.valueOf(aluno.getDataNascimento()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            //cpf limpo
            ps.setString(4, cpfLimpo);
            ps.setString(5, aluno.getEmail());
            ps.setString(6, aluno.getEndereco());
            ps.setString(7, aluno.getMunicipio());
            ps.setString(8, aluno.getUf());
            ps.setString(9, aluno.getCelular());
            ps.setInt(10, aluno.getIdCurso());

            ps.executeUpdate(); 
            System.out.println("Aluno salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar aluno: " + e.getMessage());
            throw new Exception("Erro de banco de dados ao salvar: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
	
	/* método salvar antigo funcionando também
    public void salvar(Aluno aluno) throws Exception {
        String sql = "INSERT INTO tb_aluno (rgm, nome, data_nascimento, cpf, email, endereco, municipio, uf, celular, id_curso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, aluno.getRgm());
            ps.setString(2, aluno.getNome());
            
            if (aluno.getDataNascimento() != null) {
                ps.setDate(3, Date.valueOf(aluno.getDataNascimento()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            ps.setString(4, aluno.getCpf());
            ps.setString(5, aluno.getEmail());
            ps.setString(6, aluno.getEndereco());
            ps.setString(7, aluno.getMunicipio());
            ps.setString(8, aluno.getUf());
            ps.setString(9, aluno.getCelular());
            ps.setInt(10, aluno.getIdCurso());

            ps.executeUpdate(); 
            System.out.println("Aluno salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar aluno: " + e.getMessage()); // [00:03:55]
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }*/

    // SELECT
    public List<Aluno> listarTodos() throws Exception {
        String sql = "SELECT * FROM tb_aluno";
        List<Aluno> lista = new ArrayList<>(); 
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null; 

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(); 

            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                
                aluno.setRgm(rs.getInt("rgm"));
                aluno.setNome(rs.getString("nome"));
                
                //convertendo data
                Date dbDate = rs.getDate("data_nascimento");
                if (dbDate != null) {
                    aluno.setDataNascimento(dbDate.toLocalDate());
                }
                
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setMunicipio(rs.getString("municipio"));
                aluno.setUf(rs.getString("uf"));
                aluno.setCelular(rs.getString("celular"));
                aluno.setIdCurso(rs.getInt("id_curso"));

                lista.add(aluno); 
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return lista; 
    }

    // SELECT POR RGM
    public Aluno consultarPorRgm(int rgm) throws Exception {
        String sql = "SELECT * FROM tb_aluno WHERE rgm = ?";
        Aluno aluno = null;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, rgm); // [00:44:09]
            rs = ps.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setRgm(rs.getInt("rgm"));
                aluno.setNome(rs.getString("nome"));
                
                Date dbDate = rs.getDate("data_nascimento");
                if (dbDate != null) {
                    aluno.setDataNascimento(dbDate.toLocalDate());
                }
                
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setMunicipio(rs.getString("municipio"));
                aluno.setUf(rs.getString("uf"));
                aluno.setCelular(rs.getString("celular"));
                aluno.setIdCurso(rs.getInt("id_curso"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar aluno: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return aluno;
    }

    // MÉTODO ALTERAR
    public void alterar(Aluno aluno) throws Exception {
        String sql = "UPDATE tb_aluno SET nome = ?, data_nascimento = ?, cpf = ?, email = ?, "
                   + "endereco = ?, municipio = ?, uf = ?, celular = ?, id_curso = ? WHERE rgm = ?"; // [00:38:13]
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, aluno.getNome());
            
            if (aluno.getDataNascimento() != null) {
                ps.setDate(2, Date.valueOf(aluno.getDataNascimento()));
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }
            
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getEmail());
            ps.setString(5, aluno.getEndereco());
            ps.setString(6, aluno.getMunicipio());
            ps.setString(7, aluno.getUf());
            ps.setString(8, aluno.getCelular());
            ps.setInt(9, aluno.getIdCurso());
            ps.setInt(10, aluno.getRgm()); // Onde rgm = ?

            ps.executeUpdate();
            System.out.println("Dados do aluno alterados com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar aluno: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // MÉTODO EXCLUIR
    public void excluir(int rgm) throws Exception {
        String sql = "DELETE FROM tb_aluno WHERE rgm = ?"; // [00:41:16]
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, rgm); 
            ps.executeUpdate();
            System.out.println("Aluno excluído com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage()); // [00:42:01]
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}