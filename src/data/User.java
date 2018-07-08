package data;

import java.sql.SQLException;

/*
 * A user Objects holds the watchlist, username and maybe some other information, idk
 */

import java.util.ArrayList;

import exception.LoadWatchlistException;
import gui.LayoutController;
import service.UserService;
import service.WatchListService;

/**
 * The User object represents a login session. It holds the watchlist, holds connection to persistence, etc...
 * @author anton
 *
 */
public class User {

	private ArrayList<WatchListItem> watchlist;
	private int id;
	private String username;
	private UserService service;
	
	public User(int id, String username) {
		this.username = username;
		this.id = id;
		// create user service
		try {
			service = new UserService();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Couldn't load service! Errors ahead!");
			e.printStackTrace();
		}
		// get watchlist from the server
		try {
			WatchListService usv = new WatchListService();
			watchlist = usv.getWatchListForUser(getId());
			System.out.println("Watchlist len:" + getWatchlist().size());
		} catch (LoadWatchlistException | ClassNotFoundException | SQLException e) {
			System.err.println("Could't get watchlist!");
			e.printStackTrace();
			LayoutController.error("Couldn't fetch your watchlist: " + e.getMessage());
			watchlist = new ArrayList<WatchListItem>();
		}	
	}

	/**
	 * Removes Movie from the watchlist and updates persistence
	 * @param wli The watchListItem to remove
	 * @return if the movie could be removed
	 */
	public boolean unlinkWatchListItem(WatchListItem wli) {
		watchlist.removeIf((itm) -> itm.equals(wli));
		try {
			service.removeFromWatchlist(id, wli.getMovie());
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Removes Movie from the watchlist and updates persistence
	 * @param m The movie to be deleted
	 * @return if the movie could be removed
	 */
	public boolean linkMovie(Movie m) {
		WatchListItem item = new WatchListItem(m);
		if (!watchlist.contains(item)) {
			watchlist.add(0, item);
			try {
				service.addToWatchList(id, m);
			} catch (SQLException e) {
				unlinkWatchListItem(new WatchListItem(m));
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<WatchListItem> getWatchlist() {
		return watchlist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}


