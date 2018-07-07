package gui.home;

import java.io.IOException;
import java.util.ArrayList;

import data.Movie;
import data.Person;
import data.User;
import data.WatchListItem;
import gui.Renderable;
import gui.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class HomeView implements Renderable {

	private User user;
	private ArrayList<WatchListItem> list;
	
	public HomeView() {
		// get current user from router
		this.user = Router.instance().getCurrentUser();
		// list is now accessed by user.getMovieList()
		list = user.getWatchlist();
	}

	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	//loader.setController(new LoginController());
        	HomeViewController hvc = new HomeViewController(user);
        	loader.setController(hvc);
			loader.setLocation(getClass().getResource("/gui/home/HomeView.fxml"));
			Node node = loader.<Node>load();
			hvc.displayMovieList(list);
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}
