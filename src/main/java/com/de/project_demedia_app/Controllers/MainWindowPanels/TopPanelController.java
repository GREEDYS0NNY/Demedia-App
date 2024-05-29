package com.de.project_demedia_app.Controllers.MainWindowPanels;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.de.project_demedia_app.Views.LeftPanelOptions;
import com.de.project_demedia_app.Models.Model;
import org.jetbrains.annotations.NotNull;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.KeyCode;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

public class TopPanelController implements Initializable {
    public JFXButton main_menu_button;
    public MenuButton user_menu_button;
    public Label username_label;
    public TextField search_field;
    public ListView<String> search_results_listview;
    private final Popup popup = new Popup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, String> all_media_titles = Model.getInstance().getAllMediaTitles();
        username_label.setText(Model.getInstance().getUser().getUsername());
        popup.getContent().add(search_results_listview);

        search_field.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ||
                    event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                return;
            } else {
                String search_text = search_field.getText();
                Map<String, String> search_results = searchResults(search_text, all_media_titles);

                if (search_field.getText().isEmpty()) {
                    popup.hide();
                } else {
                    search_results_listview.getItems().setAll(search_results.keySet());
                    popup.show(search_field.getScene().getWindow(), 20, 25);
                }
            }

            popup.setAutoHide(true);
        });
        search_results_listview.setOnMouseClicked(event -> {
            if (search_results_listview.getSelectionModel().getSelectedItem() != null) {
                String selected_media = search_results_listview.getSelectionModel().getSelectedItem();
                Model.getInstance().getViewFactory().getUserSelectedMediaId().set(all_media_titles.get(selected_media));
                Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.MEDIA_PAGE);

                search_field.setText(selected_media);
                search_field.positionCaret(search_field.getText().length());
                popup.hide();
            }
        });
        user_menu_button.getItems().getFirst().setOnAction(event -> {
            if (Model.getInstance().getViewFactory().getIsMediaPlaying().getValue()) {
                Model.getInstance().getViewFactory().getIsMediaPlaying().set(false);
            }
            Model.getInstance().getViewFactory().closeStage((Stage) user_menu_button.getScene().getWindow());
            Model.getInstance().getIsUserLoggedOutFlag().set(true);
        });

        onMainMenuButton();
        onUserMenuButton();
        addLogOutOption();
    }

    private @NotNull Map<String, String> searchResults(String received_string, @NotNull Map<String, String> all_media_titles) {
        Map<String, String> results = new HashMap<>();
        for (Map.Entry<String, String> entry : all_media_titles.entrySet()) {
            if (entry.getKey().toLowerCase().contains(received_string.toLowerCase())) {
                results.put(entry.getKey(), entry.getValue());
            }
        }
        return results;
    }

    private void onUserMenuButton() {
        user_menu_button.setOnMouseEntered(event -> user_menu_button.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 15;" +
                "-fx-border-color: #262627; -fx-border-radius: 15"));
        user_menu_button.setOnMouseExited(event -> user_menu_button.setStyle("-fx-background-color: #2D2D2D; -fx-background-radius: 15;" +
                "-fx-border-color: #262627; -fx-border-radius: 15"));
    }

    private void addLogOutOption() {
        FontAwesomeIconView sign_out_icon = new FontAwesomeIconView();
        sign_out_icon.setGlyphName("SIGN_OUT");
        sign_out_icon.setGlyphSize(20);
        sign_out_icon.setFill(Paint.valueOf("#B0B0B0"));

        user_menu_button.getItems().getFirst().setText("Log out");
        user_menu_button.getItems().getFirst().setStyle("-fx-text-fill: #B0B0B0;");
        user_menu_button.getItems().getFirst().setGraphic(sign_out_icon);
    }

    private void onMainMenuButton() {
        main_menu_button.setOnAction(event -> Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.DEMEDIA));
    }
}
