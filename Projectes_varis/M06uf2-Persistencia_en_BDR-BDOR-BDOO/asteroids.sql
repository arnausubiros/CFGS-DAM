CREATE SCHEMA `asteroids` ;

use `asteroids` ;


CREATE TABLE `asteroids`.`enemics` (
  `idenemics` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(45) NULL,
  `velocitat` INT NULL,
  `defensa` INT NULL,
  `potencia` INT NULL,
  PRIMARY KEY (`idenemics`));



CREATE TABLE `asteroids`.`potenciador` (
  `idpotenciador` INT NOT NULL AUTO_INCREMENT,
  `vida` INT NULL,
  `efensa` INT NULL,
  PRIMARY KEY (`idpotenciador`));



CREATE TABLE `asteroids`.`nau` (
  `idnau` INT NOT NULL AUTO_INCREMENT,
  `nomNau` VARCHAR(45) NULL,
  `vidaNau` INT NULL,
  `defensaNau` INT NULL,
  `velocitatNau` INT NULL,
  `potenciaNau` INT NULL,
  `fotoNau` LONGBLOB NULL,
  PRIMARY KEY (`idnau`));



INSERT INTO `asteroids`.`nau` (`nomNau`, `vidaNau`, `defensaNau`, `velocitatNau`, `potenciaNau`) VALUES ('Starfighter-TI', '1000', '200', '100', '90');
INSERT INTO `asteroids`.`nau` (`nomNau`, `vidaNau`, `defensaNau`, `velocitatNau`, `potenciaNau`) VALUES ('TIE-Fighter', '2000', '300', '200', '110');
INSERT INTO `asteroids`.`nau` (`nomNau`, `vidaNau`, `defensaNau`, `velocitatNau`, `potenciaNau`) VALUES ('Naboo-N1', '3000', '400', '300', '220');
