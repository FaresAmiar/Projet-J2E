SET FEEDBACK OFF
SET LINESIZE 150
SET PAGESIZE 40

REM **************************************************************************
REM BASE Bibliotheque
REM Auteur : Farès AMIAR & Yanis RABIA
REM ***************************************************************************

ALTER SESSION SET NLS_DATE_FORMAT = 'dd/mm/yyyy';

PROMPT --> DEBUT DU SCRIPT

REM ** Requête SQL de création des tables **

DROP TABLE Utilisateur CASCADE CONSTRAINTS PURGE
/
DROP TABLE Document CASCADE CONSTRAINTS PURGE
/

PROMPT CREATION DES TABLES

CREATE TABLE Utilisateur
(
NumUtilisateur INTEGER CONSTRAINT pk_Utilisateur PRIMARY KEY,
LoginUtilisateur VARCHAR(20),
PasswordUtilisateur VARCHAR2(20),
TypeUtilisateur INTEGER,
AdresseIP VARCHAR(15) DEFAULT ''
)
/

CREATE TABLE Document
(
NumDoc INTEGER CONSTRAINT pk_Document PRIMARY KEY,
NumUtilisateur INTEGER,
TitreDoc VARCHAR2(25) NOT NULL,
AuteurDoc VARCHAR2(30),
TypeDoc INTEGER,
StatutDoc VARCHAR2(20) DEFAULT 'disponible'
)
/



ALTER TABLE Document
ADD CONSTRAINT fk_Doc_Utilisateur FOREIGN KEY (NumUtilisateur) 
REFERENCES Utilisateur(NumUtilsateur)
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


REM ** Les Requêtes insertion de données

PROMPT INSERTION Abonnés

Insert into Utilisateur(NumUtilisateur,LoginUtilisateur,PasswordUtilisateur,TypeUtilisateur, adresseIP)
values(seq_Utilisateur.nextVal,'yanisdz','213',0, '10.1.0.1');

Insert into Utilisateur(NumUtilisateur,LoginUtilisateur,PasswordUtilisateur,TypeUtilisateur, adresseIP)
values(seq_Utilisateur.nextVal,'resfadz','dz',0, '10.1.0.2');

PROMPT insertion du bibliothecaire

Insert into Utilisateur(NumUtilisateur,LoginUtilisateur,PasswordUtilisateur,TypeUtilisateur, adresseIP)
values(seq_Utilisateur.nextVal,'brette','professeur',1, '10.1.0.3');

PROMPT insertion documents

Insert into Document(NumDoc,TitreDoc,AuteurDoc,TypeDoc) 
values(seq_Document.nextVal,'DBZ','Toriyama',0);

Insert into Document(NumDoc,NumUtilisateur,TitreDoc,AuteurDoc,TypeDoc,statutDoc) 
values(seq_Document.nextVal,1,'Seven','Flincher',0,'emprunte');

PROMPT --> SCRIPT COMPLETEMENT TERMINE

commit;

SET FEEDBACK ON