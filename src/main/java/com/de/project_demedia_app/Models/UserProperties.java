package com.de.project_demedia_app.Models;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;

public class UserProperties {
    private static final String CONFIG_FILE = "user.properties";
    private final Properties properties;

    public UserProperties() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException ignored) {}
    }

    public boolean isRememberMe() {
        return Boolean.parseBoolean(properties.getProperty("rememberMe", "false"));
    }

    public void setRememberMe(boolean rememberMe) {
        properties.setProperty("rememberMe", Boolean.toString(rememberMe));
        saveProperties();
    }

    public String getUsername() {
        return properties.getProperty("username", "");
    }

    public void setUsername(String username) {
        properties.setProperty("username", username);
        saveProperties();
    }

    public String getPassword() {
        return properties.getProperty("password", "");
    }

    public void setPassword(String password) {
        properties.setProperty("password", password);
        saveProperties();
    }

    private void saveProperties() {
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, null);
        } catch (IOException ignored) {}
    }
}
