
/*

drop table trainingsuebung tCASCADE CONSTRAINTS;





CREATE TABLE trainingsuebung (
    trainingsuebung_id   INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    trainingsname               VARCHAR(30),
    b         INT NOT NULL,
    beschreibung         VARCHAR(30),
    benutzer_id          INT NOT NULL,
    bild_id              INT
);

*/


-- insert into trainingsuebung(trainingsname,wiederholung, beschreibung, benutzer_id, bild_id )VALUES('julian', 5,'asdasd', 10, 5);


--insert into trainingsuebung VALUES()


INSERT INTO `trainingsuebung` (`trainingsuebung_id`, `trainingsname`, `wiederholung`, `beschreibung`, `benutzer_id`, `bild_id`) VALUES (NULL, 'hallo', '12', 'test', '1', '2');
select * from trainingsuebung;
