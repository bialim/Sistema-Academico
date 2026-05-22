package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Adicionados parâmetros para corrigir o fuso horário (serverTimezone), SSL e codificação de caracteres (characterEncoding)
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "Lcs14hmhm@";
    
    // Atualizado para o driver moderno do MySQL (com.mysql.cj.jdbc.Driver)
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection() throws Exception {
        try {
            Class.forName(DRIVER);
            
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver do banco de dados não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            throw new Exception("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}