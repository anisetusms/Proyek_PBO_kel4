package kel4;

import javafx.beans.property.*;

public class User {
    private final IntegerProperty id;
    private final StringProperty nama;
    private final StringProperty email;
    private final StringProperty password;
    private final StringProperty role;

    public User(int id, String nama, String email, String password, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNama() {
        return nama.get();
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }
}
