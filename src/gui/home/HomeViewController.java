package gui.home;

import java.util.ArrayList;

import data.Movie;
import data.WatchListItem;
import gui.movieList.MovieCardList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class HomeViewController {

	@FXML
	public BorderPane main;
	
	public HomeViewController() {
		
	}
	
	public void displayMovieList(ArrayList<WatchListItem> list) {
		main.setCenter((new MovieCardList(list)).getView());
	}

}
