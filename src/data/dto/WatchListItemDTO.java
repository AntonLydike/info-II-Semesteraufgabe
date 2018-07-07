package data.dto;

import data.common.Col;
import data.common.Entity;

public class WatchListItemDTO implements Entity {
    @Col(name="id")
    private Integer id;

    @Col(name="user_id")
    private Integer userId;

    @Col(name="movie_id")
    private Integer movieId;

    @Col(name="personalRating")
    private Integer personalRating;

    @Col(name="watched")
    private Integer watched;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Integer personalRating) {
        this.personalRating = personalRating;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }
}
