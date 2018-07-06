package data;

public class WatchListItem implements Comparable<WatchListItem> {

	private Movie movie;
	private byte rating = 0; // rating from 0 to 100
	private boolean watched = false;
	
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
	}

	public boolean isWatched() {
		return watched;
	}

	public void setWatched(boolean watched) {
		this.watched = watched;
	}

	@Override
	public int compareTo(WatchListItem o) {
		return movie.compareTo(o.getMovie());
	}
	
	public boolean equals(WatchListItem a) {
		return a.compareTo(this) == 0;
	}
	
}