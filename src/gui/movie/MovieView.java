package gui.movie;

import java.io.IOException;
import data.Actor;
import data.WatchListItem;
import exception.APIRequestException;
import gui.LayoutController;
import gui.Renderable;
import gui.Router;
import gui.components.Loader;
import gui.home.HomeView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import store.api.MovieInfoApi;
import store.api.RtMovieDTO;

/**
 * A detailed View of a WatchListItem. Displays information about that movie, loads it's actors from IMDB, etc...
 * @author anton
 *
 */
public class MovieView implements Renderable {

	private WatchListItem item;
	
	public MovieView(WatchListItem wli) {
		item = wli;
	}
	
	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	loader.setController(new MovieViewController(item));
			loader.setLocation(getClass().getResource("/gui/movie/movie.fxml"));
			Node node = loader.<Node>load();
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}

class MovieViewController {
	private WatchListItem wli;
	
	private boolean loaded = false;
	
	@FXML FlowPane actors = null;
	@FXML ImageView cover;
	@FXML Label title;
	@FXML Label description;
	@FXML Label imdb;
	@FXML Label rt;
	@FXML Label mc;
	@FXML Label rta;
	@FXML ImageView checked;
	@FXML ImageView back;
	
	public MovieViewController(WatchListItem wli) {
		this.wli = wli;
		
		//MovieViewController self = this;
		
		(new Thread() {
			public void run() {
				MovieInfoApi api = new MovieInfoApi();
				try {
					RtMovieDTO dto = api.rtInfo(wli.getMovie().getRtPath());
					// wait for main thread to load view
					while (loaded == false) {
						try {
							sleep(100);
						} catch(Exception e) {}
					}
					// use Platoform.runLater to execute on main thread
					Platform.runLater(() -> {
                    	for(Actor a: dto.getActors()) {
        					actors.getChildren().add(MovieViewController.createActorNode(a));
        				}
                    	setActorLength();
					});
					
				} catch (APIRequestException e) {
					System.out.println(e);
					Platform.runLater(() -> {
						LayoutController.error("Couldn't load actor data from Rotten Tomatoes");
					});
				}
			}
		}).start();
	}
	
	/**
	 * Creates an Actor List-Item
	 * @param a
	 * @return A HBox containing image, name and role of actor
	 */
	private static HBox createActorNode(Actor a) {
		HBox root = new HBox();
		VBox vb = new VBox();
		Label role = new Label("as " + a.role);
		ImageView img = new ImageView(Loader.getImage());
		(new Thread() {
			public void run() {
				Image prelaoded = new Image(a.person.getImageURL());
				img.setImage(prelaoded);
			}
		}).start();
		
		img.setFitHeight(50);
		img.setFitWidth(50);
		
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setSpacing(0);
		vb.getChildren().add(new Label(a.person.getName()));
		vb.getChildren().add(role);
		
		role.getStyleClass().add("grey");
		
		root.setSpacing(4);
		root.getStyleClass().add("actor-container");
		root.setAlignment(Pos.CENTER_LEFT);
		root.setMinWidth(240.0);
		root.getChildren().add(img);
		root.getChildren().add(vb);
		
		return root;
	}
	
	// run when initialized
	@FXML public void initialize() {
		cover.setImage(new Image(wli.getMovie().getPosterURL()));
		title.setText(wli.getMovie().getTitle());
		description.setText(wli.getMovie().getDescription());
		imdb.setText("" + wli.getMovie().getImdbRating() + "%");
		rt.setText("" + wli.getMovie().getRtRating() + "%");
		rta.setText("" + wli.getMovie().getRtaRating() + "%");
		mc.setText("" + wli.getMovie().getMcRating() + "%");
		if (wli.isWatched()) {
			checked.setImage(new Image("/gui/images/check.png"));
		}
		checked.setOnMouseClicked((e) -> {
			wli.setWatched(!wli.isWatched());
			if (wli.isWatched()) {
				checked.setImage(new Image("/gui/images/check.png"));
			} else {
				checked.setImage(new Image("/gui/images/check.dark.png"));
			}
		});
		
		back.setOnMouseClicked((e) -> {
			Router.instance().render(new HomeView());
		});
		
		loaded = true;
	}
	
	// set all actor elements to the same width (the largest one), so that the list doesn't look funny
	private void setActorLength () {
		// calc largest width
		double len = 0;
		for (Node n: actors.getChildren()) {
			if (n instanceof HBox) {
				if (len < n.getBoundsInParent().getWidth()) {
					len = n.getBoundsInParent().getWidth();
				}
			}
		}
		// set to largest width
		for (Node n: actors.getChildren()) {
			if (n instanceof HBox) {
				((HBox) n).setPrefWidth(len);
			}
		}
		
	}
}
