package com.de.project_demedia_app.Controllers.MediaPages;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.de.project_demedia_app.Models.MediaPage;
import com.de.project_demedia_app.Models.Model;
import org.jetbrains.annotations.NotNull;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Separator;
import javafx.scene.media.MediaPlayer;
import javafx.scene.effect.DropShadow;
import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.shape.Arc;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.animation.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

public class MediaPageController implements Initializable {
    public Label media_status_label, media_type_label, media_episodes_label, media_genres_label, media_production_label, media_rating_label;
    public Label media_description_label, media_runtime_label, status_label, current_time_label, total_time_label, media_title_label, production_label;
    public FontAwesomeIconView PPR_icon, volume_icon, fullscreen_icon, now_watching_icon, favorites_icon, watch_list_icon;
    public Button PPR_button, speed_button, fullscreen_button, volume_button;
    public HBox controls_hbox, volume_hbox, episodes_hbox, rating_hbox;
    public JFXButton now_watching_button, favorites_button, watch_list_button;
    public VBox parent_vbox, media_info_vbox;
    public JFXSlider volume_slider, time_slider;
    public ImageView media_image, background_media_image;
    public AnchorPane parent_anchorpane, media_info_anchorpane;
    public StackPane parent_stackpane, child_stackpane;
    public BorderPane parent_borderpane;
    public ScrollPane media_page_scrollpane;
    public MediaView video_mediaview;
    public MediaPlayer video_mediaplayer;
    public Media video_media;
    public Separator episodes_separator;
    public JFXComboBox<String> episodes_choicebox;
    public Pane controls_pane;
    public Arc loading_arc;

    private final String user_id = Model.getInstance().getUser().getId();
    private final String media_id = Model.getInstance().getViewFactory().getUserSelectedMediaId().getValue();
    private String status;
    private boolean at_end_of_video = false;
    private boolean is_playing = true;
    private boolean is_muted = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> episodes_URLS;
        MediaPage media_page;

