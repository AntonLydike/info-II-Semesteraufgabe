package gui.components;

import java.io.IOException;
import gui.Renderable;
import data.Movie;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MovieCard implements Renderable {
	private Movie movie;
	
	public MovieCard(Movie m) {
		movie = m;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	loader.setController(new MovieCardController(movie));
			loader.setLocation(getClass().getResource("MovieCard.fxml"));
			Node node = loader.<Node>load();
			
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public void beforeUnload() {
	}
}

class MovieCardController {
	private Movie movie;
	
	@FXML
	public Label title;
	@FXML
	public Label year;
	@FXML
	public Label director;
	@FXML
	public HBox rating;
	@FXML
	public ImageView cover;
	
	public MovieCardController(Movie m) {
		movie = m;
	}
	
	@FXML
    public void initialize() {
		Movie m = movie;
		
		title.setText(m.getTitle());
		year.setText(String.valueOf(m.getYear()));
		director.setText(m.getDirector().getName());
		
		Image im = new Image(m.getPosterURL());
		
		cover.setImage(im);
	}
}