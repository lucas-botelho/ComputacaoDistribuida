import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerActions extends UnicastRemoteObject implements IServerActions {

    private static final String CONSULTA_FILE = "C:/dev/SisDistribuidos/PROJETO/db/db/consulta.txt";
    private static final String PESSOA_FILE = "C:/dev/SisDistribuidos/PROJETO/db/db/pessoa.txt";

    public ServerActions() throws RemoteException {
    }

    @Override
    public void reservarConsulta(int idClinica, int idMedico, int idPessoa, String dataHora) throws RemoteException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONSULTA_FILE, true))) {
            // Append the new consulta to the file
            bw.write(idClinica + ";" + idMedico + ";" + idPessoa + ";" + dataHora);
            bw.newLine();
            System.out.println("Consulta reservada com sucesso!");
        } catch (IOException e) {
            throw new RemoteException("Erro ao reservar consulta", e);
        }
    }

    @Override
    public void cancelarConsulta(int idConsulta) throws RemoteException {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(CONSULTA_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (Integer.parseInt(parts[0]) != idConsulta) { // Keep lines that don't match the ID
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONSULTA_FILE))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Consulta cancelada com sucesso!");
        } catch (IOException e) {
            throw new RemoteException("Erro ao cancelar consulta", e);
        }
    }

    @Override
    public void listarConsulta(int idUser) throws RemoteException {
        try (BufferedReader br = new BufferedReader(new FileReader(CONSULTA_FILE))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (Integer.parseInt(parts[2]) == idUser) {
                    System.out.println("Consulta ID: " + parts[0]);
                    System.out.println("Clínica ID: " + parts[1]);
                    System.out.println("Médico ID: " + parts[2]);
                    System.out.println("Data/Hora: " + parts[3]);
                    System.out.println("---------------");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Nenhuma consulta encontrada para o usuário.");
            }
        } catch (IOException e) {
            throw new RemoteException("Erro ao listar consultas", e);
        }
    }

    @Override
    public void registarUtilizador(String username, String password) throws RemoteException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PESSOA_FILE, true))) {
            // Append the new user to the file
            bw.write(username);
            bw.newLine();
            System.out.println("Usuário registrado com sucesso!");
        } catch (IOException e) {
            throw new RemoteException("Erro ao registrar usuário", e);
        }
    }

    @Override
    public int teste(int valor) throws RemoteException {
        return valor * 2;
    }
}
