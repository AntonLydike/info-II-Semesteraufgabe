package gui.login;

import java.io.IOException;
import gui.Renderable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

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
	}

}

class LoginController {
	@FXML
	private void btnClick(ActionEvent e) {
		System.out.println("Logging in...");
	}
	
}
