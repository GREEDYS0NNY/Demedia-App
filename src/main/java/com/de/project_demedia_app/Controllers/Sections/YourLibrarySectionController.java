package com.de.project_demedia_app.Controllers.Sections;

import com.de.project_demedia_app.Views.LeftPanelOptions;
import static com.de.project_demedia_app.Models.Banner.*;
import com.de.project_demedia_app.Views.DatabaseTables;
import com.de.project_demedia_app.Models.Banner;
import com.de.project_demedia_app.Models.Model;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import javafx.animation.ScaleTransition;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.util.Duration;
import java.net.URL;

public class YourLibrarySectionController implements Initializable {
    public JFXButton anime_gridpane_button, films_gridpane_button, series_gridpane_button;
    public AnchorPane anime_anchorpane, films_anchorpane, series_anchorpane;
    public GridPane anime_gridpane, films_gridpane, series_gridpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeftPanelOptions selected_menu_item = Model.getInstance().getViewFactory().getUserSelectedMenuItem().getValue();
        
        if (selected_menu_item.equals(LeftPanelOptions.NOW_WATCHING)) {
            placeBannersByStatus("Now watching");
        } else if (selected_menu_item.equals(LeftPanelOptions.FAVORITES)) {
            placeBannersByStatus("Favorites");
        } else if (selected_menu_item.equals(LeftPanelOptions.WATCH_LIST)) {
            placeBannersByStatus("Watch list");
        }

        anime_gridpane_button.setOnAction(event -> onAnimeButton());
        films_gridpane_button.setOnAction(event -> onFilmsButton());
        series_gridpane_button.setOnAction(event -> onSeriesButton());

        onButtonHover(anime_gridpane_button);
        onButtonHover(films_gridpane_button);
        onButtonHover(series_gridpane_button);

        anime_gridpane_button.setDisable(true);
    }

    private void placeBannersByStatus(String status) {
        ObservableList<Banner> anime_banners = Model.getInstance().loadStatusMediaBanners(DatabaseTables.AnimeList, "A", status);
        ObservableList<Banner> films_banners = Model.getInstance().loadStatusMediaBanners(DatabaseTables.FilmsList, "F", status);
        ObservableList<Banner> series_banners = Model.getInstance().loadStatusMediaBanners(DatabaseTables.SeriesList, "S", status);

        addBanners(constructBanners(anime_banners), anime_gridpane);
        addBanners(constructBanners(films_banners), films_gridpane);
        addBanners(constructBanners(series_banners), series_gridpane);
    }

    private void onAnimeButton() {
        anime_anchorpane.setVisible(true);
        films_anchorpane.setVisible(false);
        series_anchorpane.setVisible(false);

        anime_gridpane_button.setDisable(true);
        films_gridpane_button.setDisable(false);
        series_gridpane_button.setDisable(false);
    }

    private void onFilmsButton() {
        anime_anchorpane.setVisible(false);
        films_anchorpane.setVisible(true);
        series_anchorpane.setVisible(false);

        anime_gridpane_button.setDisable(false);
        films_gridpane_button.setDisable(true);
        series_gridpane_button.setDisable(false);
    }

    private void onSeriesButton() {
        anime_anchorpane.setVisible(false);
        films_anchorpane.setVisible(false);
        series_anchorpane.setVisible(true);

        anime_gridpane_button.setDisable(false);
        films_gridpane_button.setDisable(false);
        series_gridpane_button.setDisable(true);
    }

    private void onButtonHover(@NotNull JFXButton button) {
        ScaleTransition scale_up = new ScaleTransition(Duration.millis(300), button);
        scale_up.setToX(1.03);
        scale_up.setToY(1.03);

        ScaleTransition scale_down = new ScaleTransition(Duration.millis(300), button);
        scale_down.setToX(1);
        scale_down.setToY(1);

        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 15; -fx-border-color: #3C3C3C;" +
                    "-fx-border-radius: 15; -fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 0);");
            scale_up.play();
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: #2D2D2D; -fx-background-radius: 15;" +
                "-fx-border-color: #3C3C3C; -fx-border-radius: 15; -fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 0);");
            scale_down.play();
        });
    }
}
