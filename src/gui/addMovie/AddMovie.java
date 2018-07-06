package gui.addMovie;

import java.io.IOException;

import data.User;
import exception.APIRequestException;
import gui.LayoutController;
import gui.Renderable;
import gui.Router;
import gui.components.Loader;
import gui.home.HomeView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import store.api.MovieInfoApi;
import store.api.RtQueryDTO;
import store.api.RtQueryMovieItem;

public class AddMovie implements Renderable {

	private User user;
	
	public AddMovie(User u) {
		user = u;
	}

	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	loader.setController(new AddMovieController(user));
			loader.setLocation(getClass().getResource("/gui/addMovie/addMovie.fxml"));
			Node node = loader.<Node>load();
			
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}


class AddMovieController {
	private User user;
	private HBox loader;
	
	@FXML VBox searchResults;
	@FXML TextField searchBar;
	@FXML ImageView backbtn;
	@FXML ImageView searchbtn;
	
	public AddMovieController (User u) {
		user = u;
		loader = new HBox();
		ImageView img = new ImageView(Loader.getImage());
		img.setFitHeight(48);
		img.setPreserveRatio(true);
		loader.getChildren().add(img);
		loader.setAlignment(Pos.CENTER);
	}
	
	@FXML public void initialize() {
		backbtn.setOnMouseClicked((e) -> {
			Router.instance().render(new HomeView(user));
		});
		searchbtn.setOnMouseClicked((e) -> {
			search(null);
		});
	}
	
	private void loader(boolean display) {
		if (display) {
			searchResults.getChildren().clear();
			searchResults.getChildren().add(loader);
			loader.setVisible(true);	
		} else {
			searchResults.getChildren().remove(loader);
		}
	}
	
	@FXML void search(ActionEvent e) {
		if (searchBar.getText().trim().length() == 0) return;
		loader(true);
		(new Thread() {
			public void run() {
				MovieInfoApi api = new MovieInfoApi();
				try {
					RtQueryDTO result = api.rtQuery(searchBar.getText());
					Platform.runLater(() -> {
						displaySearchResults(result);
					});
				} catch(APIRequestException e) {
					System.out.println(e);
					e.printStackTrace();
					Platform.runLater(() -> {
						LayoutController.error(e.getMessage());
					});
				}
			}
		}).start();
	}
	
	private void displaySearchResults(RtQueryDTO results) {
		loader(false);
		
		for(RtQueryMovieItem item: results.getMovies()) {
			HBox root = new HBox();
			VBox vb = new VBox();
			HBox hb = new HBox();
			Label year = new Label();
			Label actors = new Label();
			String actorText = "With ";
			ImageView img = new ImageView(Loader.getImage());

			year.getStyleClass().add("grey");
			actors.getStyleClass().add("grey");
			year.setText(" (" + item.getYear() + ")");
			img.setFitHeight(80);
			img.setFitWidth(54);
			img.setPreserveRatio(true);
			
			actorText += String.join(", ", item.getActors());
			actors.setText(actorText);
			
			// laod image in another thread
			(new Thread() {
				public void run() {
					Image preloaded = new Image(item.getImage());
					img.setImage(preloaded);
					img.setFitWidth(80);
				}
			}).start();
			
			root.setAlignment(Pos.CENTER_LEFT);
			root.setSpacing(8);
			
			hb.getChildren().add(new Label(item.getName()));
			hb.getChildren().add(year);
			vb.getChildren().add(hb);

			if (item.getActors().length != 0) {
				vb.getChildren().add(actors);
			}
			
			root.getChildren().add(img);
			root.getChildren().add(vb);
			searchResults.getChildren().add(root);
		}
	}
}