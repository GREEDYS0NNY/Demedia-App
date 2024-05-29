package com.de.project_demedia_app.Controllers.MainWindowPanels;

import com.de.project_demedia_app.Views.LeftPanelOptions;
import com.de.project_demedia_app.Models.*;
import org.jetbrains.annotations.NotNull;
import javafx.animation.ScaleTransition;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Tooltip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.util.Duration;
import java.net.URL;

public class LeftPanelController implements Initializable {
    public JFXButton top_anime_button, top_films_button, top_series_button, now_watching_button;
    public JFXButton favorites_button, watch_list_button, categories_button, novelties_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addOnActions();
        addOnButtonsHover();
        onInaccessibleButton();

        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            top_anime_button.setDisable(false);
            top_films_button.setDisable(false);
            top_series_button.setDisable(false);
            now_watching_button.setDisable(false);
            favorites_button.setDisable(false);
            watch_list_button.setDisable(false);
        });
    }

    private void addOnActions() {
        top_anime_button.setOnAction(event -> onTopAnime());
        top_films_button.setOnAction(event -> onTopFilms());
        top_series_button.setOnAction(event -> onTopSeries());
        now_watching_button.setOnAction(event -> onNowWatching());
        favorites_button.setOnAction(event -> onFavorites());
        watch_list_button.setOnAction(event -> onWatchList());
    }

    private void addOnButtonsHover() {
        onButtonHover(top_anime_button);
        onButtonHover(top_films_button);
        onButtonHover(top_series_button);
        onButtonHover(now_watching_button);
        onButtonHover(favorites_button);
        onButtonHover(watch_list_button);
        onButtonHover(categories_button);
        onButtonHover(novelties_button);
    }

    private void onTopAnime() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.TOP_ANIME);
        top_anime_button.setDisable(true);
        top_films_button.setDisable(false);
        top_series_button.setDisable(false);
        now_watching_button.setDisable(false);
        favorites_button.setDisable(false);
        watch_list_button.setDisable(false);
    }

    private void onTopFilms() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.TOP_FILMS);
        top_anime_button.setDisable(false);
        top_films_button.setDisable(true);
        top_series_button.setDisable(false);
        now_watching_button.setDisable(false);
        favorites_button.setDisable(false);
        watch_list_button.setDisable(false);
    }

    private void onTopSeries() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.TOP_SERIES);
        top_anime_button.setDisable(false);
        top_films_button.setDisable(false);
        top_series_button.setDisable(true);
        now_watching_button.setDisable(false);
        favorites_button.setDisable(false);
        watch_list_button.setDisable(false);
    }

    private void onNowWatching() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.NOW_WATCHING);
        top_anime_button.setDisable(false);
        top_films_button.setDisable(false);
        top_series_button.setDisable(false);
        now_watching_button.setDisable(true);
        favorites_button.setDisable(false);
        watch_list_button.setDisable(false);
    }

    private void onFavorites() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.FAVORITES);
        top_anime_button.setDisable(false);
        top_films_button.setDisable(false);
        top_series_button.setDisable(false);
        now_watching_button.setDisable(false);
        favorites_button.setDisable(true);
        watch_list_button.setDisable(false);
    }

    private void onWatchList() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.WATCH_LIST);
        top_anime_button.setDisable(false);
        top_films_button.setDisable(false);
        top_series_button.setDisable(false);
        now_watching_button.setDisable(false);
        favorites_button.setDisable(false);
        watch_list_button.setDisable(true);
    }

    private void onButtonHover(@NotNull JFXButton button) {
        ScaleTransition scale_up = new ScaleTransition(Duration.millis(260), button);
        scale_up.setToX(1.02);
        scale_up.setToY(1.02);

        ScaleTransition scale_down = new ScaleTransition(Duration.millis(260), button);
        scale_down.setToX(1);
        scale_down.setToY(1);

        button.setOnMouseEntered(event -> scale_up.play());
        button.setOnMouseExited(event -> scale_down.play());
    }

    private void onInaccessibleButton() {
        Tooltip tooltip1 = new Tooltip("This section is not available for now.");
        Tooltip tooltip2 = new Tooltip("This section is not available for now.");

        categories_button.setOnAction(event -> {
            tooltip1.show(categories_button, 245, 571);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> tooltip1.hide()));
            timeline.setCycleCount(1);
            timeline.play();
        });
        novelties_button.setOnAction(event -> {
            tooltip2.show(novelties_button, 245, 623);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> tooltip2.hide()));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }
}
