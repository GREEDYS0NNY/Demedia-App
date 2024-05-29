package com.de.project_demedia_app.Controllers.Sections;

import com.de.project_demedia_app.Views.LeftPanelOptions;
import static com.de.project_demedia_app.Models.Banner.*;
import com.de.project_demedia_app.Views.DatabaseTables;
import com.de.project_demedia_app.Models.Banner;
import com.de.project_demedia_app.Models.Model;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;

public class BrowseSectionController implements Initializable {
    public GridPane banners_layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeftPanelOptions selected_menu_item = Model.getInstance().getViewFactory().getUserSelectedMenuItem().getValue();
        ObservableList<Banner> anime_banners = Model.getInstance().loadAllBanners(DatabaseTables.AnimeList);
        ObservableList<Banner> films_banners = Model.getInstance().loadAllBanners(DatabaseTables.FilmsList);
        ObservableList<Banner> series_banners = Model.getInstance().loadAllBanners(DatabaseTables.SeriesList);

        if (selected_menu_item.equals(LeftPanelOptions.TOP_ANIME)) {
            addBanners(constructBanners(anime_banners), banners_layout);
        } else if (selected_menu_item.equals(LeftPanelOptions.TOP_FILMS)) {
            addBanners(constructBanners(films_banners), banners_layout);
        } else if (selected_menu_item.equals(LeftPanelOptions.TOP_SERIES)) {
            addBanners(constructBanners(series_banners), banners_layout);
        }
    }
}
