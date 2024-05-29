package com.de.project_demedia_app.Views;

import com.de.project_demedia_app.Controllers.Windows.MainWindowController;
import org.jetbrains.annotations.NotNull;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class ViewFactory {
    private AnchorPane centralMenuView, topAnimeView, topFilmsView, topSeriesView, mediaPage;
    private final BooleanProperty isMediaPlaying, isMediaPlayerInFullScreen;
    private final ObjectProperty<LeftPanelOptions> userSelectedMenuItem;
    private final StringProperty userSelectedMediaId;
    private Stage stage;

    public ViewFactory() {
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
        this.userSelectedMediaId = new SimpleStringProperty();
        this.isMediaPlaying = new SimpleBooleanProperty();
        this.isMediaPlayerInFullScreen = new SimpleBooleanProperty();
        this.stage = new Stage();
    }

    public ObjectProperty<LeftPanelOptions> getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    public StringProperty getUserSelectedMediaId() {
        return userSelectedMediaId;
    }

    public BooleanProperty getIsMediaPlaying() {
        return isMediaPlaying;
    }

    public BooleanProperty getIsMediaPlayerInFullScreen() {
        return isMediaPlayerInFullScreen;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public AnchorPane getCentralMenuView() {
        if (centralMenuView == null) {
            try {
                centralMenuView = new FXMLLoader(getClass().getResource("/Fxml/CenterPanel.fxml")).load();
            } catch (IOException ignored) {
            }
        }
        return centralMenuView;
    }

    public AnchorPane getTopAnimeView() {
        if (topAnimeView == null) {
            try {
                topAnimeView = new FXMLLoader(getClass().getResource("/Fxml/BrowseSection.fxml")).load();
            } catch (IOException ignored) {
            }
        }
        return topAnimeView;
    }

    public AnchorPane getTopFilmsView() {
        if (topFilmsView == null) {
            try {
                topFilmsView = new FXMLLoader(getClass().getResource("/Fxml/BrowseSection.fxml")).load();
            } catch (IOException ignored) {
            }
        }
        return topFilmsView;
    }

    public AnchorPane getTopSeriesView() {
        if (topSeriesView == null) {
            try {
                topSeriesView = new FXMLLoader(getClass().getResource("/Fxml/BrowseSection.fxml")).load();
            } catch (IOException ignored) {
            }
        }
        return topSeriesView;
    }

    public AnchorPane getMediaPage() {
        try {
            mediaPage = new FXMLLoader(getClass().getResource("/Fxml/MediaPage.fxml")).load();
        } catch (IOException ignored) {
        }
        return mediaPage;
    }

    public AnchorPane getYourLibrarySection() {
        try {
            mediaPage = new FXMLLoader(getClass().getResource("/Fxml/YourLibrarySection.fxml")).load();
        } catch (IOException ignored) {
        }
        return mediaPage;
    }

    public void showLogInWindow() {
        FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("/Fxml/LogInWindow.fxml"));
        createStage(fxml_loader, false);
    }

    public void showMainMenuWindow() {
        FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("/Fxml/MainWindow.fxml"));
        MainWindowController controller = new MainWindowController();
        fxml_loader.setController(controller);
        createStage(fxml_loader, true);
    }

    private void createStage(@NotNull FXMLLoader fxml_loader, boolean resizable) {
        Scene scene = null;
        Stage stage = new Stage();

        try {
            scene = new Scene(fxml_loader.load());
        } catch (IOException ignored) {
        }

        stage.setScene(scene);

        if (resizable) {
            stage.setResizable(true);
            stage.setMaximized(true);
        }
        else { stage.setResizable(false); }

        stage.setTitle("Demedia");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/AppIcon.png"))));
        stage.show();
    }

    public void closeStage(@NotNull Stage stage) {
        stage.close();
    }
}
