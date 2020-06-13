-- Generiert von Oracle SQL Developer Data Modeler 19.2.0.182.1216
--   am/um:        2020-05-31 21:05:42 MESZ
--   Site:      DB2/UDB 7.1
--   Typ:      DB2/UDB 7.1



CREATE TABLE aufteilung (
    trainingsplan_id      NUMERIC(10) NOT NULL,
    trainingseinheit_id   NUMERIC(10) NOT NULL,
    stelle                NUMERIC(3) NOT NULL
);

ALTER TABLE aufteilung ADD CONSTRAINT aufteilung_trainingsplan_pk PRIMARY KEY ( trainingsplan_id,
                                                                                trainingseinheit_id );

CREATE TABLE aufteilung_trainingseinheit (
    trainingseinheit_id   NUMERIC(10) NOT NULL,
    trainingsübung_id     NUMERIC(10) NOT NULL,
    stelle                NUMERIC(5) NOT NULL,
    anzahl                NUMERIC(3) NOT NULL
);

ALTER TABLE aufteilung_trainingseinheit ADD CONSTRAINT aufteilung_trainingseinheit_pk PRIMARY KEY ( trainingseinheit_id,
                                                                                                    trainingsübung_id );

CREATE TABLE benutzer (
    benutzer_id    NUMERIC(10) NOT NULL,
    vorname        VARCHAR(30) NOT NULL,
    nachname       VARCHAR(30) NOT NULL,
    email          VARCHAR(30) NOT NULL,
    passwort       VARCHAR(32) NOT NULL,
    status         SMALLINT NOT NULL,
    geburtsdatum   DATE
);

ALTER TABLE benutzer ADD CONSTRAINT benutzer_pk PRIMARY KEY ( benutzer_id );

CREATE TABLE bild 
    (
    bild_id   NUMERIC(10) NOT NULL,
    bild      BLOB NOT NULL,
    typ SMALLINT NOT NULL
)
;


ALTER TABLE bild ADD CONSTRAINT bild_pk PRIMARY KEY ( bild_id );

CREATE TABLE trainingseinheit (
    trainingseinheit_id   NUMERIC(10) NOT NULL,
    "Name"                VARCHAR(17) NOT NULL,
    erstelldatum          DATE NOT NULL,
    dauer                 time,
    benutzer_id           NUMERIC(10) NOT NULL,
    bild_id               NUMERIC(10) NOT NULL
);

ALTER TABLE trainingseinheit ADD CONSTRAINT trainingseinheit_pk PRIMARY KEY ( trainingseinheit_id );

CREATE TABLE trainingsplan (
    trainingsplan_id   NUMERIC(10) NOT NULL,
    "Name"             VARCHAR(18) NOT NULL,
    benutzer_id        NUMERIC(10) NOT NULL,
    bild_id            NUMERIC(10) NOT NULL
);

ALTER TABLE trainingsplan ADD CONSTRAINT trainingsplan_pk PRIMARY KEY ( trainingsplan_id );

CREATE TABLE trainingsuebung (
    trainingsuebung_id   NUMERIC(10) NOT NULL,
    "Name"               VARCHAR(18) NOT NULL,
    beschreibung         VARCHAR(30),
    besitzer             VARCHAR(10) NOT NULL,
    benutzer_id          NUMERIC(10) NOT NULL,
    bild_id              NUMERIC(10) NOT NULL
);

ALTER TABLE trainingsuebung ADD CONSTRAINT trainingsübung_pk PRIMARY KEY ( trainingsuebung_id );

CREATE TABLE ziel (
    benutzer_id           NUMERIC(10) NOT NULL,
    trainingseinheit_id   NUMERIC(10) NOT NULL,
    datum                 DATE NOT NULL,
    status                SMALLINT NOT NULL
);

ALTER TABLE ziel ADD CONSTRAINT ziel_pk PRIMARY KEY ( benutzer_id,
                                                      trainingseinheit_id );

ALTER TABLE Aufteilung_Trainingseinheit
    ADD CONSTRAINT aufteilung_trainingseinheit_fk FOREIGN KEY ( trainingseinheit_id )
        REFERENCES trainingseinheit ( trainingseinheit_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Aufteilung
    ADD CONSTRAINT aufteilung_trainingseinheit_fkv2 FOREIGN KEY ( trainingseinheit_id )
        REFERENCES trainingseinheit ( trainingseinheit_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Aufteilung
    ADD CONSTRAINT aufteilung_trainingsplan_fk FOREIGN KEY ( trainingsplan_id )
        REFERENCES trainingsplan ( trainingsplan_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Aufteilung_Trainingseinheit
    ADD CONSTRAINT aufteilung_trainingsübung_fk FOREIGN KEY ( trainingsübung_id )
        REFERENCES trainingsuebung ( trainingsuebung_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Trainingseinheit
    ADD CONSTRAINT trainingseinheit_benutzer_fk FOREIGN KEY ( benutzer_id )
        REFERENCES benutzer ( benutzer_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Trainingseinheit
    ADD CONSTRAINT trainingseinheit_bild_fk FOREIGN KEY ( bild_id )
        REFERENCES bild ( bild_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Trainingsplan
    ADD CONSTRAINT trainingsplan_benutzer_fk FOREIGN KEY ( benutzer_id )
        REFERENCES benutzer ( benutzer_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Trainingsplan
    ADD CONSTRAINT trainingsplan_bild_fk FOREIGN KEY ( bild_id )
        REFERENCES bild ( bild_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Trainingsuebung
    ADD CONSTRAINT trainingsübung_benutzer_fk FOREIGN KEY ( benutzer_id )
        REFERENCES benutzer ( benutzer_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Trainingsuebung
    ADD CONSTRAINT trainingsübung_bild_fk FOREIGN KEY ( bild_id )
        REFERENCES bild ( bild_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Ziel
    ADD CONSTRAINT ziel_benutzer_fk FOREIGN KEY ( benutzer_id )
        REFERENCES benutzer ( benutzer_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;

ALTER TABLE Ziel
    ADD CONSTRAINT ziel_trainingseinheit_fk FOREIGN KEY ( trainingseinheit_id )
        REFERENCES trainingseinheit ( trainingseinheit_id )
ON DELETE NO ACTION
    ON UPDATE
    no action;



-- Zusammenfassungsbericht für Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             8
-- CREATE INDEX                             0
-- ALTER TABLE                             20
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE STRUCTURED TYPE                   0
-- CREATE ALIAS                             0
-- CREATE BUFFERPOOL                        0
-- CREATE DATABASE                          0
-- CREATE DISTINCT TYPE                     0
-- CREATE INSTANCE                          0
-- CREATE NODE GROUP                        0
-- CREATE TABLESPACE                        0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
