package data.dto;

import data.common.Col;
import data.common.Entity;

import java.time.ZonedDateTime;

public class WatchListDTO implements Entity {
    @Col(name="watchlist.id")
    private Integer id;

    @Col(name="user_id")
    private Integer userId;

    @Col(name="movie_id")
    private Integer movieId;

    @Col(name="director_id")
    private Integer directorId;

    @Col(name="watchlist.personalRating")
    private Integer pesonalRating;

    @Col(name="watchlist.createdAt")
    private ZonedDateTime createdAt;

    @Col(name="watchlist.watched")
    private String watched;

    @Col(name="movies.title")
    private String movieTitle;

    @Col(name="movies.title")
    private String movieDescription;

    @Col(name="movies.year")
    private Integer movieYear;

    @Col(name="movies.rtPath")
    private String movieRtPath;

    @Col(name="movies.posterUrl")
    private String moviePosterUrl;

    @Col(name="movies.imdbRating")
    private Integer movieImdbRating;

    @Col(name="movies.imdbRating")
    private Integer movieMcRating;

    @Col(name="movies.rtRating")
    private Integer movieRtRating;

    @Col(name="movies.rtaRating")
    private Integer movieRtaRating;

    @Col(name="directors.name")
    private String directorName;

    @Col(name="directors.rtPath")
    private String directorRtPath;

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

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public Integer getPesonalRating() {
        return pesonalRating;
    }

    public void setPesonalRating(Integer pesonalRating) {
        this.pesonalRating = pesonalRating;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getWatched() {
        return watched;
    }

    public void setWatched(String watched) {
        this.watched = watched;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieRtPath() {
        return movieRtPath;
    }

    public void setMovieRtPath(String movieRtPath) {
        this.movieRtPath = movieRtPath;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public Integer getMovieImdbRating() {
        return movieImdbRating;
    }

    public void setMovieImdbRating(Integer movieImdbRating) {
        this.movieImdbRating = movieImdbRating;
    }

    public Integer getMovieMcRating() {
        return movieMcRating;
    }

    public void setMovieMcRating(Integer movieMcRating) {
        this.movieMcRating = movieMcRating;
    }

    public Integer getMovieRtRating() {
        return movieRtRating;
    }

    public void setMovieRtRating(Integer movieRtRating) {
        this.movieRtRating = movieRtRating;
    }

    public Integer getMovieRtaRating() {
        return movieRtaRating;
    }

    public void setMovieRtaRating(Integer movieRtaRating) {
        this.movieRtaRating = movieRtaRating;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDirectorRtPath() {
        return directorRtPath;
    }

    public void setDirectorRtPath(String directorRtPath) {
        this.directorRtPath = directorRtPath;
    }

}
