package application;
	
import java.io.IOException;

import gui.Router;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import gui.login.Login;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		// initialize controller with stage
        Router router = Router.init(stage);
        
		Parent root = FXMLLoader.load(getClass().getResource("../gui/layout.fxml"));
		String css = getClass().getResource("../gui/style.css").toExternalForm(); 

		Font.loadFont(getClass().getResource("../gui/fonts/OpenSans-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("../gui/fonts/OpenSans-Light.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("../gui/fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
		
        Scene scene = new Scene(root, 1000, 800);
		scene.getStylesheets().add(css);
		
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("YourMovieWatchlist - Keep track of your movie desires!");
        stage.setScene(scene);
        stage.show();
        
        router.render(new Login());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
