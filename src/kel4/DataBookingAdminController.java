package kel4;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DataBookingAdminController implements Initializable {

    @FXML
    private TableView<Booking> tableView;

    @FXML
    private TableColumn<Booking, String> colNama;

    @FXML
    private TableColumn<Booking, String> colNamaLapangan;

    @FXML
    private TableColumn<Booking, LocalDate> colTanggalBooking;

    @FXML
    private TableColumn<Booking, String> colJamMulai;

    @FXML
    private TableColumn<Booking, String> colJamSelesai;

    @FXML
    private TableColumn<Booking, String> colStatus;

    @FXML
    private Button btnTerima;

    @FXML
    private Button btnTolak;

    @FXML
    private Button btnKembali;

    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNamaLapangan.setCellValueFactory(new PropertyValueFactory<>("namaLapangan"));
        colTanggalBooking.setCellValueFactory(new PropertyValueFactory<>("tanggalBooking"));
        colJamMulai.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        colJamSelesai.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadData(); // Memuat data dari database saat aplikasi diinisialisasi
    }

    // Fungsi untuk memuat data booking dari database
    private void loadData() {
        bookingList.clear();
        String sql = "SELECT b.id, u.nama AS nama_user, l.nama_lapangan, b.tanggal_booking, b.jam_mulai, b.jam_selesai, b.status "
                + "FROM booking b "
                + "JOIN users u ON b.user_id = u.id "
                + "JOIN lapangan l ON b.lapangan_id = l.id";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                bookingList.add(new Booking(
                        rs.getInt("id"),
                        rs.getString("nama_user"),
                        rs.getString("nama_lapangan"),
                        rs.getDate("tanggal_booking").toLocalDate(),
                        rs.getTime("jam_mulai").toLocalTime(),
                        rs.getTime("jam_selesai").toLocalTime(),
                        rs.getString("status")
                ));
            }
            tableView.setItems(bookingList);

        } catch (SQLException e) {
            System.err.println("Error saat memuat data: " + e.getMessage());
        }
    }

    // Fungsi untuk menyetujui booking
    @FXML
    private void handleTerima() {
        Booking selectedBooking = tableView.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            int bookingId = selectedBooking.getId(); // Mengambil ID booking
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("UPDATE booking SET status = ? WHERE id = ?")) {

                pstmt.setString(1, "Diterima");
                pstmt.setInt(2, bookingId);
                pstmt.executeUpdate();

                // Tampilkan notifikasi (opsional)
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking Diterima");
                alert.setHeaderText(null);
                alert.setContentText("Status booking berhasil diubah menjadi 'Diterima'.");
                alert.showAndWait();

                // Refresh data
                loadData();

            } catch (SQLException e) {
                System.err.println("Error saat menerima booking: " + e.getMessage());
            }
        } else {
            // Tampilkan pesan jika tidak ada item yang dipilih
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih booking yang ingin diterima.");
            alert.showAndWait();
        }
    }

    // Fungsi untuk menolak booking
    @FXML
    private void handleTolak() {
        Booking selectedBooking = tableView.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            updateStatus(selectedBooking.getId(), "Ditolak");
        } else {
            showAlert("Pilih Data", "Silakan pilih data booking yang ingin ditolak.");
        }
    }

    // Fungsi untuk memperbarui status booking di database
    private void updateStatus(int bookingId, String status) {
        String sql = "UPDATE booking SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                loadData(); // Refresh data di tabel
                showAlert("Sukses", "Status berhasil diperbarui menjadi: " + status);
            } else {
                showAlert("Gagal", "Gagal memperbarui status.");
            }

        } catch (SQLException e) {
            System.err.println("Error saat memperbarui status: " + e.getMessage());
        }
    }

    // Fungsi untuk kembali ke menu utama
    @FXML
    private void handleKembali() {
        Stage stage = (Stage) btnKembali.getScene().getWindow();
        SceneController.changeScene(stage, "MenuAdmin.fxml");
    }

    // Fungsi untuk menampilkan alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
