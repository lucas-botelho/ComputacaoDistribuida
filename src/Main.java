import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void inserirEspecialidade(String nomeEspecialidade) {
        String sql = "INSERT INTO Especialidade (Nome) VALUES (?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do parâmetro
            stmt.setString(1, nomeEspecialidade);

            // Executa o INSERT
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Linhas inseridas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao inserir especialidade: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        inserirEspecialidade("Cardiologia");  // Exemplo de uso
    }
}
