import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    // Variável para a instância única da conexão
    private static DBConnection instance;
    private Connection connection;

    // Dados de conexão
    private static final String URL = "jdbc:sqlserver://127.0.0.1:1333;databaseName=DistributedComputingDB;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "Root12345";

    private DBConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    // Método para retornar a instância única da classe
    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Método para obter a conexão de banco de dados
    public Connection getConnection() {
        return connection;
    }
}
