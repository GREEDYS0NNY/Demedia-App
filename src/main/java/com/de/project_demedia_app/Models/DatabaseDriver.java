package com.de.project_demedia_app.Models;

import com.de.project_demedia_app.Views.DatabaseTables;
import org.jetbrains.annotations.NotNull;
import java.sql.*;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:demedia.db");
        } catch (SQLException ignored) {
        }
    }

    public void addUserData(String user_id, String username, String email, String password) {
        PreparedStatement prepared_statement;
        try {
            prepared_statement = this.connection.prepareStatement("INSERT INTO Users (user_id, username, email, password) VALUES (?, ?, ?, ?);");
            prepared_statement.setString(1, user_id);
            prepared_statement.setString(2, username);
            prepared_statement.setString(3, email);
            prepared_statement.setString(4, password);
            prepared_statement.executeUpdate();

        } catch (SQLException ignored) {}
    }

    public void addUserMediaData(String user_id, String media_id, String status) {
        PreparedStatement prepared_statement;
        try {
            prepared_statement = this.connection.prepareStatement("INSERT INTO User_Media (user_id, media_id, status) VALUES (?, ?, ?);");
            prepared_statement.setString(1, user_id);
            prepared_statement.setString(2, media_id);
            prepared_statement.setString(3, status);
            prepared_statement.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public ResultSet getUserMediaData(String user_id, String media_id) {
        PreparedStatement prepared_statement;
        ResultSet result_set = null;
        try {
            prepared_statement = this.connection.prepareStatement("SELECT * FROM User_Media WHERE user_id=? AND media_id=?;");
            prepared_statement.setString(1, user_id);
            prepared_statement.setString(2, media_id);
            result_set = prepared_statement.executeQuery();
        } catch (SQLException ignored) {}
        return result_set;
    }

    public void removeUserMediaData(String user_id, String media_id, String status) {
        PreparedStatement prepared_statement;
        try {
            prepared_statement = this.connection.prepareStatement("DELETE FROM User_Media WHERE user_id=? AND media_id=? AND status=?;");
            prepared_statement.setString(1, user_id);
            prepared_statement.setString(2, media_id);
            prepared_statement.setString(3, status);
            prepared_statement.executeUpdate();
        } catch (SQLException ignored) { }
    }

    public ResultSet getUserData(String username, String password) {
        PreparedStatement prepared_statement;
        ResultSet result_set = null;
        try {
            prepared_statement = this.connection.prepareStatement("SELECT * FROM Users WHERE username=? AND password=?;");
            prepared_statement.setString(1, username);
            prepared_statement.setString(2, password);
            result_set = prepared_statement.executeQuery();
        } catch (SQLException ignored) {
        }
        return result_set;
    }

    public ResultSet getUserByUsername(String username) {
        PreparedStatement prepared_statement;
        ResultSet result_set = null;
        try {
            prepared_statement = this.connection.prepareStatement("SELECT * FROM Users WHERE username=?;");
            prepared_statement.setString(1, username);
            result_set = prepared_statement.executeQuery();
        } catch (SQLException ignored) {}
        return result_set;
    }

    public ResultSet getUserByEmail(String email) {
        PreparedStatement prepared_statement;
        ResultSet result_set = null;
        try {
            prepared_statement = this.connection.prepareStatement("SELECT * FROM Users WHERE email=?;");
            prepared_statement.setString(1, email);
            result_set = prepared_statement.executeQuery();
        } catch (SQLException ignored) {}
        return result_set;
    }

    public ResultSet getSortedDataFromTable(DatabaseTables table) {
        Statement statement;
        ResultSet result_set = null;
        try {
            statement = this.connection.createStatement();
            result_set = statement.executeQuery("SELECT * FROM '"+table+"' ORDER BY rating DESC;");
        } catch (SQLException ignored) {}
        return result_set;
    }

    public ResultSet getLimitedDataFromTable(@NotNull DatabaseTables table) {
        Statement statement;
        ResultSet result_set = null;
        try {
            statement = this.connection.createStatement();
            if (table.equals(DatabaseTables.AnimeList)) {
                result_set = statement.executeQuery("SELECT * FROM AnimeList WHERE anime_id IN ('A1', 'A9', 'A5', 'A4', 'A3') ORDER BY extended_type;");
            } else if (table.equals(DatabaseTables.FilmsList)) {
                result_set = statement.executeQuery("SELECT * FROM FilmsList WHERE film_id IN ('F3', 'F1', 'F2', 'F6', 'F4') ORDER BY runtime DESC;");
            } else if (table.equals(DatabaseTables.SeriesList)) {
                result_set = statement.executeQuery("SELECT * FROM SeriesList WHERE series_id IN ('S2', 'S4', 'S6', 'S1', 'S8') ORDER BY extended_type;");
            }

        } catch (SQLException ignored) {}
        return result_set;
    }

    public ResultSet getStatusMediaData(String media_id, String status) {
        PreparedStatement prepared_statement;
        Statement statement;
        ResultSet result_set = null;
        ResultSet media_ids_result_set;
        String media_ids_string;
        StringBuilder media_ids = new StringBuilder();
        try {
            prepared_statement = this.connection.prepareStatement("SELECT media_id FROM User_Media WHERE media_id LIKE ? AND status=?;");
            prepared_statement.setString(1, media_id + "%");
            prepared_statement.setString(2, status);
            media_ids_result_set = prepared_statement.executeQuery();

            if (media_ids_result_set.next()) {
                do {
                    media_ids.append("'").append(media_ids_result_set.getString("media_id")).append("',");
                } while (media_ids_result_set.next());

                media_ids_string = media_ids.substring(0, media_ids.length() - 1);

                statement = this.connection.createStatement();
                if (media_id.contains("A")) {
                    result_set = statement.executeQuery("SELECT * FROM AnimeList WHERE anime_id IN ("+media_ids_string+");");
                } else if (media_id.contains("F")) {
                    result_set = statement.executeQuery("SELECT * FROM FilmsList WHERE film_id IN ("+media_ids_string+");");
                } else if (media_id.contains("S")) {
                    result_set = statement.executeQuery("SELECT * FROM SeriesList WHERE series_id IN ("+media_ids_string+");");
                }
            }
        } catch (SQLException ignored) {}
        return result_set;
    }

    public ResultSet getMediaDataById(@NotNull String media_id) {
        PreparedStatement prepared_statement;
        ResultSet result_set = null;

        try {
            if (media_id.contains("A")) {
                prepared_statement = this.connection.prepareStatement("SELECT * FROM AnimeList WHERE anime_id=?;");
                prepared_statement.setString(1, media_id);
                result_set = prepared_statement.executeQuery();
            } else if (media_id.contains("F")) {
                prepared_statement = this.connection.prepareStatement("SELECT * FROM FilmsList WHERE film_id=?;");
                prepared_statement.setString(1, media_id);
                result_set = prepared_statement.executeQuery();
            }
            else {
                prepared_statement = this.connection.prepareStatement("SELECT * FROM SeriesList WHERE series_id=?;");
                prepared_statement.setString(1, media_id);
                result_set = prepared_statement.executeQuery();
            }
        } catch (SQLException ignored) {}

        return result_set;
    }

    public ResultSet getEpisodesDataById(@NotNull String media_id) {
        PreparedStatement prepared_statement;
        ResultSet result_set = null;

        try {
            if (media_id.contains("A")) {
                prepared_statement = this.connection.prepareStatement("SELECT * FROM AnimeEpisodes WHERE anime_id=?;");
                prepared_statement.setString(1, media_id);
                result_set = prepared_statement.executeQuery();
            } else if (media_id.contains("S")) {
                prepared_statement = this.connection.prepareStatement("SELECT * FROM SeriesEpisodes WHERE series_id=?;");
                prepared_statement.setString(1, media_id);
                result_set = prepared_statement.executeQuery();
            }
        } catch (SQLException ignored) {}

        return result_set;
    }
}
