
import java.rmi.*;


public class Main {

    public static void main(String[] args) {

        try {
            ServerActions rmiServerActions = new ServerActions();
            Naming.rebind("rmiServer", rmiServerActions);
            System.out.println("ServerRunning");

        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}

