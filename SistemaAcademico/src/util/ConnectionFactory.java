package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Configurações do seu banco de dados
    // Altere "seu_banco_de_dados", "seu_usuario" e "sua_senha" de acordo com o seu ambiente
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    // Método responsável por abrir e retornar a conexão
    public static Connection getConnection() throws Exception {
        try {
            // Garante que o driver do banco está carregado na memória
            Class.forName(DRIVER);
            
            // Cria e retorna a conexão com os parâmetros configurados
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver do banco de dados não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            throw new Exception("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}