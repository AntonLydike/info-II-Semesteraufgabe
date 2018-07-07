package data;

import java.sql.SQLException;

/*
 * A user Objects holds the watchlist, username and maybe some other information, idk
 */

import java.util.ArrayList;
import service.UserService;

public class User {

	private ArrayList<WatchListItem> watchlist;
	private int id;
	private String username;
	private UserService service;
	
	public User(int id, String username) {
		this.username = username;
		this.id = id;
		watchlist = new ArrayList<WatchListItem>();
		try {
			service = new UserService();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Couldn't load service! Errors ahead!");
			e.printStackTrace();
		}
	}

	public boolean unlinkWatchListItem(WatchListItem wli) {
		watchlist.removeIf((itm) -> itm.equals(wli));
		try {
			service.removeFromWatchlist(id, wli.getMovie());
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
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
	
	public boolean setWatchList(ArrayList<WatchListItem> list) {
		if (watchlist.isEmpty()) {
			watchlist = list;
			return true;
		}
		return false;
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


