package kel4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import javafx.stage.Stage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminViewController {

    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<User, String> colNama;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> comboRole;

    @FXML
    private Button btnTambah;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnHapus;
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
        colNama.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        loadUsers();

        // Inisialisasi ComboBox untuk role
        comboRole.setItems(FXCollections.observableArrayList("Admin", "User"));

        // Set column resize policy
        tableViewUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        String nama = txtNama.getText().trim();
        String email = txtEmail.getText().trim();
        String role = comboRole.getValue();

        // Validasi input
        if (nama.isEmpty() || email.isEmpty() || role == null) {
            showAlert("Peringatan", "Semua field harus diisi!", Alert.AlertType.WARNING);
            return;
        }

        // Hash password before saving (using a stronger algorithm like SHA-256)
        String hashedPassword = hashPassword("password"); // For example, you should get password from input.

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (nama, email, password, role) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, nama);
            ps.setString(2, email);
            ps.setString(3, hashedPassword); // Hash password sebelum simpan
            ps.setString(4, role);
            ps.executeUpdate();
            showAlert("Berhasil", "Akun pengguna berhasil ditambahkan", Alert.AlertType.INFORMATION);
            loadUsers(); // Reload data
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat menambahkan akun.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEditUser(ActionEvent event) {
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String nama = txtNama.getText().trim();
            String email = txtEmail.getText().trim();
            String role = comboRole.getValue();

            // Validasi input
            if (nama.isEmpty() || email.isEmpty() || role == null) {
                showAlert("Peringatan", "Semua field harus diisi!", Alert.AlertType.WARNING);
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE users SET nama = ?, email = ?, role = ? WHERE id = ?")) {
                ps.setString(1, nama);
                ps.setString(2, email);
                ps.setString(3, role);
                ps.setInt(4, selectedUser.getId());
                ps.executeUpdate();
                showAlert("Berhasil", "Akun pengguna berhasil diperbarui", Alert.AlertType.INFORMATION);
                loadUsers(); // Reload data
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Terjadi kesalahan saat memperbarui akun.", Alert.AlertType.ERROR);
            }
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

    // Hashing password method (using SHA-256 for better security)
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
