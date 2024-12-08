
import java.rmi.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Classes de Dados
class Clinica {
    String nome;
    String morada;

    Clinica(String nome, String morada) {
        this.nome = nome;
        this.morada = morada;
    }

    @Override
    public String toString() {
        return "Clinica{nome='" + nome + "', morada='" + morada + "'}";
    }
}

class Especialidade {
    String nome;

    Especialidade(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Especialidade{nome='" + nome + "'}";
    }
}

class Medico {
    String nome;
    int idEspecialidade;
    int idClinica;

    Medico(String nome, int idEspecialidade, int idClinica) {
        this.nome = nome;
        this.idEspecialidade = idEspecialidade;
        this.idClinica = idClinica;
    }

    @Override
    public String toString() {
        return "Medico{nome='" + nome + "', idEspecialidade=" + idEspecialidade + ", idClinica=" + idClinica + "}";
    }
}

class Pessoa {
    String nome;

    Pessoa(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "'}";
    }
}

class Consulta {
    int idClinica;
    int idMedico;
    int idPessoa;
    String dataHora;

    Consulta(int idClinica, int idMedico, int idPessoa, String dataHora) {
        this.idClinica = idClinica;
        this.idMedico = idMedico;
        this.idPessoa = idPessoa;
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Consulta{idClinica=" + idClinica + ", idMedico=" + idMedico + ", idPessoa=" + idPessoa + ", dataHora='" + dataHora + "'}";
    }
}

public class Main {

    // Global lists to store data
//    static List<Clinica> clinicas = new ArrayList<>();
//    static List<Especialidade> especialidades = new ArrayList<>();
//    static List<Medico> medicos = new ArrayList<>();
//    static List<Pessoa> pessoas = new ArrayList<>();
//    static List<Consulta> consultas = new ArrayList<>();

    public static void main(String[] args) {
//        try {
//            // Paths for the individual files
//            String basePath = "/home/gon-alo-neves/Desktop/ComputacaoDistribuida/db/";
//            // String basePath = "C:/dev/SisDistribuidos/PROJETO/db/db/";
//            String clinicasPath = basePath + "clinicas.txt";
//            String especialidadesPath = basePath + "especialidade.txt";
//            String medicosPath = basePath + "medico.txt";
//            String pessoasPath = basePath + "pessoa.txt";
//            String consultasPath = basePath + "consulta.txt";
//
//            clinicas.addAll(readClinicas(clinicasPath));
//            especialidades.addAll(readEspecialidades(especialidadesPath));
//            medicos.addAll(readMedicos(medicosPath));
//            pessoas.addAll(readPessoas(pessoasPath));
//            consultas.addAll(readConsultas(consultasPath));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        if (args.length != 1) {
//            System.err.println("usage: java SampleServer rmi_port");
//            System.exit(1);
//        }
        // Create and install a security manager
//        if (System.getSecurityManager() == null)
//            System.setSecurityManager(new RMISecurityManager());

        try {
//            int port = Integer.parseInt(args[0]);
//            String url = "//localhost:" + port + "/Sample";
//            Naming.rebind(url, new ServerActions());
//            System.out.println("server " + url + " is running...");
            ServerActions rmiServerActions = new ServerActions();
            Naming.rebind("rmiServer", rmiServerActions);
            System.out.println("ServerRunning");

        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }

//
//    static List<Clinica> readClinicas(String filePath) throws IOException {
//        List<Clinica> clinicas = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            br.readLine(); // Skip header
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(";");
//                clinicas.add(new Clinica(parts[0], parts[1]));
//            }
//        }
//        return clinicas;
//    }
//
//    static List<Especialidade> readEspecialidades(String filePath) throws IOException {
//        List<Especialidade> especialidades = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            br.readLine(); // Skip header
//            String line;
//            while ((line = br.readLine()) != null) {
//                especialidades.add(new Especialidade(line));
//            }
//        }
//        return especialidades;
//    }
//
//    static List<Medico> readMedicos(String filePath) throws IOException {
//        List<Medico> medicos = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            br.readLine(); // Skip header
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(";");
//                medicos.add(new Medico(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
//            }
//        }
//        return medicos;
//    }
//
//    static List<Pessoa> readPessoas(String filePath) throws IOException {
//        List<Pessoa> pessoas = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            br.readLine(); // Skip header
//            String line;
//            while ((line = br.readLine()) != null) {
//                pessoas.add(new Pessoa(line));
//            }
//        }
//        return pessoas;
//    }
//
//    static List<Consulta> readConsultas(String filePath) throws IOException {
//        List<Consulta> consultas = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            br.readLine(); // Skip header
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(";");
//                consultas.add(new Consulta(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3]));
//            }
//        }
//        return consultas;
//    }
}

