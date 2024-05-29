package com.de.project_demedia_app.Models;

import com.de.project_demedia_app.Views.DatabaseTables;
import com.de.project_demedia_app.Views.ViewFactory;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private static Model model;
    private ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private final User user;
    private boolean userLoginSuccessFlag;
    private final BooleanProperty isUserLoggedOutFlag;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        this.user = new User("", "", "", "");
        this.userLoginSuccessFlag = false;
        this.isUserLoggedOutFlag = new SimpleBooleanProperty();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void setViewFactory(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public User getUser() {
        return user;
    }

    public boolean getUserLoginSuccessFlag() {
        return userLoginSuccessFlag;
    }

    public BooleanProperty getIsUserLoggedOutFlag() {
        return isUserLoggedOutFlag;
    }

    public void loadUser(String username, String password) {
        ResultSet result_set = databaseDriver.getUserData(username, password);
        try {
            if (result_set.isBeforeFirst()) {
                this.user.usernameProperty().set(result_set.getString("username"));
                this.user.idProperty().set(result_set.getString("user_id"));
                this.userLoginSuccessFlag = true;
            }
        } catch (Exception ignored) {
        }
    }

    public boolean checkUsername(String username) {
        ResultSet result_set = databaseDriver.getUserByUsername(username);
        boolean is_valid = false;
        try {
            is_valid = result_set.getString("username") == null;
        } catch (Exception ignored) {}
        return is_valid;
    }

    public boolean checkEmail(String email) {
        ResultSet result_set = databaseDriver.getUserByEmail(email);
        boolean is_valid = false;
        try {
            is_valid = result_set.getString("email") == null;
        } catch (Exception ignored) {}
        return is_valid;
    }

    public List<String> loadUserMediaData(String user_id, String media_id) {
        ResultSet result_set = databaseDriver.getUserMediaData(user_id, media_id);
        List<String> media_statuses = new ArrayList<>();
        try {
            while (result_set.next()) {
                media_statuses.add(result_set.getString("status"));
            }
        } catch (Exception ignored) {}
        return media_statuses;
    }

    public ObservableList<Banner> loadLimitedBanners(DatabaseTables table) {
        ResultSet result_set = databaseDriver.getLimitedDataFromTable(table);

        return loadBanners(table, result_set);
    }

    public ObservableList<Banner> loadAllBanners(DatabaseTables table) {
        ResultSet result_set = databaseDriver.getSortedDataFromTable(table);

        return loadBanners(table, result_set);
    }

    public ObservableList<Banner> loadStatusMediaBanners(DatabaseTables table, String media_id, String status) {
        ResultSet result_set = databaseDriver.getStatusMediaData(media_id, status);

        return loadBanners(table, result_set);
    }

    public ObservableList<Banner> loadBanners(DatabaseTables table, ResultSet result_set) {
        ObservableList<Banner> banners = FXCollections.observableArrayList();

        try {
            String id;
            Image image;
            byte[] image_bytes;

            while (result_set.next()) {
                if (table == DatabaseTables.AnimeList) {
                    id = result_set.getString("anime_id");
                    image_bytes = result_set.getBytes("anime_cover");
                }
                else if (table == DatabaseTables.FilmsList) {
                    id = result_set.getString("film_id");
                    image_bytes = result_set.getBytes("film_cover");
                }
                else {
                    id = result_set.getString("series_id");
                    image_bytes = result_set.getBytes("series_cover");
                }

                String title = result_set.getString("title");
                String extended_type = result_set.getString("extended_type");
                String genres = (result_set.getString("genres")).replace("\\n", System.lineSeparator());
                double rating = result_set.getDouble("rating");
                String reviews = result_set.getString("reviews");

                image = new Image(new ByteArrayInputStream(image_bytes));
                ImageView cover = new ImageView(image);

                banners.add(new Banner(id, title, extended_type, genres, rating, reviews, cover));
            }
        } catch (Exception ignored) {
        }

        return banners;
    }

    public Map<String, String> getAllMediaTitles() {
        ResultSet result_set1 = databaseDriver.getSortedDataFromTable(DatabaseTables.AnimeList);
        ResultSet result_set2 = databaseDriver.getSortedDataFromTable(DatabaseTables.FilmsList);
        ResultSet result_set3 = databaseDriver.getSortedDataFromTable(DatabaseTables.SeriesList);
        Map<String, String> all_media_titles = new HashMap<>();

        try {
            while (result_set1.next()) {
                all_media_titles.put((result_set1.getString("title")).replace("\\n", " "), result_set1.getString("anime_id"));
            }
            while (result_set2.next()) {
                all_media_titles.put((result_set2.getString("title")).replace("\\n", " "), result_set2.getString("film_id"));
            }
            while (result_set3.next()) {
                all_media_titles.put((result_set3.getString("title")).replace("\\n", " "), result_set3.getString("series_id"));
            }
        } catch (Exception ignored) {}

        return all_media_titles;
    }

    public MediaPage getMediaPage(String media_id) {
        ResultSet result_set = databaseDriver.getMediaDataById(media_id);
        MediaPage media_page = null;

        try {
            String title = (result_set.getString("title")).replace("\\n", " ");
            String type = result_set.getString("type");
            String genres = (result_set.getString("genres")).replace("\\n", " ");
            double rating = result_set.getDouble("rating");
            String description = result_set.getString("description");

            String status;
            String release_date;
            String episodes;
            String runtime;
            String studios;
            String directors;
            String creators;
            String movie_url;
            Image image;
            Image background_image;
            byte[] image_bytes;
            byte[] background_image_bytes;

            if (media_id.contains("A")) {
                status = result_set.getString("status");
                episodes = result_set.getString("episodes");
                studios = result_set.getString("studios");

                if (result_set.getString("extended_type").contains("TV Mini Series")) {
                    type = result_set.getString("extended_type").substring(0, 14);
                } else {
                    type = result_set.getString("extended_type").substring(0, 9);
                }

                image_bytes = result_set.getBytes("big_anime_cover");
                image = new Image(new ByteArrayInputStream(image_bytes));
                background_image_bytes = result_set.getBytes("background_anime_cover");
                background_image = new Image(new ByteArrayInputStream(background_image_bytes));
                media_page = new MediaPage(title, status, type, episodes, genres, studios, rating, description, image, background_image);

            } else if (media_id.contains("F")) {
                release_date = result_set.getString("release_date");
                runtime = result_set.getString("runtime");
                directors = result_set.getString("directors");
                movie_url = result_set.getString("movie_url");

                image_bytes = result_set.getBytes("big_film_cover");
                image = new Image(new ByteArrayInputStream(image_bytes));
                background_image_bytes = result_set.getBytes("background_film_cover");
                background_image = new Image(new ByteArrayInputStream(background_image_bytes));
                media_page = new MediaPage(image, title, release_date, type, runtime, genres, directors, rating, description, movie_url, background_image);

            } else {
                status = result_set.getString("status");
                episodes = result_set.getString("episodes");
                runtime = result_set.getString("runtime");
                creators = result_set.getString("creators");

                if (result_set.getString("extended_type").contains("TV Mini Series")) {
                    type = result_set.getString("extended_type").substring(0, 14);
                } else {
                    type = result_set.getString("extended_type").substring(0, 9);
                }

                image_bytes = result_set.getBytes("big_series_cover");
                image = new Image(new ByteArrayInputStream(image_bytes));
                background_image_bytes = result_set.getBytes("background_series_cover");
                background_image = new Image(new ByteArrayInputStream(background_image_bytes));
                media_page = new MediaPage(title, image, status, type, episodes, genres, creators, runtime, rating, description, background_image);
            }
        } catch (Exception ignored) {
        }

        return media_page;
    }

    public List<String> getEpisodes(String media_id) {
        ResultSet result_set = databaseDriver.getEpisodesDataById(media_id);
        List<String> episodes_URLS = new ArrayList<>();
        try {
            while (result_set.next()) {
                episodes_URLS.add(result_set.getString("episode_url"));
            }
        } catch (Exception ignored) {
        }
        return episodes_URLS;
    }
}
