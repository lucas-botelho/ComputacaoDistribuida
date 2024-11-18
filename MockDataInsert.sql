USE DistributedComputingDB;
GO

-- Inserir dados na tabela de Clínicas
INSERT INTO Clinicas (Nome, Morada)
VALUES 
    ('Clínica A', 'Rua das Flores, 123'),
    ('Clínica B', 'Avenida Principal, 456'),
    ('Clínica C', 'Praça da Alegria, 789'),
    ('Clínica D', 'Rua da Esperança, 101');

-- Inserir dados na tabela de Especialidades
INSERT INTO Especialidade (Nome)
VALUES 
    ('Clínica Geral'),      -- 1
    ('Pediatria'),          -- 2
    ('Cardiologia'),        -- 3
    ('Ginecologia'),        -- 4
    ('Hematologia'),        -- 5
    ('Medicina Tropical'),  -- 6
    ('Neurologia'),         -- 7
    ('Oftalmologia'),       -- 8
    ('Oncologia'),          -- 9
    ('Otorrinolaringologia'); -- 10

-- Inserir dados na tabela de Médicos
-- Médicos da Clínica A
INSERT INTO Medico (Nome, IdEspecialidade, IdClinica)
VALUES 
    ('Dr. João Silva', 1, 1), -- Clínica Geral
    ('Dr. Ana Costa', 1, 1),
    ('Dr. Pedro Almeida', 1, 1),
    ('Dr. Maria Santos', 1, 1),
    ('Dr. Ricardo Mendes', 1, 1),
    ('Dr. Beatriz Lima', 1, 1),
    ('Dr. André Teixeira', 1, 1),
    ('Dr. Fátima Moreira', 1, 1),
    ('Dr. Miguel Pinto', 1, 1),
    ('Dr. Carla Oliveira', 1, 1),
    ('Dr. Sofia Costa', 2, 1), -- Pediatria
    ('Dr. Bruno Almeida', 2, 1),
    ('Dr. Luísa Carvalho', 2, 1),
    ('Dr. Rafael Gomes', 2, 1),
    ('Dr. Mariana Lopes', 2, 1),
    ('Dr. Tiago Ribeiro', 3, 1), -- Cardiologia
    ('Dr. Teresa Sousa', 4, 1), -- Ginecologia
    ('Dr. Felipe Correia', 4, 1);

-- Médicos da Clínica B
INSERT INTO Medico (Nome, IdEspecialidade, IdClinica)
VALUES 
    ('Dr. Eduardo Silva', 1, 2), -- Clínica Geral
    ('Dr. Carolina Matos', 1, 2),
    ('Dr. Fernando Ramos', 1, 2),
    ('Dr. Patrícia Dias', 1, 2),
    ('Dr. Hugo Fernandes', 1, 2),
    ('Dr. Larissa Cunha', 4, 2), -- Ginecologia
    ('Dr. César Nogueira', 5, 2); -- Hematologia

-- Médicos da Clínica C
INSERT INTO Medico (Nome, IdEspecialidade, IdClinica)
VALUES 
    ('Dr. Aline Torres', 6, 3), -- Medicina Tropical
    ('Dr. Henrique Martins', 7, 3); -- Neurologia

-- Médicos da Clínica D
INSERT INTO Medico (Nome, IdEspecialidade, IdClinica)
VALUES 
    ('Dr. Paula Marques', 8, 4), -- Oftalmologia
    ('Dr. Renato Vieira', 8, 4),
    ('Dr. Cláudia Barros', 9, 4), -- Oncologia
    ('Dr. Gustavo Silva', 9, 4),
    ('Dr. Camila Santos', 9, 4),
    ('Dr. Roberto Oliveira', 10, 4); -- Otorrinolaringologia


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
