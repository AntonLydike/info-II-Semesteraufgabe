package data.dto;


import data.common.Col;
import data.common.Entity;

public class MovieDTO implements Entity {

    @Col(name="id")
    private Integer id;
    @Col(name="title")
    private String title;
    @Col(name="description")
    private String description;
    @Col(name="director")
    private Integer director;
    @Col(name="rtPath")
    private String rtPath; // Path on rotten tomatoes, unique ID
    @Col(name="posterUrl")
    private String posterURL;

    @Col(name="imdbRating")
    private Byte imdbRating; // IMDB Rating
    @Col(name="mcRating")
    private Byte mcRating;	 // metaCritic Rating
    @Col(name="rtRating")
    private Byte rtRating;	 // Rotten Tomatoes Rating
    @Col(name="rtaRating")
    private Byte rtaRating;  // Rotten Tomatoes audience rating
    @Col(name="year")
    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDirector() {
        return director;
    }

    public void setDirector(Integer director) {
        this.director = director;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
