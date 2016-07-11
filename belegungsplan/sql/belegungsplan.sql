SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `belegungsplan` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `belegungsplan` ;

-- -----------------------------------------------------
-- Table `belegungsplan`.`Vermieter`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `belegungsplan`.`Vermieter` (
  `pid` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `vorname` VARCHAR(45) NULL ,
  `adresse` VARCHAR(45) NULL ,
  `plz` INT(4) NULL ,
  `ort` VARCHAR(45) NULL ,
  `telefon` VARCHAR(45) NULL ,
  `natel` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `benutzername` VARCHAR(45) NOT NULL ,
  `passwort` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`pid`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `belegungsplan`.`Objekte`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `belegungsplan`.`Objekte` (
  `oid` INT NOT NULL AUTO_INCREMENT ,
  `bezeichnung` VARCHAR(45) NOT NULL ,
  `zusatzinfo` VARCHAR(45) NULL ,
  `groesse` VARCHAR(45) NOT NULL ,
  `pid` INT NOT NULL ,
  PRIMARY KEY (`oid`) ,
  INDEX `fk_Objekte_Vermieter1` (`pid` ASC) ,
  CONSTRAINT `fk_Objekte_Vermieter1`
    FOREIGN KEY (`pid` )
    REFERENCES `belegungsplan`.`Vermieter` (`pid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `belegungsplan`.`Belegungsplan`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `belegungsplan`.`Belegungsplan` (
  `timestamp` TIMESTAMP NOT NULL ,
  `oid` INT NOT NULL ,
  PRIMARY KEY (`timestamp`, `oid`) ,
  INDEX `fk_Belegungsplan_Objekte` (`oid` ASC) ,
  CONSTRAINT `fk_Belegungsplan_Objekte`
    FOREIGN KEY (`oid` )
    REFERENCES `belegungsplan`.`Objekte` (`oid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
