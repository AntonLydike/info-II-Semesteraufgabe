package gui.login;

import java.io.IOException;
import java.util.ArrayList;

import exception.LoginFailedException;
import gui.LayoutController;
import gui.Renderable;
import gui.Router;
import gui.movieList.MovieCardList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import service.UserService;

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
}

class LoginController {
	@FXML TextField username;
	@FXML PasswordField password;
	@FXML Label error;
	@FXML Label title;
	@FXML Button login;
	@FXML Button register;

	// since login and register is handled by the same view, keep track what is what
	private boolean isLoginView = true;
	
	Router router = Router.instance();
	UserService userService = new UserService();

	@FXML
	private void btnClick(ActionEvent e) {
		error.setText("");
		System.out.println("Logging in...");
		System.out.println("username: " + username.getText());
		System.out.println("password: " + password.getText());
		// TODO check if login is valid
		// TODO error handling?
		try {
			userService.login(username.getText(), password.getText());
			router.render(new MovieCardList(new ArrayList<>()));
		} catch (LoginFailedException e1) {
			System.out.println("SHOW ERROR");
/*			error.setText(e1.getMessage());
			error.setTextFill(Paint.valueOf("RED"));*/
			LayoutController.error(e1.getMessage());
		}

		// for testing
	}
	
	@FXML
	private void registerSwitch(ActionEvent e) {
		if (isLoginView) {
			title.setText("Register");
			login.setText("REGISTER");
			register.setText("LOGIN");
		} else {
			title.setText("Login");
			register.setText("REGISTER");
			login.setText("LOGIN");
			
		}
		isLoginView = !isLoginView;
	}
	
}
