package gui.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import data.Movie;
import data.User;
import data.WatchListItem;
import exception.LoadWatchlistException;
import gui.LayoutController;
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
import javafx.scene.layout.FlowPane;
import service.UserService;
import service.WatchListService;

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
		
		if (user.getWatchlist().isEmpty()) {
			System.out.println("Getting watchlist");
			try {
				WatchListService usv = new WatchListService();
				user.setWatchList(usv.getWatchListForUser(user.getId()));
				System.out.println("Watchlist len:" + user.getWatchlist().size());
			} catch (LoadWatchlistException | ClassNotFoundException | SQLException e) {
				System.err.println("Could't get watchlist!");
				e.printStackTrace();
				LayoutController.error("Couldn't fetch your watchlist: " + e.getMessage());
			}	
		}
	}
	
	public void displayMovieList(ArrayList<WatchListItem> list) {
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

	@FXML
	public void initialize() {
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
				String source = (wli.getMovie().getTitle() + " " + wli.getMovie().getYear()).toLowerCase();
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
