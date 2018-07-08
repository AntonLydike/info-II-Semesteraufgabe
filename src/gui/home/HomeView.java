package gui.home;

import java.io.IOException;
import data.User;
import gui.Renderable;
import gui.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Home View displaying the users watchlist
 * @author anton
 *
 */
public class HomeView implements Renderable {

	private User user;
	
	public HomeView() {
		// get current user from router
		this.user = Router.instance().getCurrentUser();
		// list is now accessed by user.getMovieList()
	}

	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	//loader.setController(new LoginController());
        	loader.setController(new HomeViewController(user));
			loader.setLocation(getClass().getResource("/gui/home/HomeView.fxml"));
			Node node = loader.<Node>load();
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}
