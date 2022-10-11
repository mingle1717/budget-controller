drop database if exists calculator;
create database calculator;
use calculator;

CREATE TABLE `appUser` (
    `user_id` INT AUTO_INCREMENT NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    `passhash` CHAR(60) NOT NULL,
    `email` VARCHAR(200) NOT NULL,
    `isDeleted` BOOLEAN NULL DEFAULT 0,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `Budget` (
    `budget_id` INT AUTO_INCREMENT NOT NULL,
    `budget_name` VARCHAR(60) NOT NULL,
    `balance` INT NOT NULL,
    PRIMARY KEY (`budget_id`)
);

CREATE TABLE `Category` (
    `category_id` INT AUTO_INCREMENT NOT NULL,
    `cat_name` VARCHAR(100) NOT NULL,
    `cat_percent` INT NOT NULL,
    `higher_limit` INT NOT NULL,
    `lower_limit` INT NOT NULL,
    `goal` BOOLEAN NOT NULL,
    `budget_id` INT NOT NULL,
    PRIMARY KEY (`category_id`)
);

CREATE TABLE `autoTrigger` (
    `auto_id` INT AUTO_INCREMENT NOT NULL,
    `trigger_date` DATE NOT NULL,
    `payment_amount` INT NOT NULL,
    `category_id` INT NOT NULL,
    PRIMARY KEY (`auto_id`)
);

CREATE TABLE `myTransaction` (
    `transaction_id` INT AUTO_INCREMENT NOT NULL,
    `transaction_name` VARCHAR(50) NOT NULL,
    `transaction_amount` INT NOT NULL,
    `transaction_description` VARCHAR(300) NOT NULL,
    `category_id` INT NOT NULL,
    `auto_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`transaction_id`)
);

CREATE TABLE `myRole` (
    `role_id` INT AUTO_INCREMENT NOT NULL,
    `role_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `userBudget` (
    `userBudget_id` INT AUTO_INCREMENT NOT NULL,
    `isOwner` BOOLEAN NOT NULL,
    `user_id` INT NOT NULL,
    `budget_id` INT NOT NULL,
    PRIMARY KEY (`userBudget_id`)
);

ALTER TABLE `appUser` ADD CONSTRAINT `fk_appUser_role_id` FOREIGN KEY(`role_id`)
REFERENCES `myRole` (`role_id`);

ALTER TABLE `Category` ADD CONSTRAINT `fk_Category_budget_id` FOREIGN KEY(`budget_id`)
REFERENCES `Budget` (`budget_id`);

ALTER TABLE `autoTrigger` ADD CONSTRAINT `fk_autoTrigger_category_id` FOREIGN KEY(`category_id`)
REFERENCES `Category` (`category_id`);

ALTER TABLE `myTransaction` ADD CONSTRAINT `fk_myTransaction_category_id` FOREIGN KEY(`category_id`)
REFERENCES `Category` (`category_id`);

ALTER TABLE `myTransaction` ADD CONSTRAINT `fk_myTransaction_auto_id` FOREIGN KEY(`auto_id`)
REFERENCES `autoTrigger` (`auto_id`);

ALTER TABLE `myTransaction` ADD CONSTRAINT `fk_myTransaction_user_id` FOREIGN KEY(`user_id`)
REFERENCES `appUser` (`user_id`);

ALTER TABLE `userBudget` ADD CONSTRAINT `fk_userBudget_user_id` FOREIGN KEY(`user_id`)
REFERENCES `appUser` (`user_id`);

ALTER TABLE `userBudget` ADD CONSTRAINT `fk_userBudget_budget_id` FOREIGN KEY(`budget_id`)
REFERENCES `Budget` (`budget_id`);

/*
Tracking:
1. Budget
2. Categories
3. Auto trigger
4. Transaction
5. User
6. Roles

Trigger Table (Thread check to ensure doesn't miss/doublepay)


1 , 2 Every budget can have multiple Categories
1 , 5 Many to Many with isOwner column
2 , 3 Every category has multiple auto trigger
2 , 4 Every category has multiple transactions
3 , 4 One trigger has multiple transactions (monthly)
4 , 5 Every transaction has 1 user, 1 user to many transactions
5 , 6 One role has many users 
*/







