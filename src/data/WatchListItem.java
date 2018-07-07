package data;

import gui.Router;
import service.UserService;

public class WatchListItem implements Comparable<WatchListItem> {

	private Movie movie;
	private byte rating = -1; // rating from 0 to 100, -1 is "no-value"
	private boolean watched = false;
	private UserService service = new UserService();
	
	public WatchListItem(Movie m) {
		movie = m;
	}
	
	public WatchListItem(Movie movie, byte rating, boolean watched) {
		this.movie = movie;
		this.rating = rating;
		this.watched = watched;
	}

	public Movie getMovie() {
		return movie;
	}

	public byte getRating() {
		return rating;
	}

	public void setRating(byte rating) {
		this.rating = rating;
		service.setRating(Router.instance().getCurrentUser().getId(), movie, rating);
	}

	public boolean isWatched() {
		return watched;
	}

	public void setWatched(boolean watched) {
		this.watched = watched;
		service.setWatched(Router.instance().getCurrentUser().getId(), movie);
	}

	@Override
	public int compareTo(WatchListItem o) {
		return movie.compareTo(o.getMovie());
	}
	
	public boolean equals(WatchListItem a) {
		return a.compareTo(this) == 0;
	}
	
}