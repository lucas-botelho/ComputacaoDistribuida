import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerActions extends Remote {
    void reservarConsulta(int idClinica, int idMedico, int idPessoa, String dataHora) throws RemoteException;
    void cancelarConsulta(int idConsulta) throws RemoteException;
    void listarConsulta(int idUser) throws RemoteException;
    void registarUtilizador(String username, String password) throws RemoteException;
    int teste(int valor) throws RemoteException;

}
