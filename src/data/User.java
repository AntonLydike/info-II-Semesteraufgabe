package data;

/*
 * A user Objects holds the watchlist, username and maybe some other information, idk
 */

import java.util.ArrayList;
import service.UserService;

public class User {

	private ArrayList<WatchListItem> watchlist;
	private int id;
	private String username;
	private UserService service = new UserService();
	
	public User(int id, String username) {
		this.username = username;
		this.id = id;
		watchlist = new ArrayList<WatchListItem>();
	}

	public void unlinkWatchListItem(WatchListItem wli) {
		watchlist.removeIf((itm) -> itm.equals(wli));
		service.removeFromWatchlist(id, wli.getMovie());
	}
	
	public void linkMovie(Movie m) {
		WatchListItem item = new WatchListItem(m);
		if (!watchlist.contains(item)) {
			watchlist.add(item);
			service.addToWatchList(id, m);
		}
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


