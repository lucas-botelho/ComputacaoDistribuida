-- Drop existing database if it exists (optional, for cleanup)
IF DB_ID('DistributedComputingDB') IS NOT NULL
BEGIN
    DROP DATABASE DistributedComputingDB;
END
GO

-- Create the database
CREATE DATABASE DistributedComputingDB;
GO

-- Use the created database
USE DistributedComputingDB;
GO

-- Drop existing tables if they exist to avoid conflicts
IF OBJECT_ID('dbo.Consulta', 'U') IS NOT NULL DROP TABLE Consulta;
IF OBJECT_ID('dbo.Medico', 'U') IS NOT NULL DROP TABLE Medico;
IF OBJECT_ID('dbo.Pessoa', 'U') IS NOT NULL DROP TABLE Pessoa;
IF OBJECT_ID('dbo.Especialidade', 'U') IS NOT NULL DROP TABLE Especialidade;
IF OBJECT_ID('dbo.Clinicas', 'U') IS NOT NULL DROP TABLE Clinicas;

-- Criação da tabela de Clínicas
CREATE TABLE Clinicas (
    IdClinica INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL,
    Morada VARCHAR(255)
);

-- Criação da tabela de Especialidades
CREATE TABLE Especialidade (
    IdEspecialidade INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL
);

-- Criação da tabela de Médicos
CREATE TABLE Medico (
    IdMedico INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL,
    IdEspecialidade INT,
    IdClinica INT,
    FOREIGN KEY (IdEspecialidade) REFERENCES Especialidade(IdEspecialidade),
    FOREIGN KEY (IdClinica) REFERENCES Clinicas(IdClinica)
);

-- Criação da tabela de Pessoas (Pacientes)
CREATE TABLE Pessoa (
    IdPessoa INT PRIMARY KEY IDENTITY(1,1),
    Nome VARCHAR(255) NOT NULL
);

-- Criação da tabela de Consultas
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

-- Script ends here
PRINT 'Database and tables created successfully!';
GO
