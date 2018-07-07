package service;

import data.Movie;
import data.User;
import data.dto.MovieDTO;
import data.dto.PersonDTO;
import data.dto.UserDTO;
import data.store.movie.MovieDao;
import data.store.person.PersonDao;
import data.store.user.UserDao;
import exception.LoginFailedException;
import exception.RegisterFailedException;
import gui.LayoutController;

import java.sql.SQLException;

public class UserService {

    UserDao userDao;
    MovieDao movieDao;
    PersonDao personDao;
    LayoutController layout = LayoutController.instance();

    public UserService() {
        try {
            userDao = UserDao.instance();
            movieDao = MovieDao.instance();
            personDao = PersonDao.instance();
        } catch (SQLException e) {
            layout.error("Could not connect to Database! For more details see the log file or contact the administrator.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            layout.error("Unexpected error occurred. Please see the log files for more details.");
        }
    }

    public User login(String name, String password) throws LoginFailedException {
        UserDTO dto = userDao.loginUser(name, password);
        return new User(dto.getId(), dto.getUsername());
    }

    /**
     * Register a new User
     * @param name the username for the new user
     * @param password the password for the new user
     * @return
     * @throws RegisterFailedException thrown, if the username is already taken, name or password too long
     */
    public boolean register(String name, String password) throws RegisterFailedException {
        return userDao.register(name, password);
    }


    /**
     * Adds a movie to the watchlist of a user.
     * If the movie does not already exists in the database, it will be added.
     * If the director (person) does not already exists in the database, it will be added.
     * @param userId identifier of the current user
     * @param movie the movie which is added to the watchlist of the user
     */
    public void addToWatchList(int userId, Movie movie) {
        try {
            PersonDTO director = personDao.upsert(movie.getDirector());
            MovieDTO movieDTO = movieDao.upsert(movie, director.getId());
            userDao.addoWatchListItem(userId, movieDTO.getId());

        } catch (SQLException e) {
            e.printStackTrace();
            layout.error("Could not add movie to your personal watchlist.");
        }
    }

    /**
     * removes a movie from the personal watchlist of the user
     * @param userId identifier for the current user
     * @param movie the movie which is removed from the watchlist of the user
     * @return
     */
    public boolean removeFromWatchlist(int userId, Movie movie) {
        try {
            MovieDTO movieDTO = movieDao.searchByRtPath(movie.getRtPath());
            return userDao.removeWatchListItem(userId, movieDTO.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            layout.error("Could not remove movie from your personal watchlist.");
        }
        return false;
    }

    public boolean setWatched(int userId, Movie movie) {
        try {
            MovieDTO movieDTO = movieDao.searchByRtPath(movie.getRtPath());
            return userDao.setWatchListItemWatched(userId, movieDTO.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            layout.error("Could not set movie as watched from your personal watchlist.");
        }
        return false;
    }

    public boolean setRating(int userId, Movie movie, int rating) {
        try {
            MovieDTO movieDTO = movieDao.searchByRtPath(movie.getRtPath());
            return userDao.setWatchListItemRating(userId, movieDTO.getId(), rating);
        } catch (SQLException e) {
            e.printStackTrace();
            layout.error("Could not set movie as watched from your personal watchlist.");
        }
        return false;
    }
}
