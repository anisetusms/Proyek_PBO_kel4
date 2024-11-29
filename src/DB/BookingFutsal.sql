CREATE DATABASE bookingfutsal;
USE bookingfutsal;

-- Tabel Users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE ENUM('admin', 'user') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Lapangan
CREATE TABLE IF NOT EXISTS lapangan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_lapangan VARCHAR(100) NOT NULL,
    harga DECIMAL(10, 2) NOT NULL,
    STATUS ENUM('tersedia', 'tidak tersedia') DEFAULT 'tersedia',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Booking
CREATE TABLE IF NOT EXISTS booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    lapangan_id INT NOT NULL,
    tanggal_booking DATE NOT NULL,
    jam_mulai TIME NOT NULL,
    jam_selesai TIME NOT NULL,
    STATUS ENUM('terima', 'tolak', 'pending') DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (lapangan_id) REFERENCES lapangan(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Pembayaran (Opsional)
CREATE TABLE IF NOT EXISTS pembayaran (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    jumlah_bayar DECIMAL(10, 2) NOT NULL,
    tanggal_pembayaran TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_pembayaran ENUM('lunas', 'belum lunas') DEFAULT 'belum lunas',
    FOREIGN KEY (booking_id) REFERENCES booking(id) ON DELETE CASCADE
);

ALTER TABLE booking MODIFY STATUS VARCHAR(10);
ALTER TABLE booking MODIFY STATUS ENUM('Diterima', 'Ditolak', 'Pending') DEFAULT 'Pending';


