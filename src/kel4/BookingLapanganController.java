package kel4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.sql.*;

public class BookingLapanganController {

    @FXML
    private ComboBox<String> comboLapangan;
    @FXML
    private DatePicker tanggalBooking;
    @FXML
    private TextField txtJamMulai;
    @FXML
    private TextField txtJamSelesai;
    @FXML
    private Button btnBook;

    private Connection conn;
    private Stage stage;

    // Konstruktor untuk mengatur koneksi database
    public BookingLapanganController() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingfutsal", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Kesalahan Koneksi", "Tidak dapat terhubung ke database: " + e.getMessage(), AlertType.ERROR);
        }
    }

    // Menyiapkan ComboBox dengan daftar lapangan yang tersedia
    @FXML
    private void initialize() {
        if (conn == null) {
            showAlert("Kesalahan Koneksi", "Tidak dapat terhubung ke database.", AlertType.ERROR);
            return;
        }

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM lapangan WHERE status = 'tersedia'"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comboLapangan.getItems().add(rs.getString("nama_lapangan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal memuat data lapangan.", AlertType.ERROR);
        }
    }

    // Menangani aksi booking lapangan
    @FXML
    private void handleBooking(ActionEvent event) {
        // Validasi input
        if (comboLapangan.getSelectionModel().isEmpty() || tanggalBooking.getValue() == null
                || txtJamMulai.getText().isEmpty() || txtJamSelesai.getText().isEmpty()) {
            showAlert("Input Tidak Lengkap", "Mohon lengkapi semua field untuk melakukan booking.", AlertType.WARNING);
            return;
        }

        String lapanganName = comboLapangan.getValue();
        Date tanggal = Date.valueOf(tanggalBooking.getValue());

        // Validasi format waktu
        Time jamMulai = validateTime(txtJamMulai.getText());
        Time jamSelesai = validateTime(txtJamSelesai.getText());

        if (jamMulai == null || jamSelesai == null) {
            showAlert("Format Waktu Salah", "Format waktu harus sesuai dengan HH:mm.", AlertType.WARNING);
            return;
        }

        try {
            // Gunakan try-with-resources untuk menutup resource secara otomatis
            String lapanganQuery = "SELECT id, status FROM lapangan WHERE nama_lapangan = ?";
            try (PreparedStatement psLapangan = conn.prepareStatement(lapanganQuery)) {
                psLapangan.setString(1, lapanganName);
                try (ResultSet rsLapangan = psLapangan.executeQuery()) {

                    if (rsLapangan.next()) {
                        int lapanganId = rsLapangan.getInt("id");
                        String lapanganStatus = rsLapangan.getString("status");

                        if ("tidak tersedia".equals(lapanganStatus)) {
                            showAlert("Lapangan Tidak Tersedia", "Lapangan yang Anda pilih saat ini tidak tersedia.", AlertType.WARNING);
                            return;
                        }

                        // Mengambil ID user yang login
                        int userId = LoginController.getLoggedInUserId();

                        // Menyimpan data booking
                        String bookingQuery = "INSERT INTO booking (user_id, lapangan_id, tanggal_booking, jam_mulai, jam_selesai, status) "
                                + "VALUES (?, ?, ?, ?, ?, 'pending')";
                        try (PreparedStatement psBooking = conn.prepareStatement(bookingQuery)) {
                            psBooking.setInt(1, userId);  // Menggunakan ID pengguna yang login
                            psBooking.setInt(2, lapanganId);
                            psBooking.setDate(3, tanggal);
                            psBooking.setTime(4, jamMulai);
                            psBooking.setTime(5, jamSelesai);

                            int result = psBooking.executeUpdate();
                            if (result > 0) {
                                showAlert("Booking Berhasil", "Lapangan berhasil dibooking.", AlertType.INFORMATION);
                            } else {
                                showAlert("Booking Gagal", "Terjadi kesalahan saat menyimpan data booking.", AlertType.ERROR);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat memproses booking.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            // Periksa path file FXML untuk menu utama
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuUSer.fxml"));
            Scene scene = new Scene(loader.load());

            // Mendapatkan stage saat ini dan mengganti scene
            stage = (Stage) btnBook.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Gagal memuat menu utama.", AlertType.ERROR);
        }
    }

    private Time validateTime(String timeStr) {
        try {
            return Time.valueOf(timeStr + ":00");
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
