package service;

import data.Movie;
import data.User;
import data.dto.MovieDTO;
import data.dto.PersonDTO;
import data.dto.UserDTO;
import data.store.movie.MovieDao;
import data.store.person.PersonDao;
import data.store.user.UserDao;
import data.store.watchlist.WatchListDao;
import exception.LoginFailedException;
import exception.RegisterFailedException;
import gui.LayoutController;

import java.sql.SQLException;

public class UserService {

    UserDao userDao;
    MovieDao movieDao;
    PersonDao personDao;
    WatchListDao watchListDao;

    /**
     *
     * @throws SQLException thrown if the connection to the database is not possible
     * @throws ClassNotFoundException thrown if an unexpected error occurs during initialization
     */
    public UserService() throws SQLException, ClassNotFoundException {
        userDao = UserDao.instance();
        movieDao = MovieDao.instance();
        personDao = PersonDao.instance();
        watchListDao = WatchListDao.instance();
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
    public void addToWatchList(int userId, Movie movie) throws SQLException {
        PersonDTO director = personDao.upsert(movie.getDirector());
        MovieDTO movieDTO = movieDao.upsert(movie, director.getId());
        if (!watchListDao.watchListItemForUserExists(userId, movieDTO.getId()))
            userDao.addoWatchListItem(userId, movieDTO.getId());
        else throw new SQLException("The movie is already in your personal watchlist.");
    }

    /**
     * removes a movie from the personal watchlist of the user
     * @param userId identifier for the current user
     * @param movie the movie which is removed from the watchlist of the user
     * @return
     */
    public boolean removeFromWatchlist(int userId, Movie movie) throws SQLException {
        MovieDTO movieDTO = movieDao.searchByRtPath(movie.getRtPath());
        return userDao.removeWatchListItem(userId, movieDTO.getId());
    }

    public boolean setWatched(int userId, Movie movie) throws SQLException {
        MovieDTO movieDTO = movieDao.searchByRtPath(movie.getRtPath());
        return userDao.setWatchListItemWatched(userId, movieDTO.getId());
    }

    public boolean setRating(int userId, Movie movie, int rating) throws SQLException {
        MovieDTO movieDTO = movieDao.searchByRtPath(movie.getRtPath());
        return userDao.setWatchListItemRating(userId, movieDTO.getId(), rating);
    }
}
