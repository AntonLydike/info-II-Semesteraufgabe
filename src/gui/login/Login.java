package gui.login;

import java.io.IOException;
import java.net.URL;
import gui.Renderable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class Login implements Renderable {
	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	loader.setController(new LoginController());
			loader.setLocation(getClass().getResource("login.fxml"));
			VBox node = loader.<VBox>load();
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public void beforeUnload() {
		// TODO Auto-generated method stub
		
	}

}

class LoginController {
	
}
