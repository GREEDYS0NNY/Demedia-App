package com.de.project_demedia_app.Models;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.de.project_demedia_app.Views.LeftPanelOptions;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.ContentDisplay;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javafx.collections.ObservableList;
import javafx.animation.ScaleTransition;
import com.jfoenix.controls.JFXButton;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import java.util.ArrayList;
import java.util.List;

public class Banner {
    private final String id, title, extended_type, genres, reviews;
    private final double rating;
    private final ImageView image;

    public Banner(String id, String title, String extended_type, String genres, double rating, String reviews, ImageView image) {
        this.id = id;
        this.title = title;
        this.extended_type = extended_type;
        this.genres = genres;
        this.rating = rating;
        this.reviews = reviews;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExtendedType() {
        return extended_type;
    }

    public String getGenres() {
        return genres;
    }

    public double getRating() {
        return rating;
    }

    public String getReviews() {
        return reviews;
    }

    public ImageView getImage() {
        return image;
    }

    @Contract("_ -> param1")
    public static @NotNull ImageView styleImage(@NotNull ImageView banner_cover) {
        Rectangle clip = new Rectangle(107, 155);

        clip.setArcWidth(8);
        clip.setArcHeight(8);
        clip.setFill(Paint.valueOf("141414"));
        clip.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0);" +
                "-fx-background-radius: 50%;");

        banner_cover.setFitHeight(155);
        banner_cover.setFitWidth(107);
        banner_cover.setClip(clip);