        if (media_id != null) {
            if (media_id.contains("A")) {
                episodes_URLS = Model.getInstance().getEpisodes(media_id);
                setChoiceBoxFunctionality(episodes_URLS);
                media_page = Model.getInstance().getMediaPage(media_id);
                activateMediaPlayer(episodes_URLS.getFirst());
            } else if (media_id.contains("F")) {
                media_page = Model.getInstance().getMediaPage(media_id);
                episodes_choicebox.setVisible(false);
                activateMediaPlayer(media_page.getMovieURL());
            } else {
                episodes_URLS = Model.getInstance().getEpisodes(media_id);
                setChoiceBoxFunctionality(episodes_URLS);
                media_page = Model.getInstance().getMediaPage(media_id);
                activateMediaPlayer(episodes_URLS.getFirst());
            }

            startLoadingAnimation();

            fillMediaPageInfo(media_page, media_id);
            configureButtons();
            addHoverEffect(now_watching_button);
            addHoverEffect(favorites_button);
            addHoverEffect(watch_list_button);

            now_watching_button.setOnAction(event -> onNowWatchingButton());
            favorites_button.setOnAction(event -> onFavoritesButton());
            watch_list_button.setOnAction(event -> onWatchListButton());

            Model.getInstance().getViewFactory().getIsMediaPlaying().addListener((observableValue, oldVal, newVal) -> {
                if (!newVal) {
                    video_mediaplayer.pause();
                }
            });
        }
    }

    private void startLoadingAnimation() {
        RotateTransition rotate_transition = new RotateTransition(Duration.seconds(1), loading_arc);
        rotate_transition.setByAngle(360);
        rotate_transition.setCycleCount(RotateTransition.INDEFINITE);
        rotate_transition.setInterpolator(Interpolator.LINEAR);
        rotate_transition.play();
    }

    private void fillMediaPageInfo(@NotNull MediaPage mediaPage, @NotNull String mediaId) {
        Rectangle clip = new Rectangle(200, 285);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        clip.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0);" +
                "-fx-background-radius: 50%;");

        media_image.setImage(mediaPage.getCover());
        media_image.setClip(clip);

        background_media_image.setImage(mediaPage.getBackgroundCover());

        if (mediaId.contains("A")) {
            if (mediaPage.getTitle().contains("Memories")) {
                media_runtime_label.setText("22 – 44 min. per ep.");
            }
            media_status_label.setText(mediaPage.getStatus());
            media_episodes_label.setText(mediaPage.getEpisodes());
            media_production_label.setText(mediaPage.getStudios());
        } else if (mediaId.contains("F")) {
            status_label.setText("Released:");
            media_status_label.setText(mediaPage.getRelease_date());
            media_info_vbox.getChildren().remove(episodes_hbox);
            media_info_vbox.getChildren().remove(episodes_separator);
            media_runtime_label.setText(mediaPage.getRuntime());
            production_label.setText("Directors:");
            media_production_label.setText(mediaPage.getDirectors());
            rating_hbox.setPadding(new Insets(-16, 0, 0, 0));
        } else {
            media_status_label.setText(mediaPage.getStatus());
            media_episodes_label.setText(mediaPage.getEpisodes());
            media_runtime_label.setText(mediaPage.getRuntime());
            production_label.setText("Creators:");
            media_production_label.setText(mediaPage.getCreators());
        }

        media_title_label.setText(mediaPage.getTitle());
        media_type_label.setText(mediaPage.getType());
        media_genres_label.setText(mediaPage.getGenres());
        media_rating_label.setText(String.valueOf(mediaPage.getRating()));
        media_description_label.setText(mediaPage.getDescription());
    }

    /* Media Player */
    private void activateMediaPlayer(String sourceUrl) {
        if (video_media != null) {
            video_mediaplayer.dispose();
            setUpMediaPlayerResource(sourceUrl);
        }
        else {
            setUpMediaPlayerResource(sourceUrl);
        }

        setButtonsFunctionality();
        setSlidersFunctionality();
        setMediaPlayerFunctionality();
        onChildStackPane();
        bindCurrentTimeLabel();

        volume_hbox.getChildren().remove(volume_slider);
        volume_hbox.setOnMouseExited(mouseEvent -> volume_hbox.getChildren().remove(volume_slider));
        video_mediaplayer.volumeProperty().bindBidirectional(volume_slider.valueProperty());
        video_mediaplayer.play();
        video_mediaplayer.currentTimeProperty().addListener(event -> {
            double current_time = video_mediaplayer.getCurrentTime().toSeconds();
            if (current_time > 4 && current_time < 4.10) {
                controls_pane.setVisible(false);
            }
        });

        is_playing = true;
        Model.getInstance().getViewFactory().getIsMediaPlaying().setValue(true);
    }

    private void setUpMediaPlayerResource(String sourceUrl) {
        video_media = new Media(sourceUrl);
        video_mediaplayer = new MediaPlayer(video_media);
        video_mediaview.setMediaPlayer(video_mediaplayer);
    }

    private void setChoiceBoxFunctionality(@NotNull List<String> episodesURLS) {
        List<String> episodes = new ArrayList<>();

        for (int i = 1; i < episodesURLS.toArray().length + 1; i++) {
            episodes.add("Episode " + i);
        }
        episodes_choicebox.setValue("Episode 1");
        episodes_choicebox.setItems(FXCollections.observableList(episodes));

        episodes_choicebox.setOnAction(event -> {
            String episodeNumber = episodes_choicebox.getValue().replace("Episode ", "");
            activateMediaPlayer(episodesURLS.get(Integer.parseInt(episodeNumber) - 1));
        });
    }

    private void setButtonsFunctionality() {
        PPR_button.setOnAction(actionEvent -> {
            bindCurrentTimeLabel();
            Button play_button = (Button) actionEvent.getSource();
            bindCurrentTimeLabel();
            if (at_end_of_video) {
                time_slider.setValue(0);
                at_end_of_video = false;
                is_playing = false;
            }
            if (is_playing) {
                PPR_icon.setGlyphName("PLAY");
                play_button.setGraphic(PPR_icon);
                video_mediaplayer.pause();
                is_playing = false;
            } else {
                PPR_icon.setGlyphName("PAUSE");
                play_button.setGraphic(PPR_icon);
                video_mediaplayer.play();
                is_playing = true;
            }
        });

        speed_button.setOnMouseClicked(mouseEvent -> {
            if (speed_button.getText().equals("1X")) {
                speed_button.setText("2X");
                speed_button.setTextFill(Paint.valueOf("#9c9a9a"));
                video_mediaplayer.setRate(2.0);
            } else {
                speed_button.setText("1X");
                speed_button.setTextFill(Paint.valueOf("#9c9a9a"));
                video_mediaplayer.setRate(1.0);
            }
        });

        volume_button.setOnMouseClicked(mouseEvent -> {
            if (is_muted) {
                volume_icon.setGlyphName("VOLUME_UP");
                volume_icon.setFill(Paint.valueOf("#9c9a9a"));
                volume_button.setGraphic(volume_icon);
                volume_slider.setValue(1);
                is_muted = false;
            } else {
                System.out.println("clicked");
                volume_icon.setGlyphName("VOLUME_OFF");
                volume_icon.setFill(Paint.valueOf("#9c9a9a"));
                volume_button.setGraphic(volume_icon);
                volume_slider.setValue(0);
                is_muted = true;
            }
        });

        volume_button.setOnMouseEntered(mouseEvent -> {
            if (volume_hbox.lookup("#volume_slider") == null) {
                volume_hbox.getChildren().add(volume_slider);
                volume_slider.setValue(video_mediaplayer.getVolume());
            }
        });

        fullscreen_button.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) fullscreen_button.getScene().getWindow();

            if (stage.isFullScreen()) {
                stage.setFullScreen(false);
                setUpDefaultScreenMode();
                Model.getInstance().getViewFactory().getIsMediaPlayerInFullScreen().set(false);
            } else {
                stage.setFullScreen(true);
                setUpFullScreenMode();
                Model.getInstance().getViewFactory().getIsMediaPlayerInFullScreen().set(true);

                stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        setUpDefaultScreenMode();
                        Model.getInstance().getViewFactory().getIsMediaPlayerInFullScreen().set(false);
                    }
                });
            }
        });
    }

    private void setSlidersFunctionality() {
        volume_slider.valueProperty().addListener(observable -> {
            video_mediaplayer.setVolume(volume_slider.getValue());
            if (video_mediaplayer.getVolume() != 0.0) {
                volume_icon.setGlyphName("VOLUME_UP");
                volume_icon.setFill(Paint.valueOf("#9c9a9a"));
                volume_button.setGraphic(volume_icon);
                is_muted = false;
            } else {
                volume_icon.setGlyphName("VOLUME_OFF");
                volume_icon.setFill(Paint.valueOf("#9c9a9a"));
                volume_button.setGraphic(volume_icon);
                is_muted = true;
            }
        });

        time_slider.valueChangingProperty().addListener((observableValue, wasChanging, isChanging) -> {
            if (!isChanging) {
                video_mediaplayer.seek(Duration.seconds(time_slider.getValue()));
            }
        });

        time_slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            bindCurrentTimeLabel();
            double current_time = video_mediaplayer.getCurrentTime().toSeconds();
            if (Math.abs(current_time - newValue.doubleValue()) > 0.5) {
                video_mediaplayer.seek(Duration.seconds(newValue.doubleValue()));
            }
            labelMatchEndVideo(current_time_label.getText(), total_time_label.getText());
        });
    }

    private void setMediaPlayerFunctionality() {
        video_mediaplayer.totalDurationProperty().addListener((observableValue, oldDuration, newDuration) -> {
            bindCurrentTimeLabel();
            time_slider.setMax(newDuration.toSeconds());
            total_time_label.setText(getTime(newDuration));
        });

        video_mediaplayer.currentTimeProperty().addListener((observableValue, oldTime, newTime) -> {
            bindCurrentTimeLabel();
            if (!time_slider.isValueChanging()) {
                time_slider.setValue(newTime.toSeconds());
            }
            labelMatchEndVideo(current_time_label.getText(), total_time_label.getText());
        });

        video_mediaplayer.setOnEndOfMedia(() -> {
            PPR_icon.setGlyphName("UNDO");
            PPR_button.setGraphic(PPR_icon);
            at_end_of_video = true;
            if (!current_time_label.textProperty().equals(total_time_label.textProperty())) {
                current_time_label.textProperty().unbind();
                total_time_label.setText(getTime(video_mediaplayer.getTotalDuration()));
            }
        });
    }

    private void setUpDefaultScreenMode() {
        media_info_anchorpane.setVisible(true);
        media_info_anchorpane.setPrefHeight(650);

        media_page_scrollpane.setPrefHeight(724);
        media_page_scrollpane.setPrefWidth(1289.8);
        media_page_scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        media_page_scrollpane.setPadding(new Insets(2,0,0,0));

        video_mediaview.setFitWidth(1120);
        video_mediaview.setFitHeight(480);

        controls_pane.setMinWidth(Region.USE_COMPUTED_SIZE);
        controls_pane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        controls_pane.setPrefWidth(1120);

        parent_vbox.setPrefHeight(1446);

        parent_stackpane.setPrefHeight(1446);
        parent_anchorpane.setPrefWidth(1286);
        parent_borderpane.setPadding(new Insets(0,85,0,85));

        child_stackpane.setPrefWidth(1120);
        child_stackpane.setPrefHeight(477);

        time_slider.setPrefWidth(1118);
        controls_hbox.setPrefWidth(1118);

        episodes_choicebox.setVisible(!media_id.contains("F"));
        fullscreen_icon.setGlyphName("EXPAND");
        fullscreen_icon.setFill(Paint.valueOf("#9c9a9a"));
        fullscreen_button.setGraphic(fullscreen_icon);
    }

    private void setUpFullScreenMode() {
        media_info_anchorpane.setVisible(false);
        media_info_anchorpane.setPrefHeight(0);

        media_page_scrollpane.setPrefHeight(890);
        media_page_scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        media_page_scrollpane.setPadding(new Insets(-4.5,0,0,-5));

        video_mediaview.setFitWidth(1536);
        video_mediaview.setFitHeight(864);

        controls_pane.setMinWidth(Region.USE_PREF_SIZE);
        controls_pane.setMaxWidth(Region.USE_PREF_SIZE);
        controls_pane.setPrefWidth(1500);

        parent_vbox.setPrefHeight(400);

        parent_stackpane.setPrefHeight(400);
        parent_anchorpane.setPrefWidth(1536);
        parent_borderpane.setPadding(new Insets(0,0,0,0));

        child_stackpane.setPrefWidth(1536);
        child_stackpane.setPrefHeight(864);

        time_slider.setPrefWidth(1500);
        controls_hbox.setPrefWidth(1500);

        episodes_choicebox.setVisible(false);
        fullscreen_icon.setGlyphName("EXPAND");
        fullscreen_icon.setFill(Paint.valueOf("#9c9a9a"));
        fullscreen_button.setGraphic(fullscreen_icon);
    }

    private void onChildStackPane() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3), e -> controls_pane.setVisible(false)));

        Timeline cursor_timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> child_stackpane.setCursor(Cursor.DEFAULT)),
                new KeyFrame(Duration.seconds(3), e -> child_stackpane.setCursor(Cursor.NONE))
        );

        child_stackpane.setOnMouseMoved(event -> {
            controls_pane.setVisible(true);
            timeline.stop();
            timeline.playFromStart();

            child_stackpane.setCursor(Cursor.DEFAULT);
            cursor_timeline.stop();
            cursor_timeline.playFromStart();
        });
    }

    private void bindCurrentTimeLabel() {
        current_time_label.textProperty().bind(Bindings.createStringBinding(() -> getTime(video_mediaplayer.getCurrentTime()) + " / ", video_mediaplayer.currentTimeProperty()));
    }

    private String getTime(@NotNull Duration time) {
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) seconds %= 60;
        if (minutes > 59) minutes %= 60;
        if (hours > 59) hours %= 60;

        if (hours > 0) return String.format("%d:%02d:%02d", hours, minutes, seconds);
        else return String.format("%02d:%02d", minutes, seconds);
    }

    private void labelMatchEndVideo(String current_time_label, @NotNull String total_time_label) {
        for (int i = 0; i < total_time_label.length(); i++) {
            if (current_time_label.charAt(i) != total_time_label.charAt(i)) {
                at_end_of_video = false;
                if (is_playing) {
                    PPR_icon.setGlyphName("PAUSE");
                    PPR_button.setGraphic(PPR_icon);
                } else {
                    PPR_icon.setGlyphName("PLAY");
                    PPR_button.setGraphic(PPR_icon);
                }
                break;
            } else {
                at_end_of_video = true;
                PPR_icon.setGlyphName("UNDO");
                PPR_button.setGraphic(PPR_icon);
            }
        }
    }

    /* Add to User's Library Buttons */
    private void configureButtons() {
        List<String> buttons_statuses = Model.getInstance().loadUserMediaData(user_id, media_id);

        if (buttons_statuses.contains("Now watching")) {
            buttonStyle(now_watching_button, now_watching_icon, "Added to Now Watching");
            addHoverEffect(now_watching_button);
        }
        if (buttons_statuses.contains("Favorites")) {
            buttonStyle(favorites_button, favorites_icon, "Added to Favorites");
            addHoverEffect(favorites_button);
        }
        if (buttons_statuses.contains("Watch list")) {
            buttonStyle(watch_list_button, watch_list_icon, "Added to Watch List");
            addHoverEffect(watch_list_button);
        }
    }

    private void buttonStyle(@NotNull JFXButton button, @NotNull FontAwesomeIconView icon, String button_status) {
        button.setText(button_status);

        if (button_status.contains("Add to")) {
            icon.setFill(Paint.valueOf("transparent"));
            icon.setStroke(Paint.valueOf("#b0b0b0"));
            button.getStyleClass().remove(2);
            button.getStyleClass().add("default-button");
        } else {
            icon.setFill(Paint.valueOf("#b0b0b0"));
            icon.setStroke(Paint.valueOf("transparent"));
            button.getStyleClass().remove(2);
            button.getStyleClass().add("clicked-button");
        }
    }

    private void addHoverEffect(JFXButton button) {
        ScaleTransition scale_up = new ScaleTransition(Duration.millis(280), button);
        scale_up.setToX(1.03);
        scale_up.setToY(1.03);

        ScaleTransition scale_down = new ScaleTransition(Duration.millis(280), button);
        scale_down.setToX(1);
        scale_down.setToY(1);

        DropShadow drop_shadow = new DropShadow();
        drop_shadow.setRadius(10);
        drop_shadow.setSpread(0.3);
        drop_shadow.setColor(Color.rgb(255, 255, 255, 0.1));

        button.setOnMouseEntered(event -> {
            scale_up.play();
            button.setEffect(drop_shadow);
        });
        button.setOnMouseExited(event -> {
            scale_down.play();
            button.setEffect(null);
        });
    }

    private void onNowWatchingButton() {
        status = "Now watching";
        if (now_watching_button.getText().contains("Add to")) {
            buttonStyle(now_watching_button, now_watching_icon, "Added to Now Watching");
            Model.getInstance().getDatabaseDriver().addUserMediaData(user_id, media_id, status);
        } else {
            buttonStyle(now_watching_button, now_watching_icon, "Add to Now Watching");
            Model.getInstance().getDatabaseDriver().removeUserMediaData(user_id, media_id, status);
        }
    }

    private void onFavoritesButton() {
        status = "Favorites";
        if (favorites_button.getText().contains("Add to")) {
            buttonStyle(favorites_button, favorites_icon, "Added to Favorites");
            Model.getInstance().getDatabaseDriver().addUserMediaData(user_id, media_id, status);
        } else {
            buttonStyle(favorites_button, favorites_icon, "Add to Favorites");
            Model.getInstance().getDatabaseDriver().removeUserMediaData(user_id, media_id, status);
        }
    }

    private void onWatchListButton() {
        status = "Watch list";
        if (watch_list_button.getText().contains("Add to")) {
            buttonStyle(watch_list_button, watch_list_icon, "Added to Watch List");
            Model.getInstance().getDatabaseDriver().addUserMediaData(user_id, media_id, status);
        } else {
            buttonStyle(watch_list_button, watch_list_icon, "Add to Watch List");
            Model.getInstance().getDatabaseDriver().removeUserMediaData(user_id, media_id, status);
        }
    }
}
