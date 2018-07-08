package gui.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import data.User;
import data.WatchListItem;
import gui.Renderable;
import gui.Router;
import gui.addMovie.AddMovie;
import gui.movieList.MovieCardList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Controller for HomeView, implements a simple search function for the watchlist, all exposed methods are only meant for FXML
 * @author anton
 *
 */
public class HomeViewController {

	private User user;
	private ArrayList<WatchListItem> list;
	private boolean searchDisplayed = false;
	private Node fullList = null;
	
	@FXML BorderPane main;
	
	@FXML TextField search; 
	
	@FXML ScrollPane scrollContent;
	
	public HomeViewController(User user) {
		this.user = user;
		list = user.getWatchlist();
	}
	
	private void displayMovieList(ArrayList<WatchListItem> list) {
		this.list = list;
		fullList = (new MovieCardList(list)).getView();
		displayNode(fullList);
	}
	
	private void displayNode(Node n) {
		scrollContent.contentProperty().set(n);
	}
	
	private void displayNode(Renderable n) {
		displayNode(n.getView());
	}

	@FXML void initialize() {
		search.setOnKeyPressed((e) -> {
			String query = search.getText().trim().toLowerCase();
			if (query.length() < 3) {
				if (searchDisplayed) {
					displayNode(fullList);
					searchDisplayed = false;
				}
				return;
			}
			searchDisplayed = true;
			
			String parts[] = query.split("\\s");
			
			ArrayList<WatchListItem> filtered = list.stream().filter((wli) -> {
				String source = (wli.getMovie().getTitle() + " " + wli.getMovie().getYear() + " " + wli.getMovie().getDirector().getName()).toLowerCase();
				return Arrays.stream(parts).allMatch((part) -> source.contains(part));
			}).collect(Collectors.toCollection(ArrayList::new));
			
			displayNode(new MovieCardList(filtered));
		});
		
		displayMovieList(user.getWatchlist());
	}
	
	@FXML void goToAddView(MouseEvent e) {
		Router.instance().render(new AddMovie(user));
	}
}
