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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DataBookingController implements Initializable {

    @FXML
    private TableView<Booking> tableView;

    @FXML
    private TableColumn<Booking, String> colNama;

    @FXML
    private TableColumn<Booking, String> colNamaLapangan;

    @FXML
    private TableColumn<Booking, String> colTanggalBooking;

    @FXML
    private TableColumn<Booking, String> colJamMulai;

    @FXML
    private TableColumn<Booking, String> colJamSelesai;

    @FXML
    private TableColumn<Booking, String> colNomor;

    @FXML
    private TableColumn<Booking, String> colStatus;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView btnSearch;

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
        colNomor.setCellValueFactory(new PropertyValueFactory<>("nomor"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Nomor otomatis
        colNomor.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && getTableRow() != null) {
                    setText(String.valueOf(getTableRow().getIndex() + 1));
                } else {
                    setText("");
                }
            }
        });

        loadData(); // Memuat data awal
    }

    // Fungsi untuk memuat data dari database
    private void loadData() {
        bookingList.clear();
        String sql = "SELECT b.id, u.nama AS nama_user, l.nama_lapangan, b.tanggal_booking, b.jam_mulai, b.jam_selesai, b.status "
                   + "FROM booking b "
                   + "JOIN users u ON b.user_id = u.id "
                   + "JOIN lapangan l ON b.lapangan_id = l.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                bookingList.add(new Booking(
                        rs.getInt("id"), rs.getString("nama_user"),
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

    // Fungsi pencarian data
    @FXML
    private void handleSearch() {
        String searchKeyword = txtSearch.getText();
        ObservableList<Booking> filteredData = FXCollections.observableArrayList();
        String sql = "SELECT b.id, u.nama AS nama_user, l.nama_lapangan, b.tanggal_booking, b.jam_mulai, b.jam_selesai, b.status "
                   + "FROM booking b "
                   + "JOIN users u ON b.user_id = u.id "
                   + "JOIN lapangan l ON b.lapangan_id = l.id "
                   + "WHERE u.nama LIKE ? OR l.nama_lapangan LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchKeyword + "%");
            pstmt.setString(2, "%" + searchKeyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                filteredData.add(new Booking(
                        rs.getInt("id"), rs.getString("nama_user"),
                        rs.getString("nama_lapangan"),
                        rs.getDate("tanggal_booking").toLocalDate(),
                        rs.getTime("jam_mulai").toLocalTime(),
                        rs.getTime("jam_selesai").toLocalTime(),
                        rs.getString("status")
                ));
            }
            tableView.setItems(filteredData);

        } catch (SQLException e) {
            System.err.println("Error saat mencari data: " + e.getMessage());
        }
    }

    // Fungsi untuk kembali ke menu sebelumnya
@FXML
private void handleKembali() {
    // Pastikan SceneController memiliki metode changeScene yang benar
    Stage stage = (Stage) btnKembali.getScene().getWindow();
    SceneController.changeScene(stage, "MenuUser.fxml"); // Pastikan "MenuUser.fxml" benar
}
}
