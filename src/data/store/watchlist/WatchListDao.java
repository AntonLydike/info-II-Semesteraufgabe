package data.store.watchlist;

import data.Movie;
import data.Person;
import data.WatchListItem;
import data.dto.WatchListDTO;
import data.store.common.BaseDao;
import exception.LoadWatchlistException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WatchListDao extends BaseDao<WatchListDTO> {

    private static WatchListDao unique = null;

    private WatchListDao() throws SQLException, ClassNotFoundException {
        super(WatchListDTO.class, "watchlist");
    }

    public static WatchListDao instance() throws SQLException, ClassNotFoundException{
        if(unique == null)
            unique = new WatchListDao();
        return unique;
    }

    public ArrayList<WatchListItem> getWatchListForUser(int userId) throws LoadWatchlistException{
        ArrayList<WatchListItem> watchListItems = new ArrayList<>();
        try {
            ArrayList<WatchListDTO> results =  executeQuery(WatchListDTO.class, WatchListConstants.SELECT_WATCHLIST_FOR_USERID, Arrays.asList(userId));
            for(WatchListDTO result : results) {
                // Director/Person
                Person director = new Person(result.getDirectorName(), "", result.getDirectorRtPath());
                // Movie
                Movie movie = new Movie(result.getMovieRtPath(), result.getMoviePosterUrl(), result.getMovieTitle(), result.getMovieDescription(), director, result.getMovieImdbRating().byteValue(),
                        result.getMovieMcRating().byteValue(), result.getMovieRtRating().byteValue(), result.getMovieRtaRating().byteValue(), result.getMovieYear());

                // watchlistitem
                WatchListItem item = new WatchListItem(movie, result.getPesonalRating().byteValue(),
                        (result.getWatched() != 0 || result.getWatched() == null) );
                watchListItems.add(item);
            }
            return watchListItems;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoadWatchlistException("Could not load watchlist.");
        }
    }

    public boolean watchListItemForUserExists(int userId, int movieId) throws SQLException{
        List<WatchListDTO> results = executeQuery(WatchListDTO.class, WatchListConstants.SEARCH_BY_USER_AND_MOVIE_ID, Arrays.asList(userId, movieId));
        return results.size() > 0;
    }

}
