package data.dto;


import data.Person;
import data.common.Entity;

import java.util.ArrayList;

public class MovieDTO implements Entity {

    private Integer id;
    private String title;
    private String description;
    private Integer director;
    private String rtPath; // Path on rotten tomatoes, unique ID
    private String posterURL;

    private byte imdbRating; // IMDB Rating
    private byte mcRating;	 // metaCritic Rating
    private byte rtRating;	 // Rotten Tomatoes Rating
    private byte rtaRating;  // Rotten Tomatoes audience rating
    private int year;
}
