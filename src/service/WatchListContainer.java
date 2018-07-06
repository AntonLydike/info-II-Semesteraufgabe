package service;

import data.WatchListItem;
import data.dto.WatchListDTO;
import data.store.watchlist.WatchListDao;
import exception.LoadWatchlistException;
import gui.LayoutController;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class WatchListContainer extends Observable implements Iterable<WatchListItem> {

    private List<WatchListItem> watchListItems;
    private int userId;
    private WatchListDao watchListDao;
    LayoutController layout = LayoutController.instance();


    public WatchListContainer() {
        watchListItems = new ArrayList<WatchListItem>();
        try {
            watchListDao = new WatchListDao();
        } catch (SQLException e) {
            layout.error("Could not connect to Database! For more details see the log file or contact the administrator.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            layout.error("Unexpected error occured. Please see the logfiles for more details");
            e.printStackTrace();
        }
    }


    /**
     * initialize with userId
     * and load all watchlistItems
     * @param userId
     */
    public void init(int userId) {
        this.userId = userId;
        load();
    }

    @Override
    public Iterator<WatchListItem> iterator() {
        return this.iterator();
    }

    @Override
    public void forEach(Consumer<? super WatchListItem> action) {
    }

    @Override
    public Spliterator<WatchListItem> spliterator() {
        return this.spliterator();
    }

    public void load() {
        try {
            watchListDao.load(this, userId);
        } catch (LoadWatchlistException e) {
            layout.error(e.getMessage());
        }
    }

    public void link(WatchListItem item) {
        if(!this.watchListItems.contains(item)) {
            this.watchListItems.add(item);
            setChanged();
            notifyObservers();
        }
    }

    public void unlink(WatchListItem item) {
        if(!this.watchListItems.contains(item))
            return;

        this.watchListItems.remove(item);
        setChanged();
        notifyObservers();
    }
}
