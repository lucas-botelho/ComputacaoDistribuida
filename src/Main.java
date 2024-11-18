
import java.rmi.*;

public class Main {

//    public static void inserirEspecialidade(String nomeEspecialidade) {
//        String sql = "INSERT INTO Especialidade (Nome) VALUES (?)";
//
//        try (Connection conn = DBConnection.getInstance().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            // Define o valor do parâmetro
//            stmt.setString(1, nomeEspecialidade);
//
//            // Executa o INSERT
//            int rowsAffected = stmt.executeUpdate();
//            System.out.println("Linhas inseridas: " + rowsAffected);
//
//        } catch (SQLException e) {
//            System.err.println("Erro ao inserir especialidade: " + e.getMessage());
//        }
//    }

    public static void main(String[] args) {
//        inserirEspecialidade("Cardiologia");  // Exemplo de uso
//codigo cliente scuffed
//        try {
//            String addServerURL = "rmi://" + args[0] + "/AddServer";
//            IServerActions addServerIntf = (IServerActions) Naming.lookup(addServerURL);
////            System.out.println("The first number is: " + args[1]);
////            double d1 = Double.valueOf(args[1]).doubleValue();
////            System.out.println("The second number is: " + args[2]);
////
////            double d2 = Double.valueOf(args[2]).doubleValue();
////            System.out.println("The sum is: " + addServerIntf.add(d1, d2));
//        }
//        catch(Exception e) {
//            System.out.println("Exception: " + e);
//        }

        try {
            ServerActions rmiServerActions = new ServerActions();
            Naming.rebind("rmiServer", rmiServerActions);
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        }

    }
}

