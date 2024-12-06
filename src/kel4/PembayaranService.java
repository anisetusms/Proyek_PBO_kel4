package kel4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kel4.Pembayaran;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PembayaranService {
    private final DatabaseConnection databaseConnection;

    public PembayaranService() {
        databaseConnection = new DatabaseConnection();
    }

    public ObservableList<Pembayaran> getAllPembayaran() {
        ObservableList<Pembayaran> pembayaranList = FXCollections.observableArrayList();
        String query = "SELECT * FROM pembayaran";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pembayaranList.add(new Pembayaran(
                        resultSet.getInt("id"),
                        resultSet.getInt("booking_id"),
                        resultSet.getBigDecimal("jumlah_bayar"),
                        resultSet.getTimestamp("tanggal_pembayaran").toLocalDateTime(),
                        resultSet.getString("status_pembayaran")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pembayaranList;
    }

    public boolean updateStatusPembayaran(int id, String newStatus) {
        String query = "UPDATE pembayaran SET status_pembayaran = ? WHERE id = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newStatus);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
