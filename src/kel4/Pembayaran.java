package kel4;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pembayaran {
    private int id;
    private int bookingId;
    private BigDecimal jumlahBayar;
    private LocalDateTime tanggalPembayaran;
    private String statusPembayaran;

    public Pembayaran(int id, int bookingId, BigDecimal jumlahBayar, LocalDateTime tanggalPembayaran, String statusPembayaran) {
        this.id = id;
        this.bookingId = bookingId;
        this.jumlahBayar = jumlahBayar;
        this.tanggalPembayaran = tanggalPembayaran;
        this.statusPembayaran = statusPembayaran;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getBookingId() { return bookingId; }
    public BigDecimal getJumlahBayar() { return jumlahBayar; }
    public LocalDateTime getTanggalPembayaran() { return tanggalPembayaran; }
    public String getStatusPembayaran() { return statusPembayaran; }
}
