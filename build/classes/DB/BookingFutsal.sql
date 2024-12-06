-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2024 at 02:19 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

<<<<<<< Updated upstream
--
-- Database: `bookingfutsal`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `lapangan_id` int(11) NOT NULL,
  `tanggal_booking` date NOT NULL,
  `jam_mulai` time NOT NULL,
  `jam_selesai` time NOT NULL,
  `status` enum('Diterima','Ditolak','Pending') DEFAULT 'Pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `user_id`, `lapangan_id`, `tanggal_booking`, `jam_mulai`, `jam_selesai`, `status`, `created_at`) VALUES
(1, 1, 1, '2024-11-25', '10:10:00', '11:20:00', 'Ditolak', '2024-11-24 14:04:22'),
(2, 1, 1, '2024-11-28', '10:10:00', '12:30:00', 'Diterima', '2024-11-25 03:26:30'),
(4, 1, 1, '2024-11-26', '18:10:00', '20:30:00', 'Diterima', '2024-11-25 08:48:35'),
(5, 1, 2, '2024-11-29', '20:20:00', '18:18:00', 'Ditolak', '2024-11-25 11:42:28'),
(6, 3, 2, '2024-11-28', '10:10:00', '20:12:00', 'Ditolak', '2024-11-25 13:13:44'),
(7, 3, 2, '2024-11-29', '19:10:00', '20:20:00', 'Diterima', '2024-11-25 13:15:42'),
(8, 1, 3, '2024-11-29', '14:30:00', '16:50:00', 'Pending', '2024-11-26 14:05:42'),
(11, 1, 3, '2024-11-27', '03:10:00', '04:20:00', 'Pending', '2024-11-26 14:30:58'),
(14, 1, 2, '2024-11-30', '14:29:00', '17:30:00', 'Diterima', '2024-11-28 06:18:42'),
(16, 4, 3, '2024-11-29', '12:12:00', '14:14:00', 'Diterima', '2024-11-28 07:06:45'),
(17, 1, 1, '2024-11-30', '10:10:00', '19:10:00', 'Pending', '2024-11-28 07:25:10');

-- --------------------------------------------------------

--
-- Table structure for table `lapangan`
--

CREATE TABLE `lapangan` (
  `id` int(11) NOT NULL,
  `nama_lapangan` varchar(100) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `status` enum('tersedia','tidak tersedia') DEFAULT 'tersedia',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lapangan`
--

INSERT INTO `lapangan` (`id`, `nama_lapangan`, `harga`, `status`, `created_at`) VALUES
(1, 'Lapangan2', '150000.00', 'tersedia', '2024-11-24 14:03:23'),
(2, 'lapangan1', '200000.00', 'tersedia', '2024-11-25 09:00:23'),
(3, 'Lapangan Pniel', '250000.00', 'tersedia', '2024-11-25 13:57:29');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `jumlah_bayar` decimal(10,2) NOT NULL,
  `tanggal_pembayaran` timestamp NOT NULL DEFAULT current_timestamp(),
  `status_pembayaran` enum('lunas','belum lunas') DEFAULT 'belum lunas'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','user') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama`, `email`, `password`, `role`, `created_at`) VALUES
(1, 'anisetus', 'anisetus@gmail.com', 'manalu1', 'user', '2024-11-24 07:53:16'),
(2, 'admin', 'admin@gmail.com', 'admin1', 'admin', '2024-11-24 07:58:43'),
(3, 'user1', 'user1@gmail.com', 'user1', 'user', '2024-11-25 08:06:25'),
(4, 'tus1', 'tus@gmail.com', 'tus1', 'user', '2024-11-28 06:53:22'),
(5, 'tua', 'tua@gmail.com', 'msbreew', 'user', '2024-11-28 08:43:18');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `lapangan_id` (`lapangan_id`);

--
-- Indexes for table `lapangan`
--
ALTER TABLE `lapangan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `lapangan`
--
ALTER TABLE `lapangan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`lapangan_id`) REFERENCES `lapangan` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
=======
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


>>>>>>> Stashed changes
