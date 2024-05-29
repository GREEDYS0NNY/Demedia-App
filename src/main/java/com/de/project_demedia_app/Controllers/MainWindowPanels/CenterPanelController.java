package com.de.project_demedia_app.Controllers.MainWindowPanels;

import static com.de.project_demedia_app.Models.Banner.*;
import com.de.project_demedia_app.Views.DatabaseTables;
import com.de.project_demedia_app.Models.Banner;
import com.de.project_demedia_app.Models.Model;
import org.jetbrains.annotations.NotNull;
import javafx.collections.ObservableList;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import java.util.ResourceBundle;
import java.util.List;
import java.net.URL;

public class CenterPanelController implements Initializable {
    public HBox anime_layout, films_layout, series_layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Banner> anime_banners = Model.getInstance().loadLimitedBanners(DatabaseTables.AnimeList);
        ObservableList<Banner> films_banners = Model.getInstance().loadLimitedBanners(DatabaseTables.FilmsList);
        ObservableList<Banner> series_banners = Model.getInstance().loadLimitedBanners(DatabaseTables.SeriesList);

        insertBanners(anime_banners, anime_layout);
        insertBanners(films_banners, films_layout);
        insertBanners(series_banners, series_layout);
    }

    private void insertBanners(@NotNull ObservableList<Banner> banners, HBox banners_layout) {
        List<JFXButton> banner_buttons = constructBanners(banners);
        for (JFXButton banner_button : banner_buttons) {
            banners_layout.getChildren().add(banner_button);
        }
    }
}
