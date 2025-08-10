-- Kotlin Fullstack Template Database Initialization Script
-- This script runs automatically when the PostgreSQL container starts for the first time

-- Create users table - basic authentication template
CREATE TABLE users
(
    id           SERIAL8 PRIMARY KEY,
    email        varchar(255) NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    user_type    varchar(20)  NOT NULL DEFAULT 'user',
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    is_active    BOOLEAN      DEFAULT TRUE
);

-- Create a sample table for demonstration
CREATE TABLE sample_data
(
    id          SERIAL8 PRIMARY KEY,
    name        varchar(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_user_type ON users(user_type);

-- Insert sample data for testing
INSERT INTO users (email, password_hash, user_type) 
VALUES 
    ('user@example.com', '$2a$10$6cBwN.ViGdw4cSG2aXx2U.PdT3J3t5w5k7o9A3f4D6k8E5a2C1b0', 'user'),
    ('admin@example.com', '$2a$10$6cBwN.ViGdw4cSG2aXx2U.PdT3J3t5w5k7o9A3f4D6k8E5a2C1b0', 'admin');

INSERT INTO sample_data (name, description)
VALUES 
    ('Template Example', 'This is a sample data entry for the Kotlin Fullstack Template'),
    ('Hello World', 'Another example showing how to use the database with SQLDelight');