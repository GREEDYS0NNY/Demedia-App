package com.de.project_demedia_app.Models;

import javafx.scene.image.Image;

public class MediaPage {
    private final String title, type, genres, description;
    private String status, release_date, episodes, runtime, studios, directors, creators, movie_URL;
    private final double rating;
    private final Image cover, background_cover;

    public MediaPage(String title, String status, String type, String episodes, String genres, String studios, double rating, String description, Image cover, Image background_cover) {
        this.title = title;
        this.status = status;
        this.type = type;
        this.episodes = episodes;
        this.genres = genres;
        this.studios = studios;
        this.rating = rating;
        this.description = description;
        this.cover = cover;
        this.background_cover = background_cover;
    }

    public MediaPage(Image cover, String title, String release_date, String type, String runtime, String genres, String directors, double rating, String description, String movie_URL, Image background_cover) {
        this.cover = cover;
        this.title = title;
        this.release_date = release_date;
        this.type = type;
        this.runtime = runtime;
        this.genres = genres;
        this.directors = directors;
        this.rating = rating;
        this.description = description;
        this.movie_URL = movie_URL;
        this.background_cover = background_cover;
    }

    public MediaPage(String title, Image cover, String status, String type, String episodes, String genres, String creators, String runtime, double rating, String description, Image background_cover) {
        this.cover = cover;
        this.title = title;
        this.status = status;
        this.type = type;
        this.episodes = episodes;
        this.runtime = runtime;
        this.genres = genres;
        this.creators = creators;
        this.rating = rating;
        this.description = description;
        this.background_cover = background_cover;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getType() {
        return type;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getStudios() {
        return studios;
    }

    public String getDirectors() {
        return directors;
    }

    public String getCreators() {
        return creators;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getMovieURL() {
        return movie_URL;
    }

    public Image getCover() {
        return cover;
    }

    public Image getBackgroundCover() {
        return background_cover;
    }
}
