-- ============================================================
-- Bus Reservation System - Database Schema & Seed Data
-- ============================================================

CREATE DATABASE IF NOT EXISTS bus_reservation;
USE bus_reservation;

-- ------------------------------------------------------------
-- Drop existing tables (in order respecting foreign key constraints)
-- ------------------------------------------------------------
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS buses;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS users;

-- ------------------------------------------------------------
-- Table: users
-- ------------------------------------------------------------
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  phone VARCHAR(15),
  password_hash VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ------------------------------------------------------------
-- Table: routes
-- ------------------------------------------------------------
CREATE TABLE routes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  origin VARCHAR(100) NOT NULL,
  destination VARCHAR(100) NOT NULL,
  distance_km INT
);

-- ------------------------------------------------------------
-- Table: buses
-- ------------------------------------------------------------
CREATE TABLE buses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  bus_name VARCHAR(100) NOT NULL,
  bus_number VARCHAR(20) NOT NULL,
  bus_type ENUM('AC','NON_AC','SLEEPER','SEMI_SLEEPER') NOT NULL,
  total_seats INT DEFAULT 40,
  route_id BIGINT NOT NULL,
  departure_time TIME NOT NULL,
  arrival_time TIME NOT NULL,
  fare DECIMAL(10,2) NOT NULL,
  operating_days VARCHAR(100) DEFAULT 'MON,TUE,WED,THU,FRI,SAT,SUN',
  FOREIGN KEY (route_id) REFERENCES routes(id) ON DELETE CASCADE,
  INDEX idx_buses_route_id (route_id)
);

-- ------------------------------------------------------------
-- Table: bookings
-- ------------------------------------------------------------
CREATE TABLE bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  passenger_name VARCHAR(100) NOT NULL,
  passenger_email VARCHAR(100) NOT NULL,
  passenger_phone VARCHAR(15),
  bus_id BIGINT NOT NULL,
  travel_date DATE NOT NULL,
  seat_numbers TEXT NOT NULL,  -- stores JSON array like [1,2,3]
  total_fare DECIMAL(10,2) NOT NULL,
  status ENUM('CONFIRMED','CANCELLED') DEFAULT 'CONFIRMED',
  booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (bus_id) REFERENCES buses(id) ON DELETE CASCADE,
  INDEX idx_bookings_lookup (bus_id, travel_date, status)
);

-- ------------------------------------------------------------
-- Seed Data: users
-- ------------------------------------------------------------
INSERT INTO users (name, email, phone) VALUES
('Rahul Sharma', 'rahul@example.com', '9876543210'),
('Priya Patel', 'priya@example.com', '9876543211'),
('Arjun Kumar', 'arjun@example.com', '9876543212');

-- ------------------------------------------------------------
-- Seed Data: routes (8 routes, 4 bidirectional pairs)
-- ------------------------------------------------------------
INSERT INTO routes (id, origin, destination, distance_km) VALUES
(1, 'Delhi', 'Mumbai', 1400),
(2, 'Mumbai', 'Delhi', 1400),
(3, 'Bangalore', 'Chennai', 350),
(4, 'Chennai', 'Bangalore', 350),
(5, 'Hyderabad', 'Pune', 560),
(6, 'Pune', 'Hyderabad', 560),
(7, 'Jaipur', 'Delhi', 280),
(8, 'Delhi', 'Jaipur', 280);

-- ------------------------------------------------------------
-- Seed Data: buses (12 buses across different routes)
-- ------------------------------------------------------------
INSERT INTO buses (id, bus_name, bus_number, bus_type, total_seats, route_id, departure_time, arrival_time, fare, operating_days) VALUES
(1, 'Royal Cruiser', 'KA-01-AB-1234', 'AC', 40, 1, '18:00:00', '06:00:00', 1299.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(2, 'Star Express', 'KA-01-CD-5678', 'SLEEPER', 40, 1, '20:00:00', '08:00:00', 899.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(3, 'Galaxy Travels', 'KA-01-EF-9012', 'SEMI_SLEEPER', 40, 1, '21:00:00', '09:00:00', 699.00, 'MON,TUE,WED,THU,FRI'),
(4, 'Royal Cruiser Return', 'MH-01-AB-4321', 'AC', 40, 2, '19:00:00', '07:00:00', 1299.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(5, 'Star Express Return', 'MH-01-CD-8765', 'NON_AC', 40, 2, '20:30:00', '08:30:00', 599.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(6, 'South Star', 'KA-02-GH-3456', 'AC', 40, 3, '06:00:00', '12:00:00', 499.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(7, 'Express Liner', 'KA-02-IJ-7890', 'NON_AC', 40, 3, '08:00:00', '14:00:00', 299.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(8, 'South Star Return', 'TN-01-GH-6543', 'AC', 40, 4, '14:00:00', '20:00:00', 499.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(9, 'Deccan Queen', 'TS-01-KL-1122', 'SLEEPER', 40, 5, '22:00:00', '06:00:00', 799.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(10, 'Deccan Queen Return', 'MH-02-KL-2211', 'SLEEPER', 40, 6, '22:00:00', '06:00:00', 799.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(11, 'Pink City Express', 'RJ-01-MN-3344', 'AC', 40, 7, '05:00:00', '10:00:00', 399.00, 'MON,TUE,WED,THU,FRI,SAT,SUN'),
(12, 'Pink City Express', 'DL-01-MN-4433', 'AC', 40, 8, '16:00:00', '21:00:00', 399.00, 'MON,TUE,WED,THU,FRI,SAT,SUN');

-- ------------------------------------------------------------
-- Seed Data: sample bookings
-- ------------------------------------------------------------
INSERT INTO bookings (passenger_name, passenger_email, passenger_phone, bus_id, travel_date, seat_numbers, total_fare, status) VALUES
('Rahul Sharma', 'rahul@example.com', '9876543210', 1, '2026-07-26', '[1,2,5,6]', 5196.00, 'CONFIRMED'),
('Priya Patel', 'priya@example.com', '9876543211', 1, '2026-07-26', '[11,12,15]', 3897.00, 'CONFIRMED'),
('Arjun Kumar', 'arjun@example.com', '9876543212', 6, '2026-07-26', '[3,4,7,8]', 1996.00, 'CONFIRMED');
