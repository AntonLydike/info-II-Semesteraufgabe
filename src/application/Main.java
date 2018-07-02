package application;
	
import java.io.IOException;
import java.util.ArrayList;

import data.Movie;
import data.Person;
import gui.Router;
import gui.components.MovieCard;
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
		// initialize controller with stage
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
        
        ArrayList<Movie> list = new ArrayList<Movie>();
        
        Movie m = new Movie("/test");
        
        m.setDescription("The epic story of aut quos architecto omnis ea. Praesentium sed et quis id quos dolor. Rerum ut eius aliquam autem quia.\n" + 
        		"\n" + 
        		"Omnis ut eos possimus et eius. Repellendus aut numquam aut rerum expedita odio. Iusto quia rerum incidunt iure nostrum. Nemo autem dolores soluta vel omnis delectus.\n" + 
        		"\n" + 
        		"Autem atque dolores voluptatibus nam velit ipsa est. Asperiores occaecati cumque modi molestiae. Sunt est sed est dolorum. Voluptas esse repudiandae accusamus dolor.\n" + 
        		"\n" + 
        		"Omnis accusamus sint aliquam sed veniam. Neque magnam expedita molestiae atque libero molestiae. Porro ratione amet suscipit magni fugit a. Aut et impedit laborum voluptas architecto.\n" + 
        		"\n" + 
        		"Doloremque harum sed rerum fuga. Aliquid et ut dolore ab accusantium. Perferendis velit architecto mollitia excepturi.");
        
        m.setDirector(new Person("Gareth Edwards (V)", "https://resizing.flixster.com/uFnQG3CAZiE8Zt9XdudZlgxciFo=/50x50/v1.bjs3NTEwNjM7ajsxNzczODsxMjAwOzU0MDs3MjA", "/nopeasd", "He's a good guy"));
        
        m.setImdbRating((byte) 55);
        m.setMcRating((byte) 63);
        m.setPosterURL("https://resizing.flixster.com/rQBJqxX0c7-pIHr9a7YaBk0RzeI=/206x305/v1.bTsxMjIzNzIxOTtwOzE3NzM2OzEyMDA7NzIwOzEwNjU");
        m.setRtaRating((byte) 44);
        m.setRtRating((byte) 50);
        m.setYear(2008);
        m.setTitle("Rogue One: A Star Wars Story");
        
        list.add(m);
        m = new Movie("/test2");
        
        m.setDescription("The epic story of aut quos architecto omnis ea. Praesentium sed et quis id quos dolor. Rerum ut eius aliquam autem quia.\n" + 
        		"\n" + 
        		"Omnis ut eos possimus et eius. Repellendus aut numquam aut rerum expedita odio. Iusto quia rerum incidunt iure nostrum. Nemo autem dolores soluta vel omnis delectus.\n" + 
        		"\n" + 
        		"Autem atque dolores voluptatibus nam velit ipsa est. Asperiores occaecati cumque modi molestiae. Sunt est sed est dolorum. Voluptas esse repudiandae accusamus dolor.\n" + 
        		"\n" + 
        		"Omnis accusamus sint aliquam sed veniam. Neque magnam expedita molestiae atque libero molestiae. Porro ratione amet suscipit magni fugit a. Aut et impedit laborum voluptas architecto.\n" + 
        		"\n" + 
        		"Doloremque harum sed rerum fuga. Aliquid et ut dolore ab accusantium. Perferendis velit architecto mollitia excepturi.");
        
        m.setDirector(new Person("Taika Waititi", "https://resizing.flixster.com/uFnQG3CAZiE8Zt9XdudZlgxciFo=/50x50/v1.bjs3NTEwNjM7ajsxNzczODsxMjAwOzU0MDs3MjA", "/nope", "He's a good guy"));
        
        m.setImdbRating((byte) 88);
        m.setMcRating((byte) 76);
        m.setPosterURL("https://m.media-amazon.com/images/M/MV5BMjMyNDkzMzI1OF5BMl5BanBnXkFtZTgwODcxODg5MjI@._V1_.jpg");
        m.setRtaRating((byte) 96);
        m.setRtRating((byte) 99);
        m.setYear(2017);
        m.setTitle("Thor: Ragnarok");
        
        list.add(m);
        
        
        
        //router.render(new Login());
        
        router.render(new MovieCardList(list));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
