package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import util.ConnectionFactory;

public class CursoDAO {

    public List<String> listarCursos() {

        List<String> cursos = new ArrayList<>();

        try {

            Connection conn = ConnectionFactory.getConnection();

            String sql = "SELECT nome_curso FROM tb_curso";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                cursos.add(rs.getString("nome_curso"));

            }

            conn.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

        return cursos;
    }
}