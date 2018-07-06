package gui.movieList;

import java.util.ArrayList;

import data.Movie;
import data.WatchListItem;
import gui.Renderable;
import gui.components.MovieCard;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class MovieCardList implements Renderable {

	private ArrayList<WatchListItem> list;
	
	public MovieCardList(ArrayList<WatchListItem> list) {
		this.list = list;

		// TODO should load list dynamically depending on user credentials
	}

	@Override
	public Node getView() {
		FlowPane fp = new FlowPane();
		fp.setVgap(16);
		fp.setHgap(16);
		fp.setPadding(new Insets(16, 16, 16, 16));
		for(WatchListItem m: list) {
			MovieCard mc = new MovieCard(m);
			fp.getChildren().add(mc.getView());
		}
		return fp;
	}

}
