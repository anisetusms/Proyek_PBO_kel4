package kel4;

public class Lapangan {
    private int id;
    private String namaLapangan;
    private double harga;
    private String status;

    public Lapangan(int id, String namaLapangan, double harga, String status) {
        this.id = id;
        this.namaLapangan = namaLapangan;
        this.harga = harga;
        this.status = status;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
