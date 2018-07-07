package data;

/*
 * A user Objects holds the watchlist, username and maybe some other information, idk
 */

import java.util.ArrayList;

public class User {

	private ArrayList<WatchListItem> watchlist;
	private int id;
	private String username;
	
	public User(int id, String username) {
		this.username = username;
		this.id = id;
		watchlist = new ArrayList<WatchListItem>();
	}

	public void unlinkWatchListItem(WatchListItem wli) {
		watchlist.removeIf((itm) -> itm.equals(wli));
		// TODO: Connect to service
	}
	
	public void linkMovie(Movie m) {
		WatchListItem item = new WatchListItem(m);
		if (!watchlist.contains(item)) {
			watchlist.add(item);
		}
		// TODO: Connect to service
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


