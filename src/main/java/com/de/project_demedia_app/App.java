package com.de.project_demedia_app;

import com.de.project_demedia_app.Models.UserProperties;
import com.de.project_demedia_app.Views.ViewFactory;
import com.de.project_demedia_app.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private UserProperties user_properties;

    @Override
    public void start(Stage stage) {
        user_properties = new UserProperties();

        if (user_properties.isRememberMe()) {
            Model.getInstance().loadUser(user_properties.getUsername(), user_properties.getPassword());
            Model.getInstance().getViewFactory().showMainMenuWindow();
        } else {
            Model.getInstance().getViewFactory().showLogInWindow();
            Model.getInstance().getViewFactory().setStage(stage);
        }

        Model.getInstance().getIsUserLoggedOutFlag().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                user_properties.setRememberMe(false);
                Stage new_stage = new Stage();
                Model.getInstance().setViewFactory(new ViewFactory());
                Model.getInstance().getViewFactory().showLogInWindow();
                Model.getInstance().getViewFactory().setStage(new_stage);
            }
        });
    }

    public static void main(String[] args) {
        launch(); 
    }
}