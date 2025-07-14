-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Utenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utenti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(31) NOT NULL,
  `amministratore` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`Raccolte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Raccolte` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titolo` VARCHAR(25) NOT NULL,
  `descrizione` VARCHAR(255) NULL DEFAULT NULL,
  `utente` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_raccolta_utente_idx` (`utente` ASC) VISIBLE,
  CONSTRAINT `FK_raccolta_utente`
    FOREIGN KEY (`utente`)
    REFERENCES `mydb`.`Utenti` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`Poesie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Poesie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titolo` VARCHAR(25) NOT NULL,
  `tag` VARCHAR(255) NOT NULL,
  `body` VARCHAR(500) NOT NULL,
  `visibilita` TINYINT NOT NULL,
  `dataPubblicazione` DATE NOT NULL,
  `contatoreLike` INT NOT NULL,
  `autore` INT NOT NULL,
  `raccolta` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_poesia_utente_idx` (`autore` ASC) VISIBLE,
  INDEX `FK_poesia_raccolta_idx` (`raccolta` ASC) VISIBLE,
  CONSTRAINT `FK_poesia_raccolta`
    FOREIGN KEY (`raccolta`)
    REFERENCES `mydb`.`Raccolte` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_poesia_utente`
    FOREIGN KEY (`autore`)
    REFERENCES `mydb`.`Utenti` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`Apprezzamenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Apprezzamenti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `autore` INT NOT NULL,
  `poesia` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_apprezzamento_poesia_idx` (`poesia` ASC) VISIBLE,
  INDEX `FK_apprezzamento_autore_idx` (`autore` ASC) VISIBLE,
  CONSTRAINT `FK_apprezzamento_autore`
    FOREIGN KEY (`autore`)
    REFERENCES `mydb`.`Utenti` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_apprezzamento_poesia`
    FOREIGN KEY (`poesia`)
    REFERENCES `mydb`.`Poesie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 53
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`Commenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Commenti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `testo` VARCHAR(255) NOT NULL,
  `data` DATE NOT NULL,
  `poesia` INT NOT NULL,
  `autore` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_commento_poesia_idx` (`poesia` ASC) VISIBLE,
  INDEX `FK_commento_utente_idx` (`autore` ASC) VISIBLE,
  CONSTRAINT `FK_commento_poesia`
    FOREIGN KEY (`poesia`)
    REFERENCES `mydb`.`Poesie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_commento_utente`
    FOREIGN KEY (`autore`)
    REFERENCES `mydb`.`Utenti` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`ProfiliPersonali`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ProfiliPersonali` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(40) NULL DEFAULT NULL,
  `cognome` VARCHAR(40) NULL DEFAULT NULL,
  `immagineProfilo` VARCHAR(255) NULL DEFAULT NULL,
  `biografia` VARCHAR(255) NULL DEFAULT NULL,
  `nickname` VARCHAR(28) NOT NULL,
  `dataNascita` DATE NULL DEFAULT NULL,
  `utente` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `utente_UNIQUE` (`utente` ASC) VISIBLE,
  CONSTRAINT `FK_profilo_utente`
    FOREIGN KEY (`utente`)
    REFERENCES `mydb`.`Utenti` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` DATE NOT NULL,
  `dataInizio` DATE NOT NULL,
  `dataFine` DATE NOT NULL,
  `numPoesiePubblicate` INT NULL DEFAULT NULL,
  `leadUtenti` VARCHAR(255) NULL DEFAULT NULL,
  `leadTag` VARCHAR(255) NULL DEFAULT NULL,
  `leadPoesie` VARCHAR(255) NULL DEFAULT NULL,
  `autore` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_report_utente_idx` (`autore` ASC) VISIBLE,
  CONSTRAINT `FK_report_utente`
    FOREIGN KEY (`autore`)
    REFERENCES `mydb`.`Utenti` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
