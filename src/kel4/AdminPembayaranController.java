package kel4;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import kel4.Pembayaran;
import kel4.PembayaranService;

public class AdminPembayaranController {

    @FXML
    private TableView<Pembayaran> tableViewPembayaran;
    @FXML
    private ComboBox<String> statusPembayaranCombo;

    @FXML
    private PembayaranService pembayaranService;

    @FXML
    private Button btnKembali;

    @FXML
    public void initialize() {
        // Inisialisasi service dan load data
        pembayaranService = new PembayaranService();
        loadPembayaranData();

        // Set pilihan untuk ComboBox
        statusPembayaranCombo.getItems().addAll("lunas", "belum lunas");
    }

    @FXML
    public void handleProsesPembayaran() {
        // Ambil data pembayaran yang dipilih
        Pembayaran selectedPembayaran = tableViewPembayaran.getSelectionModel().getSelectedItem();

        if (selectedPembayaran == null) {
            showAlert("Pilih pembayaran!", "Silakan pilih pembayaran yang akan diproses.", Alert.AlertType.WARNING);
            return;
        }

        // Ambil status pembayaran dari ComboBox
        String newStatus = statusPembayaranCombo.getValue();

        if (newStatus == null) {
            showAlert("Pilih status pembayaran!", "Silakan pilih status pembayaran dari ComboBox.", Alert.AlertType.WARNING);
            return;
        }

        // Update status pembayaran di database
        boolean isUpdated = pembayaranService.updateStatusPembayaran(selectedPembayaran.getId(), newStatus);

        if (isUpdated) {
            showAlert("Berhasil!", "Status pembayaran berhasil diperbarui.", Alert.AlertType.INFORMATION);
            loadPembayaranData(); // Refresh data pembayaran
        } else {
            showAlert("Gagal!", "Terjadi kesalahan saat memperbarui status pembayaran.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleKembali() {
        Stage stage = (Stage) btnKembali.getScene().getWindow();
        SceneController.changeScene(stage, "MenuAdmin.fxml");
    }

    private void loadPembayaranData() {
        // Load data pembayaran dari database
        tableViewPembayaran.setItems(pembayaranService.getAllPembayaran());
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
