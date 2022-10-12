drop database if exists calculator_test;
create database calculator_test;
USE calculator_test;

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


DELETE FROM Budget;
ALTER TABLE Budget AUTO_INCREMENT = 1;

INSERT INTO Budget (budget_name, balance)
VALUES
('Spaghetti Family' , '10000'),
('State Farm' , '0'),
('Broke' , '10'),
('Help' , '999'),
('Rabbit' , '1000.50'),
('Low', '1');


DELETE FROM myRole;
ALTER TABLE myRole AUTO_INCREMENT = 1;

INSERT INTO myRole (role_name)
VALUES
('Admin'),
('Member');


DELETE FROM appUser;
ALTER TABLE appUser AUTO_INCREMENT = 1;

INSERT INTO appUser (username, passhash, email, isDeleted, role_id)
VALUES
('bobthebob' , '$2a$10$92/B/ItAc4/Mrn1UbqPNf.q9ixOcYNc2KrkA60tZVPI5qvJ7Cg3qe' , 'bobthebob123@gmail.com', False, '1'),
('kendy' , '$2a$10$4Wml1NOkMTD7Rp1m3RRiDuOyRDo3qlRhY.3HeIVrXG8GADM70iwom' , 'kendy@dev-10.com', False, '1'),
('ryan' , '$2a$10$zAwLjZgm5NS7w9KRPtO6c.88KX6Wx3TT/GLsHb4azRim66r4ggZG.' , 'ryan@dev-10.com', False, '1'),
('cristian' , '$2a$10$ODjwk.5kRpfn1N90i7lnd.AApC8zlWWRTiOsnRSxVIZallZwJo/uq' , 'cristian@dev-10.com', False, '1'),
('fakemember' , '$2a$10$NAj5oxiRD4ymrsOEunLVoedTuO.xpTXVMfs68AS098QGpmPgOnK42 ', 'fakemember@gmail.com', False, '2');

DELETE FROM Category;
ALTER TABLE Category AUTO_INCREMENT = 1;

INSERT INTO Category (cat_name, cat_percent, higher_limit, lower_limit, goal, budget_id)
VALUES
('Entertainment', '25', '500', '250', True, '1'),
('Food', '10', '200', '100', False, '2'),
('Misc', '5', '1000', '250', True, '3'),
('Rent', '10', '1000', '550', True, '4'),
('Drinks', '5', '100', '300', False, '5');

DELETE FROM userBudget;
ALTER TABLE userBudget AUTO_INCREMENT = 1;

INSERT INTO userBudget (isOwner, user_id, budget_id)
VALUES
(True, '1', '1'),
(True, '2', '2'),
(True, '3', '3'),
(True, '4', '4'),
(False, '4', '3'),
(False, '4', '2'),
(False, '4', '1');

DELETE FROM autoTrigger;
ALTER TABLE autoTrigger AUTO_INCREMENT = 1;

INSERT INTO autoTrigger (trigger_date, payment_amount, category_id)
VALUES
('2022-11-01', 500, 1),
('2022-12-15', 800, 2),
('2021-10-31', 90.50, 3),
('2021-11-01', 500, 1),
('2021-12-15', 800, 2),
('2021-10-31', 90.50, 3),
('2021-11-01', 500, 1),
('2022-12-15', 800, 2),
('2022-10-31', 90.50, 3),
('2022-10-15', 1000, 1);

DELETE FROM myTransaction;
ALTER TABLE myTransaction AUTO_INCREMENT = 1;

INSERT INTO myTransaction (transaction_name, transaction_amount, transaction_description, category_id, auto_id, user_id)
VALUES
('McDonalds', '12.50', 'Food', '1', '1', '1'),
('Rent', '850', 'Rent', '3', '3', '3'),
('Movie', '50', 'Entertainment', '1', '2', '4'),
('Bar', '100', 'Entertainment', '2', '4', '2'),
('Hiking', '200', 'Camping', '4', '3', '3');





