CREATE DATABASE DistributedComputingDB;
go
USE DistributedComputingDB;
go


-- Cria��o da tabela de Cl�nicas
CREATE TABLE Clinicas (
    IdClinica INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL,
    Morada VARCHAR(255)
);

-- Cria��o da tabela de Especialidades
CREATE TABLE Especialidade (
    IdEspecialidade INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL
);

-- Cria��o da tabela de M�dicos
CREATE TABLE Medico (
    IdMedico INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL,
    IdEspecialidade INT,
    IdClinica INT,
    FOREIGN KEY (IdEspecialidade) REFERENCES Especialidade(IdEspecialidade),
    FOREIGN KEY (IdClinica) REFERENCES Clinicas(IdClinica)
);

-- Cria��o da tabela de Pessoas (Pacientes)
CREATE TABLE Pessoa (
    IdPessoa INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL
);

-- Cria��o da tabela de Consultas
CREATE TABLE Consulta (
    IdConsulta INT PRIMARY KEY IDENTITY(1,1),
    IdClinica INT,
    IdMedico INT,
    IdPessoa INT,
    DataHora DATETIME NOT NULL,
    FOREIGN KEY (IdClinica) REFERENCES Clinicas(IdClinica),
    FOREIGN KEY (IdMedico) REFERENCES Medico(IdMedico),
    FOREIGN KEY (IdPessoa) REFERENCES Pessoa(IdPessoa)
);
