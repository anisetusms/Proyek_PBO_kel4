/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package kel4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANISETUS B. MANALU
 */
public class MenuUserController {

    @FXML
    private ImageView imageLogo;

    @FXML
    private Button btnBookingLapangan;

    @FXML
    private Button btnDataBooking;

    @FXML
    private Button btnLogout;
    
    @FXML
    private Button btnDaftarLapangan;


    /**
     * Handle event for Booking Lapangan button
     */
    @FXML
    private void handleBookingLapanganClick(ActionEvent event) {
        Stage stage = (Stage) btnBookingLapangan.getScene().getWindow();
        SceneController.changeScene(stage, "BookingLapangan.fxml");
    }

    /**
     * Handle event for Data Booking button
     */
    @FXML
    private void handleDataBookingClick(ActionEvent event) {
        Stage stage = (Stage) btnDataBooking.getScene().getWindow();
        SceneController.changeScene(stage, "DataBooking.fxml");
    }
    
        @FXML
    private void handleDaftarLapanganClick(ActionEvent event) {
        Stage stage = (Stage) btnDaftarLapangan.getScene().getWindow();
        SceneController.changeScene(stage, "DaftarLapangan.fxml");
    }

    /**
     * Handle event for Logout button
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        SceneController.changeScene(stage, "LoginDocument.fxml");
    }
}
