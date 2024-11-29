package kel4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.Stage;

public class LapanganController {

    @FXML
    private TableView<Lapangan> tableView;

    @FXML
    private TableColumn<Lapangan, String> colNama;

    @FXML
    private TableColumn<Lapangan, Double> colHarga;

    @FXML
    private TableColumn<Lapangan, String> colStatus;

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtHarga;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private Button btnTambah;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnKembali;

    private ObservableList<Lapangan> lapanganList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaLapangan"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        comboStatus.setItems(FXCollections.observableArrayList("tersedia", "tidak tersedia"));

        loadData(); // Muat data lapangan dari database
    }

    // Fungsi untuk memuat data dari database
    private void loadData() {
        lapanganList.clear();
        String sql = "SELECT * FROM lapangan";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lapanganList.add(new Lapangan(
                        rs.getInt("id"),
                        rs.getString("nama_lapangan"),
                        rs.getDouble("harga"),
                        rs.getString("status")
                ));
            }
            tableView.setItems(lapanganList);

        } catch (SQLException e) {
            System.err.println("Error saat memuat data: " + e.getMessage());
        }
    }

    // Fungsi untuk menambahkan lapangan
    @FXML
    private void handleTambah() {
        String nama = txtNama.getText();
        String hargaText = txtHarga.getText();
        String status = comboStatus.getValue();

        if (nama.isEmpty() || hargaText.isEmpty() || status == null) {
            showAlert("Peringatan", "Semua field harus diisi.");
            return;
        }

        double harga;
        try {
            harga = Double.parseDouble(hargaText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga harus berupa angka.");
            return;
        }

        String sql = "INSERT INTO lapangan (nama_lapangan, harga, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nama);
            pstmt.setDouble(2, harga);
            pstmt.setString(3, status);
            pstmt.executeUpdate();

            showAlert("Sukses", "Lapangan berhasil ditambahkan.");
            clearFields();
            loadData();

        } catch (SQLException e) {
            System.err.println("Error saat menambahkan lapangan: " + e.getMessage());
        }
    }

    // Fungsi untuk mengedit data lapangan
    @FXML
    private void handleEdit() {
        Lapangan selectedLapangan = tableView.getSelectionModel().getSelectedItem();
        if (selectedLapangan == null) {
            showAlert("Peringatan", "Pilih lapangan yang ingin diedit.");
            return;
        }

        String nama = txtNama.getText();
        String hargaText = txtHarga.getText();
        String status = comboStatus.getValue();

        if (nama.isEmpty() || hargaText.isEmpty() || status == null) {
            showAlert("Peringatan", "Semua field harus diisi.");
            return;
        }

        double harga;
        try {
            harga = Double.parseDouble(hargaText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga harus berupa angka.");
            return;
        }

        String sql = "UPDATE lapangan SET nama_lapangan = ?, harga = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nama);
            pstmt.setDouble(2, harga);
            pstmt.setString(3, status);
            pstmt.setInt(4, selectedLapangan.getId());
            pstmt.executeUpdate();

            showAlert("Sukses", "Data lapangan berhasil diperbarui.");
            clearFields();
            loadData();

        } catch (SQLException e) {
            System.err.println("Error saat memperbarui data: " + e.getMessage());
        }
    }

    // Fungsi untuk menghapus data lapangan
    @FXML
    private void handleHapus() {
        Lapangan selectedLapangan = tableView.getSelectionModel().getSelectedItem();
        if (selectedLapangan == null) {
            showAlert("Peringatan", "Pilih lapangan yang ingin dihapus.");
            return;
        }

        String sql = "DELETE FROM lapangan WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, selectedLapangan.getId());
            pstmt.executeUpdate();

            showAlert("Sukses", "Lapangan berhasil dihapus.");
            loadData();

        } catch (SQLException e) {
            System.err.println("Error saat menghapus lapangan: " + e.getMessage());
        }
    }

    @FXML
    private void handleKembali() {
        // Pastikan SceneController memiliki metode changeScene yang benar
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

    // Fungsi untuk membersihkan field input
    private void clearFields() {
        txtNama.clear();
        txtHarga.clear();
        comboStatus.getSelectionModel().clearSelection();
    }

}
