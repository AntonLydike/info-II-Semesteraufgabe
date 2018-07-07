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
    LayoutController layout = LayoutController.instance();

    /**
     *
     * @throws SQLException thrown if the connection to the database is not possible
     * @throws ClassNotFoundException thrown if an unexpected error occurs during initialization
     */
    public WatchListService() throws SQLException, ClassNotFoundException{
        watchListDao = WatchListDao.instance();
    }

    public List<WatchListItem> getWatchListForUser(int userId) throws LoadWatchlistException{
        return watchListDao.getWatchListForUser(userId);
    }

}
