package data;

import java.util.ArrayList;

/*
 * A Movie objects hold all the interesting information about that movie, various ratings, etc
 */
public class Movie implements Comparable<Movie>{
	private String rtPath; // Path on rotten tomatoes, unique ID
	private String posterURL;
	private String title;
	private String description;
	private ArrayList<Actor> actors;
	private Person director;
	private byte imdbRating; // IMDB Rating
	private byte mcRating;	 // metaCritic Rating
	private byte rtRating;	 // Rotten Tomatoes Rating
	private byte rtaRating;  // Rotten Tomatoes audience rating
	private int year;

	public Movie(String rtPath) {
		this.actors = new ArrayList<Actor>();
		this.rtPath = rtPath;
	}	

	public Movie(String rtPath, String posterURL, String title, String description,
			Person director, byte imdbRating, byte mcRating, byte rtRating, byte rtaRating, int year) {
		
		setRtPath(rtPath);
		setPosterURL(posterURL);
		setTitle(title);
		setDescription(description);
		this.actors = new ArrayList<Actor>();
		setDirector(director);
		setImdbRating(imdbRating);
		setMcRating(mcRating);
		setRtRating(rtRating);
		setRtaRating(rtaRating);
		setYear(year);
	}

	public String getRtPath() {
		return rtPath;
	}
	public void setRtPath(String rtPath) {
		this.rtPath = rtPath;
	}
	public String getPosterURL() {
		return posterURL;
	}
	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setYear(int y) {
		year = y;
	}
	public int getYear() {
		return year;
	}
	
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	public byte getImdbRating() {
		return imdbRating;
	}
	public void setImdbRating(byte imdbRating) {
		this.imdbRating = imdbRating;
	}
	public byte getMcRating() {
		return mcRating;
	}
	public void setMcRating(byte mcRating) {
		this.mcRating = mcRating;
	}
	public byte getRtRating() {
		return rtRating;
	}
	public void setRtRating(byte rtRating) {
		this.rtRating = rtRating;
	}
	public byte getRtaRating() {
		return rtaRating;
	}
	public void setRtaRating(byte rtaRating) {
		this.rtaRating = rtaRating;
	}
	public void linkActor(Person p, String role) {
		if (!actors.contains(new Actor(p, ""))) {
			actors.add(new Actor(p, role));	
		}
	}
	public void linkActor(Actor a) {
		if (!actors.contains(a)) {
			actors.add(a);	
		}
	}
	public void unlinkActor(Person p) {
		this.actors.remove(new Actor(p, ""));
	}
	public void unlinkActor(Actor a) {
		unlinkActor(a.person);
	}

	@Override
	public int compareTo(Movie o) {
		return o.getRtPath().compareTo(rtPath);
	}
	
	public boolean equals(Movie a) {
		return a.compareTo(this) == 0;
	}

	@Override
	public String toString() {
		return "Movie(" + rtPath + "): {"
				+ "\n  cover: " + posterURL
				+ "\n  title: " + title
				+ "\n  year: " + year
				+ "\n  description: " + description.substring(0, 20) + "..."
				+ "\n  director: " + director.getName()
				+ "\n  imdb: " + imdbRating
				+ "\n  mc: " + mcRating
				+ "\n  rt: " + rtRating
				+ "\n  rta: " + rtaRating 
				+ "\n}";
	}
	
}