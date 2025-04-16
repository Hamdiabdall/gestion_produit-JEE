-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS gestionproduits;

-- Use the database
USE gestionproduits;

-- Create the products table
CREATE TABLE IF NOT EXISTS produit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    quantite INT NOT NULL,
    prix DOUBLE NOT NULL
);

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

-- Create the orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DOUBLE NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create the order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES produit(id)
);

-- Insert the default admin user
INSERT INTO users (username, password, email, role)
VALUES ('admin', 'admin123', 'admin@example.com', 'ADMIN')
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    email = VALUES(email),
    role = VALUES(role);

-- Insert some sample products
INSERT INTO produit (nom, quantite, prix) VALUES
('iPhone 14', 10, 999.99),
('Samsung Galaxy S23', 15, 899.99),
('MacBook Pro', 5, 1499.99),
('Dell XPS 15', 8, 1299.99),
('Sony PlayStation 5', 3, 499.99),
('Nintendo Switch', 12, 299.99),
('Apple Watch Series 8', 20, 399.99),
('Bose QuietComfort Earbuds', 25, 249.99),
('LG OLED 4K TV', 7, 1999.99),
('Canon EOS R6', 4, 2499.99);

-- Create database indexes for better performance
CREATE INDEX idx_product_name ON produit(nom);
CREATE INDEX idx_order_user ON orders(user_id);
CREATE INDEX idx_order_status ON orders(status);
CREATE INDEX idx_orderitem_order ON order_items(order_id);
CREATE INDEX idx_orderitem_product ON order_items(product_id);
CREATE INDEX idx_user_role ON users(role); 