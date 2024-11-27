import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ServerActions extends UnicastRemoteObject implements IServerActions {
    @Override
    public void reservarConsulta(int idClinica, int idMedico, int idPessoa, String dataHora) throws RemoteException {
        String sql = "INSERT INTO Consulta (IdClinica, IdMedico, IdPessoa, DataHora) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idClinica);
            stmt.setInt(2, idMedico);
            stmt.setInt(3, idPessoa);
            stmt.setString(4, dataHora);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Consulta reservada com sucesso!");
            } else {
                System.out.println("Falha ao reservar consulta.");
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao reservar consulta", e);
        }
    }


    @Override
    public void cancelarConsulta(int idConsulta) throws RemoteException {
        String sql = "DELETE FROM Consulta WHERE IdConsulta = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Consulta cancelada com sucesso!");
            } else {
                System.out.println("Consulta não encontrada.");
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao cancelar consulta", e);
        }
    }


    @Override
    public void listarConsulta(int idUser) throws RemoteException {
        String sql = "SELECT * FROM Consulta WHERE IdPessoa = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Consulta ID: " + rs.getInt("IdConsulta"));
                    System.out.println("Clínica ID: " + rs.getInt("IdClinica"));
                    System.out.println("Médico ID: " + rs.getInt("IdMedico"));
                    System.out.println("Data/Hora: " + rs.getString("DataHora"));
                    System.out.println("---------------");
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao listar consultas", e);
        }
    }


    @Override
    public void registarUtilizador(String username, String password) throws RemoteException {
        String sql = "INSERT INTO Pessoa (Nome) VALUES (?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário registrado com sucesso!");
            } else {
                System.out.println("Falha ao registrar usuário.");
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao registrar usuário", e);
        }
    }

    @Override
    public int teste(int valor) throws RemoteException {
        return valor *2;
    }


    public ServerActions() throws RemoteException {
    }
}
