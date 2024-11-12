/*You must create a database & a table before send data into the database*/
DROP DATABASE IF EXISTS jdbc;
CREATE DATABASE jdbc;
USE jdbc;
CREATE TABLE IF NOT EXISTS customer(
    id INT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    address VARCHAR(45) NOT NULL,
    salary DOUBLE
);