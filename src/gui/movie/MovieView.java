package gui.movie;

import java.io.IOException;
import java.util.Optional;
import data.Actor;
import data.WatchListItem;
import exception.APIRequestException;
import gui.CachedImage;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	@FXML ImageView delete;
	@FXML Label year;
	@FXML Button rateButton;
	
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
		Label role = new Label("as " + a.getRole());
		ImageView img = new ImageView(Loader.getImage());
		
		(new Thread(() -> {
			Image prelaoded = CachedImage.get(a.getPerson().getImageURL());
			img.setImage(prelaoded);
		})).start();
		
		img.setFitHeight(50);
		img.setFitWidth(50);
		
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setSpacing(0);
		vb.getChildren().add(new Label(a.getPerson().getName()));
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

		imdb.setText(ratingString(wli.getMovie().getImdbRating()));
		rt.setText(ratingString(wli.getMovie().getRtRating()));
		rta.setText(ratingString(wli.getMovie().getRtaRating()));
		mc.setText(ratingString(wli.getMovie().getMcRating()));
		
		year.setText("(" + wli.getMovie().getYear() + ")");
				
		if (wli.isWatched()) {
			checked.setImage(new Image("/gui/images/check.png"));
		}
		checked.setOnMouseClicked((e) -> {
			wli.setWatched(!wli.isWatched());
			if (wli.isWatched()) {
				checked.setImage(CachedImage.get("/gui/images/check.png"));
			} else {
				checked.setImage(CachedImage.get("/gui/images/check.dark.png"));
			}
		});
		
		
		delete.setOnMouseClicked((e) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Remove " + wli.getMovie().getTitle() + "?", ButtonType.YES, ButtonType.NO);
			if (alert.showAndWait().orElse(null) == ButtonType.YES) {
				Router.instance().getCurrentUser().unlinkWatchListItem(wli);
				Router.instance().render(new HomeView());
			}
		});
		
		back.setOnMouseClicked((e) -> {
			Router.instance().render(new HomeView());
		});
		
		rateButton.setText("RATE (" + ratingString(wli.getRating()) + ")");
		
		rateButton.setOnAction((e) -> {
			TextInputDialog tid = new TextInputDialog("" + wli.getRating());
			tid.setContentText("Your rating for " + wli.getMovie().getTitle() + ":");
			Optional<String> in = tid.showAndWait();
			try {
				byte rating = (byte) Integer.parseInt(in.orElse("-1"));
				if (!wli.setRating(rating)) {
					LayoutController.error("Rating must be between -1 (not set) and 100!");
				}
				rateButton.setText("RATE (" + ratingString(wli.getRating()) + ")");
			} catch (NumberFormatException err) {
				LayoutController.error("This isn't a number!");
			}
			
		});
		
		loaded = true;
	}
	
	/**
	 * Converts byte rating to a string representation
	 * @param rating must be in [-1,100]
	 * @return "num%" if in [0,100] otherwise "??"
	 */
	private String ratingString(byte rating) {
		if (rating == -1) {
			return "??";
		} else {
			return "" + rating + "%";
		}
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
