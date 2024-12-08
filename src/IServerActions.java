import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerActions extends Remote {

    //4.1
    boolean reservarConsulta(int idClinica, int idEspecialidade, int idUser, String dataHora) throws RemoteException;

    //4.2
    boolean cancelarConsulta(int idConsulta) throws RemoteException;

    //4.3
    String listarConsulta(int idUser) throws RemoteException;

    //4.4
    int registarUtilizador(String username, String password) throws RemoteException;

    //auth
    int autenticar(String username, String password) throws RemoteException;

    //4.1
    String listarEspecialidades() throws RemoteException;

    int teste(int valor) throws RemoteException;

    //4.1
    String getClinicasPorEspecialidade(int idEspecialidade) throws RemoteException;
}
