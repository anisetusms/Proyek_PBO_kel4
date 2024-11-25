package kel4;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kel4.DatabaseConnection;
import kel4.SceneController;

/**
 * FXML Controller class
 * 
 * @author ANISETUS
 */
public class LoginController {

    @FXML
    private ImageView imageLogo;

    @FXML
    private TextField txtNama;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegistrasi;

    @FXML
    private void handleLogin(ActionEvent event) {
        String nama = txtNama.getText().trim();
        String password = txtPassword.getText().trim();

        if (nama.isEmpty() || password.isEmpty()) {
            System.out.println("Nama atau password tidak boleh kosong.");
            return;
        }

        String sql = "SELECT * FROM users WHERE nama = ? AND PASSWORD = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nama);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("ROLE");
                Stage stage = (Stage) btnLogin.getScene().getWindow();

                if ("admin".equalsIgnoreCase(role)) {
                    SceneController.changeScene(stage, "MenuAdmin.fxml");
                } else {
                    SceneController.changeScene(stage, "MenuUser.fxml");
                }
            } else {
                System.out.println("Nama atau password salah.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Terjadi kesalahan saat login: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegistrasi(ActionEvent event) {
        Stage stage = (Stage) btnRegistrasi.getScene().getWindow();
        try {
            SceneController.changeScene(stage, "Registrasi.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Terjadi kesalahan saat membuka halaman registrasi: " + e.getMessage());
        }
    }
}
