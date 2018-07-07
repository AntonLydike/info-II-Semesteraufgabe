package data.store.user;

public class UserConstants {

    public final static String REGISTER_QUERY = "INSERT INTO users (`username`, `password`) VALUES (?, ?);";
    public final static String ADD_WATCHLIST = "INSERT IGNORE INTO watchlist (`user_id`,`movie_id`, `personalRating`,`watched`) VALUES (?, ?, ?, ?)";
    public final static String REMOVE_WATCHLIST_ITEM_BY_USER_MOVIE = "DELETE FROM watchlist WHERE user_id = ? AND movie_id = ? ";
    public final static String WATCHLIST_ITEM_SET_WATCHED = "UPDATE watchlist SET `watched` = true WHERE user_id = ? AND movie_id = ?";
    public final static String WATCHLIST_ITEM_SET_RATING = "UPDATE watchlist SET `personalRating`= ?  WHERE `user_id`= ? AND movie_id = ?";
}
