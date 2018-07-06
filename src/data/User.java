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
	
	public void linkMovie(Movie m) {
		WatchListItem item = new WatchListItem(m);
		if (!watchlist.contains(item)) {
			watchlist.add(item);
		}
	}

	public ArrayList<WatchListItem> getWatchlist() {
		return watchlist;
	}

	public void setWatchlist(ArrayList<WatchListItem> watchlist) {
		this.watchlist = watchlist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void unlinkMovie(Movie m) {
		watchlist.remove(new WatchListItem(m));
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}


