USE DistributedComputingDB;
go

SELECT m.Nome AS NomeMedico, e.Nome AS Especialidade, c.Nome AS NomeClinica FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade JOIN Clinicas c ON m.IdClinica = c.IdClinica WHERE c.Nome = 'Clínica A';
SELECT m.Nome AS NomeMedico, e.Nome AS Especialidade, c.Nome AS NomeClinica FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade JOIN Clinicas c ON m.IdClinica = c.IdClinica WHERE c.Nome = 'Clínica B';
SELECT m.Nome AS NomeMedico, e.Nome AS Especialidade, c.Nome AS NomeClinica FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade JOIN Clinicas c ON m.IdClinica = c.IdClinica WHERE c.Nome = 'Clínica C';
SELECT m.Nome AS NomeMedico, e.Nome AS Especialidade, c.Nome AS NomeClinica FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade JOIN Clinicas c ON m.IdClinica = c.IdClinica WHERE c.Nome = 'Clínica D';

SELECT e.Nome AS Especialidade, COUNT(m.IdMedico) AS NumeroMedicos FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade WHERE m.IdClinica = (SELECT IdClinica FROM Clinicas WHERE Nome = 'Clínica A') GROUP BY e.Nome;
SELECT e.Nome AS Especialidade, COUNT(m.IdMedico) AS NumeroMedicos FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade WHERE m.IdClinica = (SELECT IdClinica FROM Clinicas WHERE Nome = 'Clínica B') GROUP BY e.Nome;
SELECT e.Nome AS Especialidade, COUNT(m.IdMedico) AS NumeroMedicos FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade WHERE m.IdClinica = (SELECT IdClinica FROM Clinicas WHERE Nome = 'Clínica C') GROUP BY e.Nome;
SELECT e.Nome AS Especialidade, COUNT(m.IdMedico) AS NumeroMedicos FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade WHERE m.IdClinica = (SELECT IdClinica FROM Clinicas WHERE Nome = 'Clínica D') GROUP BY e.Nome;

SELECT m.Nome AS NomeMedico, e.Nome AS Especialidade, c.Nome AS NomeClinica FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade JOIN Clinicas c ON m.IdClinica = c.IdClinica ORDER BY c.Nome, e.Nome;

SELECT c.Nome AS NomeClinica, COUNT(m.IdMedico) AS NumeroMedicos FROM Medico m JOIN Clinicas c ON m.IdClinica = c.IdClinica GROUP BY c.Nome;

SELECT e.Nome AS Especialidade, COUNT(m.IdMedico) AS NumeroMedicos FROM Medico m JOIN Especialidade e ON m.IdEspecialidade = e.IdEspecialidade GROUP BY e.Nome ORDER BY NumeroMedicos DESC;
