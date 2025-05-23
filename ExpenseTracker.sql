CREATE DATABASE expense_tracker;

USE expense_tracker;

CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category VARCHAR(100),
    expense_date DATE NOT NULL,
    notes TEXT
);
