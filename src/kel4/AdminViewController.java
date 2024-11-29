package kel4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import javafx.stage.Stage;

public class AdminViewController {

    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<User, Integer> columnId;
    @FXML
    private TableColumn<User, String> columnNama;
    @FXML
    private TableColumn<User, String> columnEmail;
    @FXML
    private TableColumn<User, String> columnRole;
    
    @FXML
    private Button btnKembali;
            
    private Connection conn;

    public AdminViewController() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingfutsal", "root", "anisetus");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Kesalahan Koneksi", "Tidak dapat terhubung ke database: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void initialize() {
        // Inisialisasi TableView
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        columnNama.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        columnEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        loadUsers();
    }

    private void loadUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Sesuaikan kolom sesuai dengan database
                User user = new User(rs.getInt("id"), rs.getString("nama"), rs.getString("email"),
                                     rs.getString("password"), rs.getString("role"));
                userList.add(user);
            }
            tableViewUsers.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal memuat data pengguna.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        // Logika untuk menambahkan user baru
    }

    @FXML
    private void handleEditUser(ActionEvent event) {
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Logika untuk mengedit user
        } else {
            showAlert("Peringatan", "Pilih akun pengguna untuk diedit", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
                ps.setInt(1, selectedUser.getId());
                int result = ps.executeUpdate();
                if (result > 0) {
                    showAlert("Berhasil", "Akun pengguna berhasil dihapus", Alert.AlertType.INFORMATION);
                    loadUsers(); // Reload data setelah penghapusan
                } else {
                    showAlert("Gagal", "Terjadi kesalahan saat menghapus akun", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Terjadi kesalahan saat menghapus akun.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Peringatan", "Pilih akun pengguna untuk dihapus", Alert.AlertType.WARNING);
        }
    }

        @FXML
    private void handleKembali() {
        Stage stage = (Stage) btnKembali.getScene().getWindow();
        SceneController.changeScene(stage, "MenuAdmin.fxml");
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
