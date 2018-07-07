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


    public WatchListService() {
        try {
            watchListDao = WatchListDao.instance();
        } catch (SQLException e) {
            layout.error("Could not connect to Database! For more details see the log file or contact the administrator.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            layout.error("Unexpected error occured. Please see the logfiles for more details");
            e.printStackTrace();
        }
    }

    public List<WatchListItem> getWatchListForUser(int userId) {
        try {
            return watchListDao.getWatchListForUser(userId);
        } catch (LoadWatchlistException e) {
            e.printStackTrace();
            layout.error("Could not load the watchlist.");
        }
        return new ArrayList<>();
    }

}
