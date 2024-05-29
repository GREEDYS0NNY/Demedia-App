package com.de.project_demedia_app.Controllers.Windows;

import com.de.project_demedia_app.Views.LeftPanelOptions;
import com.de.project_demedia_app.Models.Model;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.scene.layout.*;
import java.net.URL;

public class MainWindowController implements Initializable {
    public AnchorPane parent_anchorpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StackPane stackpane = (StackPane) parent_anchorpane.getChildren().getFirst();
        Pane pane = (Pane) stackpane.getChildren().get(1);

        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TOP_ANIME:
                    pane.getChildren().clear();
                    pane.getChildren().add(Model.getInstance().getViewFactory().getTopAnimeView());
                    Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(false);
                    break;
                case TOP_FILMS:
                    pane.getChildren().clear();
                    pane.getChildren().add(Model.getInstance().getViewFactory().getTopFilmsView());
                    Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(false);
                    break;
                case TOP_SERIES:
                    pane.getChildren().clear();
                    pane.getChildren().add(Model.getInstance().getViewFactory().getTopSeriesView());
                    Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(false);
                    break;
                case MEDIA_PAGE:
                    if (Model.getInstance().getViewFactory().getIsMediaPlaying().getValue()) {
                        Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(false);
                    }
                    pane.getChildren().clear();
                    pane.getChildren().add(Model.getInstance().getViewFactory().getMediaPage());
                    Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.DEFAULT);
                    break;
                case NOW_WATCHING, FAVORITES, WATCH_LIST:
                    pane.getChildren().clear();
                    pane.getChildren().add(Model.getInstance().getViewFactory().getYourLibrarySection());
                    Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(false);
                    break;
                case DEMEDIA:
                    pane.getChildren().clear();
                    pane.getChildren().add(Model.getInstance().getViewFactory().getCentralMenuView());
                    Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(false);
                    break;
                default:
                    break;
            }
        });

        Model.getInstance().getViewFactory().getIsMediaPlayerInFullScreen().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                parent_anchorpane.getChildren().get(1).setVisible(false);
                parent_anchorpane.getChildren().get(2).setVisible(false);
                stackpane.getChildren().getFirst().setVisible(false);
                pane.setPrefWidth(1536);
                pane.getChildren().getFirst().setLayoutY(-66);
            } else {
                parent_anchorpane.getChildren().get(1).setVisible(true);
                parent_anchorpane.getChildren().get(2).setVisible(true);
                stackpane.getChildren().getFirst().setVisible(true);
                pane.setPrefWidth(1295);
                pane.getChildren().getFirst().setLayoutY(0);
            }
        });
    }
}