        return banner_cover;
    }

    public static @NotNull Label styleTitle(@NotNull String title) {
        Label banner_title = new Label(title.replace("\\n", System.lineSeparator()));
        banner_title.setFont(Font.font("Microsoft Sans Serif", 20));
        banner_title.setPrefWidth(135);
        banner_title.setPrefHeight(47);
        banner_title.alignmentProperty().set(Pos.TOP_LEFT);
        banner_title.setStyle("-fx-text-fill: #B0B0B0");

        return banner_title;
    }

    public static @NotNull Label styleType(String extended_type) {
        Label banner_extended_type = new Label(extended_type);
        banner_extended_type.alignmentProperty().set(Pos.CENTER_LEFT);
        banner_extended_type.setStyle("-fx-text-fill: #B0B0B0");

        return banner_extended_type;
    }

    public static @NotNull Label styleGenres(String genres) {
        Label banner_genres = new Label("Genres: " + genres);
        banner_genres.alignmentProperty().set(Pos.CENTER_LEFT);
        banner_genres.setPrefWidth(140);
        banner_genres.setPrefHeight(50);
        banner_genres.setPadding(new Insets(5, 0, 0, 0));
        banner_genres.setStyle("-fx-text-fill: #B0B0B0");

        return banner_genres;
    }

    public static @NotNull Label styleRate(double rating) {
        Label banner_rating = new Label(String.valueOf(rating));
        banner_rating.setFont(Font.font("System", FontWeight.BOLD, 14));
        banner_rating.alignmentProperty().set(Pos.CENTER_LEFT);
        banner_rating.setPrefWidth(24);
        banner_rating.setPrefHeight(20);
        banner_rating.setPadding(new Insets(0, 0, 0, 4));
        banner_rating.setStyle("-fx-text-fill: #B0B0B0");

        return banner_rating;
    }

    public static @NotNull Label styleOutOf10() {
        Label banner_out_of_10 = new Label("/10");
        banner_out_of_10.setStyle("-fx-text-fill: #B0B0B0");
        banner_out_of_10.setPadding(new Insets(1.5, 0, 0, 0));

        return banner_out_of_10;
    }

    public static @NotNull Label styleReviews(String reviews) {
        Label banner_reviews = new Label(reviews);
        banner_reviews.setFont(Font.font("System", 10));
        banner_reviews.alignmentProperty().set(Pos.CENTER_LEFT);
        banner_reviews.setPrefWidth(30);
        banner_reviews.setStyle("-fx-text-fill: #B0B0B0");

        return banner_reviews;
    }

    public static @NotNull HBox constructRatingHBox(Label rate, Label out_of_10) {
        FontAwesomeIconView star_icon = new FontAwesomeIconView(FontAwesomeIcon.STAR);
        star_icon.setSize("1.5em");
        star_icon.setFill(Paint.valueOf("#eac019"));

        HBox rating_hbox = new HBox(star_icon, rate, out_of_10);

        rating_hbox.setAlignment(Pos.TOP_LEFT);
        rating_hbox.setPrefWidth(63);
        rating_hbox.setPrefHeight(20);
        rating_hbox.setMaxWidth(Region.USE_PREF_SIZE);

        return rating_hbox;
    }

    public static @NotNull HBox constructReviewsHBox(Label reviews) {
        HBox reviews_hbox = new HBox(reviews);

        if (reviews.getText().length() >= 4 & reviews.getText().contains("K")) {
            reviews_hbox.setPadding(new Insets(0, 0, 0, 20));
        }
        else if (reviews.getText().length() >= 4 & reviews.getText().contains("M")) {
            reviews_hbox.setPadding(new Insets(0, 0, 0, 19));
        }
        else { reviews_hbox.setPadding(new Insets(0, 0, 0, 22)); }

        reviews_hbox.setPrefWidth(50);
        reviews_hbox.setPrefHeight(18);

        reviews_hbox.setAlignment(Pos.TOP_LEFT);
        reviews_hbox.setMaxWidth(Region.USE_PREF_SIZE);

        return reviews_hbox;
    }

    public static @NotNull VBox constructBannerInfo(Label name, Label type, Label genres, HBox rating_hbox, HBox reviews_hbox) {
        VBox vbox = new VBox(name, type, genres, rating_hbox, reviews_hbox);
        vbox.alignmentProperty().set(Pos.CENTER_LEFT);
        vbox.setPadding(new Insets(0, 0, 0, -5));
        vbox.setPrefWidth(136);
        vbox.setPrefHeight(186);

        return vbox;
    }

    public static @NotNull HBox constructBanner(ImageView image, VBox banner_info) {
        HBox hbox = new HBox(image, banner_info);

        hbox.setPrefWidth(261);
        hbox.setPrefHeight(186);

        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: transparent;" +
                "-fx-background-radius: 15;");
        hbox.setCursor(Cursor.cursor("hand"));
        hbox.setSpacing(15);

        return hbox;
    }

    public static @NotNull JFXButton constructBannerButton(HBox banner) {
        JFXButton banner_button = new JFXButton();

        ScaleTransition scale_up = new ScaleTransition(Duration.millis(300), banner_button);
        scale_up.setToX(1.02);
        scale_up.setToY(1.02);
        ScaleTransition scale_down = new ScaleTransition(Duration.millis(300), banner_button);
        scale_down.setToX(1);
        scale_down.setToY(1);

        banner_button.setText(" ");
        banner_button.setPrefWidth(262.5);
        banner_button.setPrefHeight(186);
        banner_button.setMinHeight(Region.USE_PREF_SIZE);
        banner_button.setMinWidth(Region.USE_PREF_SIZE);
        banner_button.setMaxHeight(Region.USE_PREF_SIZE);
        banner_button.setMaxWidth(Region.USE_PREF_SIZE);
        banner_button.setAlignment(Pos.BASELINE_LEFT);
        banner_button.setContentDisplay(ContentDisplay.CENTER);
        banner_button.setCursor(Cursor.HAND);
        banner_button.setFocusTraversable(false);
        banner_button.setGraphic(banner);
        banner_button.setStyle("-fx-background-color: #2D2D2D; -fx-background-radius: 15; " +
                "-fx-border-color: #3C3C3C; -fx-border-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 0);");

        banner_button.setOnMouseEntered(event -> {
            scale_up.play();
            banner_button.setStyle("-fx-background-color: #373737; -fx-background-radius: 15; " +
                    "-fx-border-color: #3C3C3C; -fx-border-radius: 15;" +
                    "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.4), 20, 0, 0, 0);");
        });
        banner_button.setOnMouseExited(event -> {
            scale_down.play();
            banner_button.setStyle("-fx-background-color: #2D2D2D; -fx-background-radius: 15; " +
                    "-fx-border-color: #3C3C3C; -fx-border-radius: 15;" +
                    "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 0);");
        });

        return banner_button;
    }

    public static @NotNull List<JFXButton> constructBanners(@NotNull ObservableList<Banner> banners) {
        List<JFXButton> banner_buttons = new ArrayList<>();
        for (Banner banner_object : banners) {
            ImageView banner_cover = styleImage(banner_object.getImage());
            Label banner_title = styleTitle(banner_object.getTitle());
            Label banner_extended_type = styleType(banner_object.getExtendedType());
            Label banner_genres = styleGenres(banner_object.getGenres());
            Label banner_rating = styleRate(banner_object.getRating());
            Label banner_out_of_10 = styleOutOf10();
            Label banner_reviews = styleReviews(banner_object.getReviews());

            HBox reviews_hbox = constructReviewsHBox(banner_reviews);
            HBox rating_hbox = constructRatingHBox(banner_rating, banner_out_of_10);
            VBox banner_info = constructBannerInfo(banner_title, banner_extended_type, banner_genres, rating_hbox, reviews_hbox);

            HBox banner = constructBanner(banner_cover, banner_info);

            JFXButton banner_button = constructBannerButton(banner);
            banner_button.setId(banner_object.getId());
            onMediaBanner(banner_button);
            banner_buttons.add(banner_button);
        }
        return banner_buttons;
    }

    public static void addBanners(@NotNull List<JFXButton> banner_buttons, GridPane grid_pane) {
        int index = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (index < banner_buttons.size()) {
                    grid_pane.add(banner_buttons.get(index), col, row);
                    index++;
                }
            }
        }
    }

    public static void onMediaBanner(@NotNull JFXButton button) {
        button.setOnAction(event -> {
            Model.getInstance().getViewFactory().getUserSelectedMediaId().set(button.getId());
            Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(LeftPanelOptions.MEDIA_PAGE);
        });
    }
}
