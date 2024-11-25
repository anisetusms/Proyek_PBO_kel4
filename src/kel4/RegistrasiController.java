package kel4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * @author ANISETUS
 */
public class RegistrasiController {

    @FXML
    private TextField txtNama, txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignUp;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String nama = txtNama.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // Validasi input kosong
        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Semua field harus diisi!");
            return;
        }

        String sql = "INSERT INTO users (nama, email, PASSWORD, ROLE) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameter untuk query SQL
            pstmt.setString(1, nama);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, "user"); // Default role adalah 'user'

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registrasi berhasil.");
                // Pindah ke halaman login
                Stage stage = (Stage) btnSignUp.getScene().getWindow();
                SceneController.changeScene(stage, "LoginDocument.fxml");
            } else {
                System.out.println("Registrasi gagal.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Terjadi kesalahan saat melakukan registrasi: " + e.getMessage());
        }
    }
}
