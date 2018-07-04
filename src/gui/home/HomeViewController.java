package gui.home;

import java.util.ArrayList;

import data.Movie;
import gui.movieList.MovieCardList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class HomeViewController {

	@FXML
	public BorderPane main;
	
	public HomeViewController() {
		
	}
	
	public void displayMovieList(ArrayList<Movie> list) {
		main.setCenter((new MovieCardList(list)).getView());
	}

}
