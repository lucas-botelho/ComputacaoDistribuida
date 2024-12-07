import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServerActions extends UnicastRemoteObject implements IServerActions {

    private static final String CONSULTA_FILE = "/home/gon-alo-neves/Desktop/ComputacaoDistribuida/db/consulta.txt";
    private static final String PESSOA_FILE = "/home/gon-alo-neves/Desktop/ComputacaoDistribuida/db/pessoa.txt";
    private static final String CLINICA_FILE = "/home/gon-alo-neves/Desktop/ComputacaoDistribuida/db/clinicas.txt";
    private static final String ESPECIALIDADE_FILE = "/home/gon-alo-neves/Desktop/ComputacaoDistribuida/db/especialidade.txt";
    private static final String MEDICO_FILE = "/home/gon-alo-neves/Desktop/ComputacaoDistribuida/db/medico.txt";

    public ServerActions() throws RemoteException {
    }

    //auth
    @Override
    public int autenticar(String username, String password) throws RemoteException {
        System.out.println("Debug: Starting authentication for username: " + username);

        ArrayList<String> pessoas = readFromFile(PESSOA_FILE);

        // Debugging the list of pessoas
        System.out.println("Debug: Loaded pessoas from file: " + pessoas);

        // Checking if the user exists
        for (String pessoa : pessoas) {
            System.out.println("Debug: Checking pessoa entry: " + pessoa);

            String[] dados = pessoa.split(";");
            if (dados.length == 3) {
                System.out.println("Debug: Parsed pessoa - ID: " + dados[0] + ", Username: " + dados[1]);

                if (dados[1].equals(username) && dados[2].equals(password)) {
                    System.out.println("Debug: Authentication successful for username: " + username);
                    return Integer.parseInt(dados[0]);
                }
            } else {
                System.out.println("Debug: Malformed entry detected: " + pessoa);
            }
        }

        System.out.println("Debug: Authentication failed for username: " + username);
        return -1;
    }

    //4.1
    @Override
    public boolean reservarConsulta(int idClinica, int idEspecialidade, int idUser, String dataHora) throws RemoteException {
        System.out.println("Debug: Starting reservarConsulta for IdClinica: " + idClinica + ", IdEspecialidade: " + idEspecialidade + ", IdUser: " + idUser + ", DataHora: " + dataHora);

        try {
            // Step 1: Get all doctors with the specified specialty
            String medicos = getMedicosPorIdEspecialidade(idEspecialidade);
            if (medicos.trim().isEmpty()) {
                System.out.println("Debug: No doctors found for IdEspecialidade: " + idEspecialidade);
                return false;
            }

            // Parse the doctors and filter for those belonging to the specified clinic
            ArrayList<String> medicosDisponiveis = new ArrayList<>();
            String[] medicoLines = medicos.split("\n");
            for (String line : medicoLines) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    int idMedico = Integer.parseInt(parts[0].trim());
                    String nomeMedico = parts[1].trim();
                    int clinicaMedico = Integer.parseInt(parts[3].trim());

                    if (clinicaMedico == idClinica) {
                        medicosDisponiveis.add(String.valueOf(idMedico));
                        System.out.println("Debug: Médico disponível encontrado: " + nomeMedico + " (IdMedico: " + idMedico + ")");
                    }
                } else {
                    System.out.println("Debug: Linha de médico malformada: " + line);
                }
            }

            if (medicosDisponiveis.isEmpty()) {
                System.out.println("Debug: No doctors with IdEspecialidade " + idEspecialidade + " found in clinic with IdClinica: " + idClinica);
                return false;
            }

            // Step 2: Load existing consultas
            ArrayList<String> consultas = readFromFile(CONSULTA_FILE);
            System.out.println("Debug: Loaded " + consultas.size() + " existing consultations.");

            // Step 3: Find a doctor with available schedule
            int selectedMedicoId = -1;
            for (String medicoId : medicosDisponiveis) {
                boolean isAvailable = true;

                for (String consulta : consultas) {
                    String[] consultaParts = consulta.split(";");
                    if (consultaParts.length >= 6) {
                        int idConsultaMedico = Integer.parseInt(consultaParts[2].trim());
                        String consultaHora = consultaParts[4].trim();
                        boolean isCanceled = Boolean.parseBoolean(consultaParts[5].trim());

                        if (idConsultaMedico == Integer.parseInt(medicoId) && consultaHora.equals(dataHora) && !isCanceled) {
                            isAvailable = false;
                            System.out.println("Debug: Médico com IdMedico " + medicoId + " já tem uma consulta para " + dataHora);
                            break;
                        }
                    } else {
                        System.out.println("Debug: Linha de consulta malformada: " + consulta);
                    }
                }

                if (isAvailable) {
                    selectedMedicoId = Integer.parseInt(medicoId);
                    break;
                }
            }

            if (selectedMedicoId == -1) {
                System.out.println("Debug: No doctors available at the specified time: " + dataHora);
                return false;
            }

            // Step 4: Generate a new idConsulta
            int idConsulta = consultas.size() + 1; // New idConsulta is the next available ID
            System.out.println("Debug: Calculated idConsulta: " + idConsulta);

            // Step 5: Append the new consulta to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONSULTA_FILE, true))) {
                bw.write(idConsulta + ";" + idClinica + ";" + selectedMedicoId + ";" + idUser + ";" + dataHora + ";false");
                bw.newLine();
                System.out.println("Consulta reservada com sucesso! idConsulta: " + idConsulta);
            }

            return true; // Success

        } catch (IOException e) {
            System.out.println("Debug: Exception occurred while reserving consulta: " + e.getMessage());
            throw new RemoteException("Erro ao reservar consulta", e);
        } catch (Exception e) {
            System.out.println("Debug: Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            return false; // Return false in case of an unexpected error
        }
    }

    //4.1
    @Override
    public String listarEspecialidades() throws RemoteException {
        StringBuilder especialidades = new StringBuilder();

        System.out.println("Debug: Starting listarEspecialidades.");

        try {
            // Use readFromFile to load all specialty lines (excluding header)
            ArrayList<String> lines = readFromFile(ESPECIALIDADE_FILE);
            System.out.println("Debug: Loaded " + lines.size() + " specialty lines from file.");

            for (String line : lines) {
                System.out.println("Debug: Processing specialty line: " + line);
                String[] parts = line.split(";");

                if (parts.length == 2) { // Ensure the line has the required fields
                    int idEspecialidade = Integer.parseInt(parts[0].trim());
                    String nomeEspecialidade = parts[1].trim();

                    // Append specialty info to the result
                    especialidades.append(idEspecialidade)
                            .append(";")
                            .append(nomeEspecialidade)
                            .append("\n");
                } else {
                    System.out.println("Debug: Malformed specialty line: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Debug: Exception occurred while listing specialties: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Erro ao listar especialidades.", e);
        }

        System.out.println("Debug: Finished listarEspecialidades.");
        return especialidades.length() > 0 ? especialidades.toString() : "Nenhuma especialidade encontrada.";
    }

    //4.1
    @Override
    public String getClinicasPorEspecialidade(int idEspecialidade) throws RemoteException {
        System.out.println("Debug: Starting getClinicasPorEspecialidade for idEspecialidade: " + idEspecialidade);

        try {
            // Obter lista de médicos por especialidade
            String[] medicos = getMedicosPorIdEspecialidade(idEspecialidade).split("\n");
            System.out.println("Debug: Found " + medicos.length + " médicos para idEspecialidade: " + idEspecialidade);

            // Obter lista de clínicas
            String[] clinicas = listarClinicas().split("\n");
            System.out.println("Debug: Found " + clinicas.length + " clínicas disponíveis.");

            StringBuilder clinicasComEspecialidade = new StringBuilder();

            for (String clinica : clinicas) {
                String[] clinicaParts = clinica.split(";");
                if (clinicaParts.length >= 2) { // Garantir que a linha da clínica tem pelo menos os campos necessários
                    String idClinica = clinicaParts[0];
                    System.out.println("Debug: Checking clínica com idClinica: " + idClinica);

                    boolean foundMedico = false;
                    for (String medico : medicos) {
                        String[] medicoParts = medico.split(";");
                        if (medicoParts.length >= 4) { // Garantir que a linha do médico tem os campos necessários
                            String idClinicaMedico = medicoParts[3];
                            if (idClinica.equals(idClinicaMedico)) {
                                System.out.println("Debug: Clínica " + idClinica + " possui médico com idEspecialidade: " + idEspecialidade);
                                clinicasComEspecialidade.append(clinica).append("\n");
                                foundMedico = true;
                                break; // Evitar adicionar a mesma clínica várias vezes
                            }
                        } else {
                            System.out.println("Debug: Malformed médico entry: " + medico);
                        }
                    }

                    if (!foundMedico) {
                        System.out.println("Debug: Clínica " + idClinica + " não possui médicos com idEspecialidade: " + idEspecialidade);
                    }
                } else {
                    System.out.println("Debug: Malformed clínica entry: " + clinica);
                }
            }

            String result = clinicasComEspecialidade.toString();
            if (result.isEmpty()) {
                System.out.println("Debug: Nenhuma clínica encontrada para idEspecialidade: " + idEspecialidade);
            } else {
                System.out.println("Debug: Clínicas com especialidade encontrada: \n" + result);
            }

            return result;

        } catch (Exception e) {
            System.out.println("Debug: Exception occurred in getClinicasPorEspecialidade: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Erro ao buscar clínicas por especialidade.", e);
        }
    }


    //4.2
    @Override
    public boolean cancelarConsulta(int idConsulta) throws RemoteException {
        boolean isCanceled = false;

        System.out.println("Debug: Iniciando cancelamento da consulta com ID: " + idConsulta);

        try {
            // Ler todas as consultas do arquivo, ignorando o cabeçalho
            ArrayList<String> lines = readFromFile(CONSULTA_FILE);
            System.out.println("Debug: Total de linhas carregadas (excluindo cabeçalho): " + lines.size());

            List<String> updatedLines = new ArrayList<>();
            int totalConsultas = 0;

            // Processar as linhas carregadas
            for (String line : lines) {
                totalConsultas++;
                String[] parts = line.split(";");

                if (parts.length == 6) { // Verifica se a linha possui exatamente os campos necessários
                    int currentIdConsulta = Integer.parseInt(parts[0].trim()); // ID da consulta
                    boolean canceled = Boolean.parseBoolean(parts[5].trim());

                    if (currentIdConsulta == idConsulta) {
                        System.out.println("Debug: Consulta encontrada. ID: " + idConsulta + " | Estado atual: " + canceled);
                        if (!canceled) {
                            parts[5] = "true"; // Atualiza o campo Canceled para "true"
                            line = String.join(";", parts);
                            isCanceled = true; // Marca como cancelada
                            System.out.println("Debug: Consulta ID " + idConsulta + " marcada como cancelada.");
                        } else {
                            System.out.println("Debug: Consulta ID " + idConsulta + " já estava cancelada.");
                        }
                    }
                } else {
                    System.out.println("Debug: Linha malformada no arquivo: " + line);
                }

                updatedLines.add(line); // Adiciona a linha atualizada ou original
            }

            // Reescreve o arquivo com as alterações
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONSULTA_FILE))) {
                System.out.println("Debug: Iniciando reescrita do arquivo de consultas.");

                // Adiciona o cabeçalho manualmente
                bw.write("idConsulta;IdClinica;IdMedico;IdPessoa;DataHora;Canceled");
                bw.newLine();

                // Escreve as linhas atualizadas
                for (String updatedLine : updatedLines) {
                    bw.write(updatedLine);
                    bw.newLine();
                    System.out.println("Debug: Linha escrita: " + updatedLine);
                }
                System.out.println("Debug: Reescrita do arquivo concluída.");
            }

            if (isCanceled) {
                System.out.println("Debug: Consulta cancelada com sucesso! ID: " + idConsulta);
            } else {
                System.out.println("Debug: Consulta não encontrada ou já cancelada. ID: " + idConsulta);
            }

            System.out.println("Debug: Total de consultas processadas: " + totalConsultas);

            return isCanceled;

        } catch (IOException e) {
            System.out.println("Debug: Erro ao processar o arquivo de consultas: " + e.getMessage());
            throw new RemoteException("Erro ao cancelar consulta", e);
        } catch (NumberFormatException e) {
            System.out.println("Debug: Erro ao interpretar ID da consulta. ID fornecido: " + idConsulta + " | Detalhes: " + e.getMessage());
            throw new RemoteException("Erro ao processar o ID da consulta", e);
        } catch (Exception e) {
            System.out.println("Debug: Erro inesperado ao cancelar consulta. Detalhes: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Erro inesperado ao cancelar consulta", e);
        }
    }

    //4.3
    @Override
    public String listarConsulta(int idUser) throws RemoteException {
        boolean found = false;
        String results = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("Debug: Starting listarConsulta for idUser: " + idUser);

        try {
            // Use readFromFile to load all lines (excluding the header)
            ArrayList<String> consultas = readFromFile(CONSULTA_FILE);
            System.out.println("Debug: Loaded " + consultas.size() + " lines from file.");

            for (String consulta : consultas) {
                System.out.println("Debug: Processing line: " + consulta);
                String[] parts = consulta.split(";");

                if (parts.length >= 6) { // Ensure the line has the required fields, including idConsulta
                    int idConsulta = Integer.parseInt(parts[0]);
                    int idPessoa = Integer.parseInt(parts[3]);
                    boolean isCanceled = Boolean.parseBoolean(parts[5]);
                    LocalDateTime dataConsulta = LocalDateTime.parse(parts[4], formatter);

                    if (idPessoa == idUser && !isCanceled && dataConsulta.isAfter(now)) {
                        System.out.println("Debug: Valid match found for idUser: " + idUser);
                        results += idConsulta + ";" + parts[1] + ";" + parts[2] + ";" + parts[4] + "\n";
                        found = true;
                    } else if (idPessoa == idUser) {
                        if (isCanceled) {
                            System.out.println("Debug: Match found for idUser: " + idUser + ", but it is canceled.");
                        } else if (dataConsulta.isBefore(now)) {
                            System.out.println("Debug: Match found for idUser: " + idUser + ", but the date is overdue.");
                        }
                    }
                } else {
                    System.out.println("Debug: Malformed line: " + consulta);
                }
            }
        } catch (Exception e) {
            System.out.println("Debug: Exception occurred: " + e.getMessage());
            throw new RemoteException("Erro ao listar consultas", e);
        }

        System.out.println("Debug: Consultas found: " + results);
        return found ? results : "Nenhuma consulta encontrada para o usuário.";
    }

    //4.4
    @Override
    public int registarUtilizador(String username, String password) throws RemoteException {
        System.out.println("Debug: Starting registarUtilizador for username: " + username);
        ArrayList<String> pessoas = readFromFile(PESSOA_FILE);

        // Checking if the user already exists
        for (String pessoa : pessoas) {
            String[] dados = pessoa.split(";");
            if (dados.length == 3) {
                System.out.println("Debug: Checking pessoa: " + pessoa);
                if (dados[1].equals(username)) {
                    System.out.println("Debug: Username " + username + " already exists. Returning existing ID.");
                    return -1; // Return invalid id
                }
            } else {
                System.out.println("Debug: Malformed entry found in pessoas: " + pessoa);
            }
        }

        // Generate a new user ID
        int id = pessoas.size() + 1; // Increment ID by 1 to avoid zero-based indexing
        System.out.println("Debug: New user ID: " + id);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PESSOA_FILE, true))) {
            // Append the new user to the file
            bw.write(id + ";" + username + ";" + password);
            bw.newLine();
            System.out.println("Debug: New user " + username + " written to file.");
        } catch (IOException e) {
            System.out.println("Debug: Exception occurred while writing to file: " + e.getMessage());
            throw new RemoteException("Erro ao registrar utilizador", e);
        }

        System.out.println("Debug: User " + username + " registered successfully. ID: " + id);
        return id; // Return the newly created user's ID
    }

    private ArrayList<String> readFromFile(String filePath) {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        }
        catch (Exception e){
            System.out.println("erro em readFromFile():" + filePath + " Exception: " + e);
        }

        return data;
    }

    public String listarClinicas() throws RemoteException {
        StringBuilder clinicas = new StringBuilder();

        System.out.println("Debug: Starting listarClinicas.");

        try {
            // Use readFromFile to load all clinic lines (excluding header)
            ArrayList<String> lines = readFromFile(CLINICA_FILE);
            System.out.println("Debug: Loaded " + lines.size() + " clinic lines from file.");

            for (String line : lines) {
                System.out.println("Debug: Processing clinic line: " + line);
                String[] parts = line.split(";");

                if (parts.length >= 3) { // Ensure the line has the required fields
                    int idClinica = Integer.parseInt(parts[0].trim());
                    String nomeClinica = parts[1].trim();
                    String localizacao = parts[2].trim();

                    // Append clinic info to the result
                    clinicas.append(idClinica)
                            .append(";")
                            .append(nomeClinica)
                            .append(";")
                            .append(localizacao)
                            .append("\n");
                } else {
                    System.out.println("Debug: Malformed clinic line: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Debug: Exception occurred while listing clinics: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Erro ao listar clínicas.", e);
        }

        System.out.println("Debug: Finished listarClinicas.");
        return clinicas.length() > 0 ? clinicas.toString() : "Nenhuma clínica encontrada.";
    }

    private String getMedicosPorIdEspecialidade(int idEspecialidade) throws RemoteException {
        System.out.println("Debug: Starting getMedicosPorIdEspecialidade for IdEspecialidade: " + idEspecialidade);

        StringBuilder medicos = new StringBuilder();

        try {
            // Use readFromFile to load all lines (excluding header)
            ArrayList<String> lines = readFromFile(MEDICO_FILE);
            System.out.println("Debug: Loaded " + lines.size() + " medico lines from file.");

            for (String line : lines) {
                System.out.println("Debug: Processing medico line: " + line);
                String[] parts = line.split(";");

                if (parts.length >= 4) { // Ensure the line has the required fields
                    int currentIdEspecialidade = Integer.parseInt(parts[2].trim());

                    if (currentIdEspecialidade == idEspecialidade) {
                        // Found a medico with the specified idEspecialidade
                        String medico = parts[0] + ";" + parts[1] + ";" + parts[2]+ ";" + parts[3];
                        medicos.append(medico).append("\n");
                    }
                } else {
                    System.out.println("Debug: Malformed medico line: " + line);
                }
            }

            if (medicos.length() == 0) {
                System.out.println("Debug: No medicos found for IdEspecialidade: " + idEspecialidade);
                return "Nenhum médico encontrado para a especialidade.";
            }

            System.out.println("Debug: Found medicos for IdEspecialidade: " + idEspecialidade);
            return medicos.toString();

        } catch (Exception e) {
            System.out.println("Debug: Exception occurred while getting medicos by IdEspecialidade: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Erro ao listar médicos por IdEspecialidade.", e);
        }
    }

    @Override
    public int teste(int valor) throws RemoteException {
        return valor * 2;
    }

}
