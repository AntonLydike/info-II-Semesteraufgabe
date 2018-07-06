package gui.home;

import java.util.ArrayList;

import data.Movie;
import data.User;
import data.WatchListItem;
import gui.Router;
import gui.addMovie.AddMovie;
import gui.movieList.MovieCardList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class HomeViewController {

	User user;
	
	@FXML
	public BorderPane main;
	
	public HomeViewController(User user) {
		this.user = user;
	}
	
	public void displayMovieList(ArrayList<WatchListItem> list) {
		main.setCenter((new MovieCardList(list)).getView());
	}

	
	@FXML void goToAddView(MouseEvent e) {
		Router.instance().render(new AddMovie(user));
	}
}
