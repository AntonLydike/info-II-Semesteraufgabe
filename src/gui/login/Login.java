package gui.login;

import java.io.IOException;
import java.util.ArrayList;

import data.Movie;
import data.Person;
import gui.Renderable;
import gui.Router;
import gui.movieList.MovieCardList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login implements Renderable {
	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	loader.setController(new LoginController());
			loader.setLocation(getClass().getResource("login.fxml"));
			Node node = loader.<Node>load();
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public void beforeUnload() {
		System.out.println("before unload");
	}

}

class LoginController {
	@FXML TextField username;
	@FXML PasswordField password;
	Router router = Router.instance();

	@FXML
	private void btnClick(ActionEvent e) {
		System.out.println("Logging in...");
		System.out.println("username: " + username.getText());
		System.out.println("password: " + password.getText());
		// TODO check if login is valid
		// TODO error handling?

		// for testing
		router.render(new MovieCardList(new ArrayList<>()));
	}
	
}
