/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package kel4;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANISETUS B. MANALU
 */
public class MenuAdminController{

    @FXML
    private Button btnDaftarBooking;
    
    @FXML
    private Button btnKelolaLapangan;

    @FXML
    private Button btnLogout;
    
    @FXML
    private Button btnKelolaUser;

    @FXML
    private void handleDaftarBookingClick(ActionEvent event) {
        Stage stage = (Stage) btnDaftarBooking.getScene().getWindow();
        SceneController.changeScene(stage, "DataBookingAdmin.fxml");
    }
    
    @FXML
    private void handleKelolaLapanganClick(ActionEvent event) {
        Stage stage = (Stage) btnKelolaLapangan.getScene().getWindow();
        SceneController.changeScene(stage, "Lapangan.fxml");
    }
    
        @FXML
    private void handleKelolaUserClick(ActionEvent event) {
        Stage stage = (Stage) btnKelolaUser.getScene().getWindow();
        SceneController.changeScene(stage, "AdminView.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        SceneController.changeScene(stage, "LoginDocument.fxml");
    }
}
