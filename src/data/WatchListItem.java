package data;

import java.sql.SQLException;

import gui.Router;
import service.UserService;

public class WatchListItem implements Comparable<WatchListItem> {

	private Movie movie;
	private byte rating = -1; // rating from 0 to 100, -1 is "no-value"
	private boolean watched = false;
	private UserService service;
	
	public WatchListItem(Movie m) {
		movie = m;
		try {
			service = new UserService();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Couldn't load service! Errors ahead!");
			e.printStackTrace();
		}
	}
	
	public WatchListItem(Movie movie, byte rating, boolean watched) {
		try {
			service = new UserService();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Couldn't load service! Errors ahead!");
			e.printStackTrace();
		}
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

	public boolean setRating(byte rating) {
		this.rating = rating;
		try {
			service.setRating(Router.instance().getCurrentUser().getId(), movie, rating);	
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isWatched() {
		return watched;
	}

	public boolean setWatched(boolean watched) {
		this.watched = watched;
		try {
			service.setWatched(Router.instance().getCurrentUser().getId(), movie, watched);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(WatchListItem o) {
		return movie.compareTo(o.getMovie());
	}
	
	public boolean equals(WatchListItem a) {
		return a.compareTo(this) == 0;
	}
	
}