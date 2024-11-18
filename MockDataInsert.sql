USE DistributedComputingDB;
GO

-- Inserir dados na tabela de Clínicas
INSERT INTO Clinicas (Nome, Morada)
VALUES 
    ('Clínica Central', 'Rua das Flores, 123'),
    ('Clínica Saúde e Vida', 'Avenida Principal, 456'),
    ('Clínica Bem-Estar', 'Praça da Alegria, 789');

-- Inserir dados na tabela de Especialidades
INSERT INTO Especialidade (Nome)
VALUES 
    ('Cardiologia'),
    ('Pediatria'),
    ('Dermatologia'),
    ('Ortopedia');

-- Inserir dados na tabela de Médicos
INSERT INTO Medico (Nome, IdEspecialidade, IdClinica)
VALUES 
    ('Dr. João Silva', 1, 1),
    ('Dr. Ana Maria', 2, 1),
    ('Dr. Pedro Almeida', 3, 2),
    ('Dra. Sofia Costa', 4, 3);

-- Inserir dados na tabela de Pessoas (Pacientes)
INSERT INTO Pessoa (Nome)
VALUES 
    ('Carlos Oliveira'),
    ('Maria Santos'),
    ('Joana Pereira'),
    ('Ricardo Mendes'),
    ('Ana Carvalho');

-- Inserir dados na tabela de Consultas
INSERT INTO Consulta (IdClinica, IdMedico, IdPessoa, DataHora)
VALUES 
    (1, 1, 1, '2024-11-20 10:30:00'),
    (1, 2, 2, '2024-11-21 14:00:00'),
    (2, 3, 3, '2024-11-22 09:00:00'),
    (3, 4, 4, '2024-11-23 16:30:00'),
    (1, 1, 5, '2024-11-24 11:00:00');
