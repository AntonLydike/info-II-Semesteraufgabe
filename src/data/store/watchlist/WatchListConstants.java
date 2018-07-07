package data.store.watchlist;

public class WatchListConstants {

    public static final String SELECT_WATCHLIST_FOR_USERID = "SELECT" +
            " watchlist.id as 'watchlist.id', watchlist.user_id as user_id, watchlist.movie_id as movie_id, watchlist.personalRating as 'watchlist.personalRating', watchlist.createdAt as 'watchlist.createdAt'," +
            " watchlist.watched as 'watchlist.watched', movies.title as 'movies.title', movies.description as 'movies.description', movies.director as director_id, movies.year as 'movies.year', movies.rtPath as 'movies.rtPath'," +
            " movies.posterUrl as 'movies.posterUrl', movies.imdbRating as 'movies.imdbRating', movies.mcRating as 'movies.mcRating', movies.rtRating as 'movies.rtRating', movies.rtaRating as 'movies.rtaRating'," +
            " persons.name as 'directors.name', persons.rtPath as 'directors.rtPath'" +
            " FROM watchlist" +
            " JOIN movies on movies.id=watchlist.movie_id " +
            " JOIN persons on persons.id = movies.director " +
            " WHERE user_id = ? ";
}
