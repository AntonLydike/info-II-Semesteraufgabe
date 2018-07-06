package data;

/*
 * A user Objects holds the watchlist, username and maybe some other information, idk
 */
import java.util.ArrayList;

public class User {

	private ArrayList<WatchListItem> watchlist;
	private String id;
	private String username;
	
	public User(String username) {
		this.username = username;
		watchlist = new ArrayList<WatchListItem>();
	}
	
	public void linkMovie(Movie m) {
		WatchListItem item = new WatchListItem(m);
		if (!watchlist.contains(item)) {
			watchlist.add(item);
		}
	}

	public void unlinkMovie(Movie m) {
		watchlist.remove(new WatchListItem(m));
	}
	
	public ArrayList<WatchListItem> getMovieList() {
		return watchlist;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}


