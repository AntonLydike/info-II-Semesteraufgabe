package data.store.movie;

public class MovieConstants {

    public static final String SEARCH_MOVIE_RT_PATH = "SELECT * from movies where rtPath = ?";
    public static final String UPSERT_MOVIE = "INSERT IGNORE INTO movies (title, description, director, year, rtPath, posterUrl, imdbRating, mcRating, rtRating, rtaRating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
}
