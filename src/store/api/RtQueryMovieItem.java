package store.api;

public class RtQueryMovieItem {
	private String name;
	private int year;
	private String url;
	private String image;
	
	private String[] actors;
	
	public RtQueryMovieItem(String name, int year, String url, String image, String actors[]) {
		this.name = name;
		this.year = year;
		this.url = url;
		this.image = image;
		this.actors = actors;
	}
	public String getName() {
		return name;
	}
	public int getYear() {
		return year;
	}
	public String getUrl() {
		return url;
	}
	public String getImage() {
		return image;
	}
	public String[] getActors() {
		return actors;
	}
}