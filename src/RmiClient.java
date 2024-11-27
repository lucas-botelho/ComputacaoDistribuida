import java.net.MalformedURLException;
import java.rmi.*;

public class RmiClient {
    public static void main(String args[]){
        try {
            String serverUrl = "rmi://" + args[0] + "rmiServer";
            IServerActions serverActionsInterface = (ServerActions)Naming.lookup(serverUrl);

            System.out.println("Valor passado: " + args[1]);
            int valorRecebido = Integer.valueOf(args[1]).intValue();
            System.out.println("Valor recebido: " + valorRecebido);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
