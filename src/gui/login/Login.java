package gui.login;

import java.io.IOException;
import java.sql.SQLException;
import data.User;
import exception.LoginFailedException;
import exception.RegisterFailedException;
import gui.LayoutController;
import gui.Renderable;
import gui.Router;
import gui.home.HomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	
	UserService userService;
	
	public LoginController () {
		try {
			userService = new UserService();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Couldn't create User Service! Errors ahead!");
			e.printStackTrace();
		}
	}

	@FXML
	private void btnClick(ActionEvent e) {
		// register first (if necessary)
		if (!isLoginView) {
			try {
				if (!userService.register(username.getText().trim(), password.getText())) {
					LayoutController.error("This user is already registered!");
					return;
				}
			} catch (RegisterFailedException e1) {
				LayoutController.error(e1.getMessage());
				return;
			}
		}
		
		// then login
		try {
			User user = userService.login(username.getText().trim(), password.getText());
			Router.instance().setCurrentUser(user);
			Router.instance().render(new HomeView());
		} catch (LoginFailedException e1) {
			LayoutController.error(e1.getMessage());
		}
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
