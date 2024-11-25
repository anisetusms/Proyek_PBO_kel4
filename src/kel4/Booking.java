package kel4;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ANISETUS B. MANALU
 */
public class Booking {
    private int id;
    private String nama;
    private String namaLapangan;
    private LocalDate tanggalBooking;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private String status;

    // Konstruktor
    public Booking(int id, String nama, String namaLapangan, LocalDate tanggalBooking, 
                   LocalTime jamMulai, LocalTime jamSelesai, String status) {
        this.id = id;
        this.nama = nama;
        this.namaLapangan = namaLapangan;
        this.tanggalBooking = tanggalBooking;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.status = status;
    }

    // Getters dan Setters
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public LocalDate getTanggalBooking() {
        return tanggalBooking;
    }

    public void setTanggalBooking(LocalDate tanggalBooking) {
        this.tanggalBooking = tanggalBooking;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    // Setter untuk ID (opsional jika Anda perlu mengubah ID)
    public void setId(int id) {
        this.id = id;
    }
}
