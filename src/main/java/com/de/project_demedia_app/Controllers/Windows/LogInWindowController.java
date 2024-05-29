package com.de.project_demedia_app.Controllers.Windows;

import com.de.project_demedia_app.Models.UserProperties;
import com.de.project_demedia_app.Models.Model;
import javafx.scene.control.PasswordField;
import org.jetbrains.annotations.NotNull;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.ScaleTransition;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import java.text.SimpleDateFormat;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.Duration;
import javafx.stage.Stage;
import java.util.Random;
import java.util.Date;
import java.net.URL;

public class LogInWindowController implements Initializable {
    public JFXButton facebook_button, apple_button, google_button, phone_button, sign_up_button, login_button;
    public TextField username_field, signup_email_field, signup_username_field;
    public Label error_label, signup_error_label, password_error_label;
    public PasswordField password_field, signup_password_field;
    public Hyperlink login_hyperlink, create_account_hyperlink;
    public AnchorPane sign_up_window, login_window;
    public JFXCheckBox remember_me_choicebox;

    boolean valid_username_flag = false;
    boolean valid_email_flag = false;
    boolean valid_password_flag = false;
    private final UserProperties user_properties = new UserProperties();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login_button.setOnAction(event -> onLogIn());
        sign_up_button.setOnAction(event -> onSignUp());
        create_account_hyperlink.setOnAction(event -> {
            username_field.clear();
            password_field.clear();
            error_label.setText("");
            showWindow();
        });
        login_hyperlink.setOnAction(event -> {
            signup_username_field.clear();
            signup_password_field.clear();
            signup_email_field.clear();
            signup_error_label.setText("");
            password_error_label.setText("");
            showWindow();
        });

        onButtonHover(facebook_button, 1.025, 1.025, null);
        onButtonHover(apple_button, 1.025, 1.025, null);
        onButtonHover(google_button, 1.025, 1.025, null);
        onButtonHover(phone_button, 1.025, 1.025, null);
        onButtonHover(login_button, 1.02, 1.02, "#141414C5");
        onButtonHover(sign_up_button, 1.02, 1.02, "#141414C4");
    }

    private void onLogIn() {
        Stage stage = (Stage) login_button.getScene().getWindow();
        Model.getInstance().loadUser(username_field.getText(), password_field.getText());

        if (Model.getInstance().getUserLoginSuccessFlag()) {
            user_properties.setUsername(username_field.getText());
            user_properties.setPassword(password_field.getText());
            user_properties.setRememberMe(remember_me_choicebox.isSelected());

            Model.getInstance().getViewFactory().showMainMenuWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
        else {
            error_label.setText("Incorrect username or password!");
        }
    }

    private void onSignUp() {
        String username = signup_username_field.getText();
        String email = signup_email_field.getText();
        String password = signup_password_field.getText();

        dataValidation(username, email, password);

        if (valid_username_flag && valid_email_flag && valid_password_flag) {
            Model.getInstance().getDatabaseDriver().addUserData(generateUserId(), username, email, password);
            Stage stage = (Stage) sign_up_button.getScene().getWindow();
            Model.getInstance().loadUser(signup_username_field.getText(), signup_password_field.getText());
            Model.getInstance().getViewFactory().showMainMenuWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
    }

    private void onButtonHover(@NotNull JFXButton button, double x, double y, String hover_color) {
        String default_style = button.getStyle();

        ScaleTransition scale_up = new ScaleTransition(Duration.millis(280), button);
        scale_up.setToX(x);
        scale_up.setToY(y);
        ScaleTransition scale_down = new ScaleTransition(Duration.millis(280), button);
        scale_down.setToX(1);
        scale_down.setToY(1);

        button.setOnMouseEntered(event -> {
            scale_up.play();
            if (hover_color != null) {
                button.setStyle("-fx-background-color: " + hover_color + "; -fx-background-radius: 5px;" +
                        "-fx-border-color: #808080; -fx-border-radius: 5px;");
            }
        });
        button.setOnMouseExited(event -> {
            scale_down.play();
            button.setStyle(default_style);
        });
    }

    private @NotNull String generateUserId() {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = date_format.format(new Date());
        Random random = new Random();
        int random_part = random.nextInt(90000) + 10000;
        return "user" + timestamp + random_part;
    }

    private boolean usernameValidation(String username) {
        return Model.getInstance().checkUsername(username);
    }

    private boolean emailValidation(String email) {
        return Model.getInstance().checkEmail(email);
    }

    private boolean emailPatternValidation(String email) {
        String email_pattern = "\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    private boolean passwordValidation(@NotNull String password) {
        String password_pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=]).{8,16}$";
        Pattern pattern = Pattern.compile(password_pattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private void dataValidation(String username, String email, String password) {
        if (usernameValidation(username)) {
            if (username.length() >= 4 && username.length() <= 12) {
                if (emailPatternValidation(email)) {
                    if (emailValidation(email)) {
                        if (password.length() >= 8 && password.length() <= 16) {
                            if (passwordValidation(password)) {
                                valid_username_flag = true;
                                valid_email_flag = true;
                                valid_password_flag = true;
                                signup_error_label.setText("");
                            } else {
                                signup_error_label.setText("");
                                password_error_label.setText("Password must contain at least one uppercase letter, a lowercase letter,\na number and a special character from the list: !@#$%^&*()-_+=.");
                            }
                        } else {
                            password_error_label.setText("");
                            signup_error_label.setText("Password must contain between 8 and 16 characters!");
                        }
                    } else {
                        password_error_label.setText("");
                        signup_error_label.setText("An account with that email address is already registered!");
                    }
                } else {
                    password_error_label.setText("");
                    signup_error_label.setText("Invalid email address!");
                }
            } else {
                password_error_label.setText("");
                signup_error_label.setText("Username must contain between 4 and 12 characters!");
            }
        } else {
            password_error_label.setText("");
            signup_error_label.setText("This username is already taken!");
        }
    }

    private void showWindow() {
        login_window.setVisible(!login_window.isVisible());
        sign_up_window.setVisible(!sign_up_window.isVisible());
    }
}
