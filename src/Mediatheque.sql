SET FEEDBACK OFF
SET LINESIZE 150
SET PAGESIZE 40

REM **************************************************************************
REM BASE AERO
REM Auteur : Farès AMIAR & Yanis RABIA
REM ***************************************************************************

ALTER SESSION SET NLS_DATE_FORMAT = 'dd/mm/yyyy';

PROMPT --> DEBUT DU SCRIPT

REM ** Requête SQL de création des tables **

DROP TABLE Utilisateur CASCADE CONSTRAINTS PURGE
/
DROP TABLE Document CASCADE CONSTRAINTS PURGE
/
DROP TABLE DocumentsUtilisateurs CASCADE CONSTRAINTS PURGE
/

PROMPT CREATION DES TABLES

CREATE TABLE Utilisateur
(
NumUtilisateur INTEGER CONSTRAINT pk_Utilisateur PRIMARY KEY,
LoginUtilisateur VARCHAR(20),
PasswordUtilisateur VARCHAR2(20),
TypeUtilisateur INTEGER
)
/

CREATE TABLE Document
(
NumDoc INTEGER CONSTRAINT pk_Document PRIMARY KEY,
TitreDoc VARCHAR2(25) NOT NULL,
AuteurDoc VARCHAR2(30),
TypeDoc INTEGER
)
/

CREATE TABLE DocumentsUtilisateurs 
(
NumDoc INTEGER,
NumUtilisateur INTEGER
)


ALTER TABLE DocumentsUtilisateurs
ADD CONSTRAINT pk_DocumentUtilisateur
	PRIMARY KEY(NumDoc,NumUtilisateur)
ADD CONSTRAINT fk_NumUtlisateur
	FOREIGN KEY(NumUtilisateur)
	REFERENCES Utilisateur(NumUtilisateur)
	ON DELETE CASCADE
ADD CONSTRAINT fk_NumDoc
	FOREIGN KEY(NumDoc)
	REFERENCES Document(NumDoc)
	ON DELETE CASCADE
/


REM ** Ordres SQL de création de Séquences  **

PROMPT CREATION DES SEQUENCES

DROP SEQUENCE seq_Document
/
DROP SEQUENCE seq_Utilisateur
/


CREATE SEQUENCE seq_Utilisateur start with 0 Minvalue 0
/
CREATE SEQUENCE seq_Document start with 0 Minvalue 0
/


REM ** Les Requêtes d'insertion de données

PROMPT INSERTION D'Abonnés

Insert into Utilisateur(NumUtilisateur,LoginUtilisateur,PasswordUtilisateur,TypeUtilisateur)
values(seq_Utilisateur.nextVal,"yanisdz","213",0);

Insert into UtilisateurNumUtilisateur,LoginUtilisateur,PasswordUtilisateur,TypeUtilisateur)
values(seq_Utilisateur.nextVal,"resfadz","dz",0);

PROMPT insertion du bibliothecaire

Insert into UtilisateurNumUtilisateur,LoginUtilisateur,PasswordUtilisateur,TypeUtilisateur)
values(seq_Utilisateur.nextVal,"brette","professeur",1);

PROMPT insertion documents

Insert into Document(NumDoc,TitreDoc,AuteurDoc,TypeDoc) 
values(seq_Document.nextVal,"DBZ","Toriyama",0);

Insert into Document(NumDoc,TitreDoc,AuteurDoc,TypeDoc) 
values(seq_Document.nextVal,"Seven","Flincher",0);

PROMPT --> SCRIPT COMPLETEMENT TERMINE

commit;

SET FEEDBACK ON
