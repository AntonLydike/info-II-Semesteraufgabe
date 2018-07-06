package gui.movie;

import java.io.IOException;

import org.json.JSONObject;

import data.Movie;
import data.WatchListItem;
import exception.APIRequestException;
import gui.Renderable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import store.api.MovieInfoApi;

public class MovieView implements Renderable {

	private WatchListItem item;
	
	@Override
	public Node getView() {
		System.out.println("I WAS HERE");
		FXMLLoader loader = new FXMLLoader();
        try {
			loader.setLocation(getClass().getResource("movie.fxml"));
			Node node = loader.<Node>load();
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}

class MovieViewController {
	WatchListItem wli;
	
	public MovieViewController(WatchListItem wli) {
		this.wli = wli;
		
		(new Thread() {
			public void run() {
				MovieInfoApi api = new MovieInfoApi();
					
				
			}
		}).start();
	}
}
