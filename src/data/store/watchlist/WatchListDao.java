package data.store.watchlist;

import data.Movie;
import data.Person;
import data.WatchListItem;
import data.dto.WatchListDTO;
import data.store.common.BaseDao;
import exception.LoadWatchlistException;
import service.WatchListContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class WatchListDao extends BaseDao<WatchListDTO> {


    public WatchListDao() throws SQLException, ClassNotFoundException {
        super(WatchListDTO.class, "watchlist");
    }

    public void load(WatchListContainer container, int userId) throws LoadWatchlistException{
        try {
            ArrayList<WatchListDTO> results =  executeQuery(WatchListDTO.class, WatchListConstants.SELECT_WATCHLIST_FOR_USERID, Arrays.asList(userId));
            for(WatchListDTO result : results) {
                // Director/Person
                Person director = new Person(result.getDirectorName(), result.getDirectorRtPath());
                // Movie
                Movie movie = new Movie(result.getMovieRtPath(), result.getMoviePosterUrl(), result.getMovieTitle(), result.getMovieDescription(), director, result.getMovieImdbRating().byteValue(),
                        result.getMovieMcRating().byteValue(), result.getMovieRtRating().byteValue(), result.getMovieRtaRating().byteValue(), result.getMovieYear());

                boolean watched = result.getLastWatched() != null;
                // watchlistitem
                WatchListItem item = new WatchListItem(movie, result.getPesonalRating().byteValue(), watched);
                container.link(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoadWatchlistException("Could not load watchlist.");
        }
    }
}
