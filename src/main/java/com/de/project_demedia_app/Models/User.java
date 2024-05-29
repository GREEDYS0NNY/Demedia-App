package com.de.project_demedia_app.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty id, username, email, password;

    public User(String id, String username, String email, String password) {
        this.id = new SimpleStringProperty(this, "user_id",id);
        this.username = new SimpleStringProperty(this, "username", username);
        this.email = new SimpleStringProperty(this, "email", email);
        this.password = new SimpleStringProperty(this, "password", password);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
