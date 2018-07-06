package application;
	
import java.io.IOException;
import java.util.ArrayList;

import data.Movie;
import data.Person;
import data.User;
import gui.Router;
import gui.home.HomeView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import gui.login.Login;
import gui.movieList.MovieCardList;
public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		// initialize router with stage
        Router router = Router.init(stage);
        
		Parent root = FXMLLoader.load(getClass().getResource("../gui/layout.fxml"));
		String css = getClass().getResource("../gui/style.css").toExternalForm(); 

		Font.loadFont(getClass().getResource("../gui/fonts/OpenSans-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("../gui/fonts/OpenSans-Light.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("../gui/fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
		
        Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(css);
		
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("YourMovieWatchlist - Keep track of your movie desires!");
        stage.setScene(scene);
        stage.show();
        
        
        
        // router.render(new Login());
        
        //router.render(new MovieCardList(list));
        
        //router.render(new HomeView(new User("tony")));
        
        router.render(new gui.movie.MovieView());

	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
