package service;

import data.Movie;
import data.WatchListItem;
import data.store.watchlist.WatchListDao;
import exception.LoadWatchlistException;
import gui.LayoutController;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class WatchListService {

    private WatchListDao watchListDao;

    /**
     *
     * @throws SQLException thrown if the connection to the database is not possible
     * @throws ClassNotFoundException thrown if an unexpected error occurs during initialization
     */
    public WatchListService() throws SQLException, ClassNotFoundException{
        watchListDao = WatchListDao.instance();
    }

    /**
     * get the personal watchlist of the current user
     * @param userId identifier of the current user
     * @return List of WatchListItems of the current user
     * @throws LoadWatchlistException
     */
    public ArrayList<WatchListItem> getWatchListForUser(int userId) throws LoadWatchlistException{
        return watchListDao.getWatchListForUser(userId);
    }

}
