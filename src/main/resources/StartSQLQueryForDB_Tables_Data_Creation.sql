--For creation a DB with two tables for testing the app. Below presented a simple code for initialize the DB/Tables and fill by data;

CREATE DATABASE pharmacydb_test;
USE pharmacydb_test;

CREATE TABLE drug_table (
  drug_id INT NOT NULL PRIMARY KEY,
  drug_name VARCHAR(255) NOT NULL,
  drug_manufacturer VARCHAR(255) NOT NULL,
  drug_prod_date DATE NOT NULL,
  drug_expr_date DATE NOT NULL,
  drug_description VARCHAR(1000) NOT NULL,
  drug_quantity INT NOT NULL,
  drug_price NUMERIC NOT NULL,
  drug_recipe BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE users (
  user_id INT NOT NULL PRIMARY KEY,
  user_name VARCHAR(100) NOT NULL,
  user_surname VARCHAR(100) NOT NULL,
  user_login VARCHAR(50) NOT NULL UNIQUE,
  user_password VARCHAR(50) NOT NULL,
  user_role TEXT NOT NULL
);

INSERT INTO drug_table(drug_name, drug_manufacturer,drug_prod_date, drug_expr_date, drug_description, drug_quantity, drug_price, drug_recipe)
VALUES 
('Aspirin', 'Bayer', '2021-01-01', '2023-01-01', 'Pain reliever and fever reducer', 100, 5.99, false),
('Lisinopril', 'AstraZeneca', '2020-03-01', '2023-03-01', 'Blood pressure medication', 50, 10.99, true),
('Ibuprofen', 'Advil', '2022-02-01', '2024-02-01', 'Pain reliever and fever reducer', 75, 4.99, false),
('Lorazepam', 'Teva Pharmaceuticals', '2021-05-01', '2024-05-01', 'Anxiety medication', 30, 15.99, true),
('Metformin', 'Merck', '2022-03-01', '2024-03-01', 'Diabetes medication', 60, 7.99, true),
('Simvastatin', 'Merck', '2020-08-01', '2023-08-01', 'Cholesterol medication', 40, 9.99, true),
('Levothyroxine', 'Mylan', '2021-10-01', '2024-10-01', 'Thyroid medication', 90, 12.99, true),
('Omeprazole', 'Prilosec', '2022-01-01', '2024-01-01', 'Heartburn medication', 25, 8.99, false),
('Prednisone', 'Deltasone', '2021-07-01', '2023-07-01', 'Anti-inflammatory medication', 35, 6.99, true),
('Albuterol', 'ProAir', '2022-04-01', '2024-04-01', 'Asthma medication', 20, 19.99, true);

INSERT INTO users (user_name, user_surname, user_login, user_password, user_role)
VALUES 
('Admin', 'Admin', 'admin', 'admin', 'admin'),
('Pharmacist','Pharmacist','pharmacist','pharmacist','pharmacist');