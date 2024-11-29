package kel4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DaftarLapanganController {

    @FXML
    private TableView<Lapangan> tableView;

    @FXML
    private TableColumn<Lapangan, String> colNamaLapangan;

    @FXML
    private TableColumn<Lapangan, Double> colHarga;

    @FXML
    private TableColumn<Lapangan, String> colStatus;
    
    @FXML
    private Button btnKembali;
    
    @FXML
    private Button btnBookingLapangan;

    private ObservableList<Lapangan> lapanganList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNamaLapangan.setCellValueFactory(new PropertyValueFactory<>("namaLapangan"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadData(); // Memuat data lapangan dari database
    }
    
        @FXML
    private void handleKembali() {
        // Pastikan SceneController memiliki metode changeScene yang benar
        Stage stage = (Stage) btnKembali.getScene().getWindow();
        SceneController.changeScene(stage, "MenuUser.fxml"); // Pastikan "MenuUser.fxml" benar
    }

    // Fungsi untuk memuat data lapangan dari database
    private void loadData() {
        lapanganList.clear();
        String sql = "SELECT * FROM lapangan WHERE status = 'tersedia'";  // Ambil lapangan yang tersedia saja

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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
            System.err.println("Error saat memuat data lapangan: " + e.getMessage());
        }
    }
    
        @FXML
    private void handleBookingLapanganClick(ActionEvent event) {
        Stage stage = (Stage) btnBookingLapangan.getScene().getWindow();
        SceneController.changeScene(stage, "BookingLapangan.fxml");
    }
}
