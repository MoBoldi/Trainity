-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 15. Jun 2020 um 02:41
-- Server-Version: 10.4.11-MariaDB
-- PHP-Version: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `trainity`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE `benutzer` (
  `id` int(3) NOT NULL,
  `vorname` varchar(36) NOT NULL,
  `nachname` varchar(36) NOT NULL,
  `email` varchar(90) NOT NULL,
  `passwort` varchar(36) NOT NULL,
  `nextWorkoutId` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`id`, `vorname`, `nachname`, `email`, `passwort`, `nextWorkoutId`) VALUES
(16, 'Benjamin', 'Golic', 'golicbenjamin.school@gmail.com', '9B26CF71DF797A2B44D7B004CCCC4BEC', NULL),
(17, 'Moritz', 'Weibold', 'moritz@weibold.cc', '4D2BE4B2DBF0B7ED234DB28F4EEC4590', 5),
(18, 'Test', 'Test', 'test@test.com', 'ADB1AB12F9690460D2B59D0BCF676CF8', NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `trainingseinheit`
--

CREATE TABLE `trainingseinheit` (
  `trainingseinheit_id` int(3) NOT NULL,
  `name` varchar(40) NOT NULL,
  `dauer` int(3) NOT NULL,
  `user_id` int(3) NOT NULL,
  `bildName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `trainingseinheit`
--

INSERT INTO `trainingseinheit` (`trainingseinheit_id`, `name`, `dauer`, `user_id`, `bildName`) VALUES
(1, 'Test05', 120, 1, NULL),
(2, 'Abs', 20, 17, NULL),
(3, 'Abs', 20, 17, NULL),
(4, 'Beine', 20, 17, NULL),
(5, 'Bizeps', 20, 17, NULL),
(6, 'test', 0, 17, '051-athlete-3.png'),
(7, 'miau', 0, 17, '051-athlete-7.png');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `trainingshistorie`
--

CREATE TABLE `trainingshistorie` (
  `trainingshistorie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `trainingseinheit_id` int(11) NOT NULL,
  `datum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `trainingshistorie`
--

INSERT INTO `trainingshistorie` (`trainingshistorie_id`, `user_id`, `trainingseinheit_id`, `datum`) VALUES
(1, 17, 1, '2020-06-10'),
(2, 17, 2, '2020-06-13'),
(3, 17, 3, '2020-06-11'),
(4, 17, 5, '2020-06-14');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `trainingsliste`
--

CREATE TABLE `trainingsliste` (
  `liste_id` int(3) NOT NULL,
  `trainingseinheit_id` int(11) NOT NULL,
  `trainingsuebung_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `trainingsliste`
--

INSERT INTO `trainingsliste` (`liste_id`, `trainingseinheit_id`, `trainingsuebung_id`) VALUES
(1, 1, 4),
(2, 1, 6),
(3, 1, 5),
(4, 1, 8),
(5, 1, 12),
(6, 1, 14),
(7, 1, 15),
(8, 1, 16),
(9, 2, 4),
(10, 1, 16),
(11, 1, 16),
(12, 1, 18),
(13, 2, 18);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `trainingsuebung`
--

CREATE TABLE `trainingsuebung` (
  `trainingsuebung_id` int(3) NOT NULL,
  `trainingsname` varchar(30) NOT NULL,
  `wiederholung` int(3) NOT NULL,
  `beschreibung` varchar(90) NOT NULL,
  `benutzer_id` int(4) NOT NULL,
  `bildName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `trainingsuebung`
--

INSERT INTO `trainingsuebung` (`trainingsuebung_id`, `trainingsname`, `wiederholung`, `beschreibung`, `benutzer_id`, `bildName`) VALUES
(4, 'hallo', 12, 'test', 1, NULL),
(5, 'JulianHaas01', 23, 'asdnaksd', 5, NULL),
(6, 'Liegestütze', 12, 'Die Liegestütze sind eine einfache', 5, NULL),
(7, 'Liegestütze', 12, 'snlkansdlknalkdn', 12, NULL),
(8, 'JulianTest3', 12, 'hsakdadkjad', 5, NULL),
(9, 'JulianTest3', 12, 'hsakdadkjad', 5, NULL),
(10, 'sd', 23, 's', 5, NULL),
(11, 'asd', 0, 'asdad', 5, NULL),
(12, 'aads', 23, 'ass', 5, NULL),
(13, 'as', 1, 'aas', 5, NULL),
(14, 'Testio', 11, 'testal', 5, NULL),
(15, 'Moritz', 10, 'sandlasnd', 5, NULL),
(16, 'test', 10, 'Miau', 17, '051-athlete-7.png'),
(17, 'test', 10, 'dfgsdfhgsdh', 17, '051-athlete-3.png'),
(18, 'miau', 10, 'odjfsj', 17, '051-athlete-4.png');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ziel`
--

CREATE TABLE `ziel` (
  `datum` date NOT NULL,
  `benutzer_id` int(10) NOT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT 'Status 1 = Training geplant\r\nStatus 0 = Training erledigt\r\nStatus null = kein Training an diesem Tag'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `ziel`
--

INSERT INTO `ziel` (`datum`, `benutzer_id`, `status`) VALUES
('2020-06-08', 1, 1),
('2020-06-09', 1, 1),
('2020-06-10', 1, NULL),
('2020-06-11', 1, 0),
('2020-06-12', 1, NULL),
('2020-06-13', 1, NULL),
('2020-06-14', 1, NULL),
('2020-06-15', 17, 1),
('2020-06-16', 17, NULL),
('2020-06-17', 17, NULL),
('2020-06-18', 17, NULL),
('2020-06-19', 17, NULL),
('2020-06-20', 17, 1),
('2020-06-21', 17, 1);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `trainingseinheit`
--
ALTER TABLE `trainingseinheit`
  ADD PRIMARY KEY (`trainingseinheit_id`);

--
-- Indizes für die Tabelle `trainingshistorie`
--
ALTER TABLE `trainingshistorie`
  ADD PRIMARY KEY (`trainingshistorie_id`);

--
-- Indizes für die Tabelle `trainingsliste`
--
ALTER TABLE `trainingsliste`
  ADD PRIMARY KEY (`liste_id`);

--
-- Indizes für die Tabelle `trainingsuebung`
--
ALTER TABLE `trainingsuebung`
  ADD UNIQUE KEY `trainingsuebung_id` (`trainingsuebung_id`);

--
-- Indizes für die Tabelle `ziel`
--
ALTER TABLE `ziel`
  ADD PRIMARY KEY (`datum`,`benutzer_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT für Tabelle `trainingseinheit`
--
ALTER TABLE `trainingseinheit`
  MODIFY `trainingseinheit_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT für Tabelle `trainingshistorie`
--
ALTER TABLE `trainingshistorie`
  MODIFY `trainingshistorie_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `trainingsliste`
--
ALTER TABLE `trainingsliste`
  MODIFY `liste_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT für Tabelle `trainingsuebung`
--
ALTER TABLE `trainingsuebung`
  MODIFY `trainingsuebung_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
