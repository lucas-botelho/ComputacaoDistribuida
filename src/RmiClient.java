import java.rmi.*;

public class RmiClient {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Usage: java RmiClient <server-host> <value>");
            return;
        }

        try {
            String serverUrl = "rmi://" + args[0] + "/rmiServer";
            IServerActions serverActionsInterface = (IServerActions) Naming.lookup(serverUrl);

            System.out.println("Valor passado: " + args[1]);
            int valorRecebido = Integer.parseInt(args[1]);
            int resultado = serverActionsInterface.teste(valorRecebido);
            System.out.println("Resultado do método teste: " + resultado);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
    }
}
