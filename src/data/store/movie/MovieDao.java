package data.store.movie;

import data.Movie;
import data.dto.MovieDTO;
import data.store.common.BaseDao;
import data.store.user.UserDao;
import exception.IdNotUniqueException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MovieDao extends BaseDao<MovieDTO> {
    private static MovieDao unique = null;


    private MovieDao() throws SQLException, ClassNotFoundException {
        super(MovieDTO.class, "movies");
    }

    public static MovieDao instance() throws SQLException, ClassNotFoundException{
        if(unique == null)
            unique = new MovieDao();
        return unique;
    }

    public MovieDTO upsert(Movie movie, int directorId) throws SQLException{
        executeSQL(MovieConstants.UPSERT_MOVIE, Arrays.asList(
                    movie.getTitle(),
                    movie.getDescription(),
                    directorId,
                    movie.getYear(),
                    movie.getRtPath(),
                    movie.getPosterURL(),
                    movie.getImdbRating(),
                    movie.getMcRating(),
                    movie.getRtRating(),
                    movie.getRtaRating()
            ));
        return searchByRtPath(movie.getRtPath());
    }

    public MovieDTO searchByRtPath(String rtPath) throws SQLException{
        List<MovieDTO> results = executeQuery(MovieDTO.class, MovieConstants.SEARCH_MOVIE_RT_PATH, Arrays.asList(rtPath));
        if (results.size() == 1) {
            return results.get(0);
        } else if (results.size() == 0) {
            return null;
        } else {
            throw new IdNotUniqueException();
        }

    }
}
