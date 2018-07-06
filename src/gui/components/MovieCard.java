package gui.components;

import java.io.IOException;
import gui.Renderable;
import data.Movie;
import data.WatchListItem;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MovieCard implements Renderable {
	private WatchListItem item;
	
	public MovieCard(WatchListItem m) {
		item = m;
	}
	
	public WatchListItem getWatchListItem() {
		return item;
	}
	
	@Override
	public Node getView() {
		FXMLLoader loader = new FXMLLoader();
        try {
        	loader.setController(new MovieCardController(item));
			loader.setLocation(getClass().getResource("MovieCard.fxml"));
			Node node = loader.<Node>load();
			
	        return node;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}

class MovieCardController {
	private Movie movie;
	private WatchListItem item;
	
	@FXML
	public Label title;
	@FXML
	public Label year;
	@FXML
	public Label director;
	@FXML
	public HBox rating;
	@FXML
	public ImageView cover;
	@FXML public ImageView checkmark;
	@FXML public VBox card_root;
	
	
	public MovieCardController(WatchListItem m) {
		item = m;
		movie = m.getMovie();
	}
	
	@FXML
    public void initialize() {
		Movie m = movie;
		
		title.setText(m.getTitle() + " ");
		year.setText("(" + String.valueOf(m.getYear()) + ")");
		director.setText(m.getDirector().getName());

		if (item.isWatched()) {
			card_root.getStyleClass().add("movie-checked");
		}
		
		// do lot's of calculating to compile all the ratings together...
		byte ratings[] = {m.getImdbRating(), m.getRtaRating(), m.getRtRating(), m.getMcRating()};
		int total = 0, count = 0;
		for (byte b: ratings) {
			if (b != -1) {
				total += b;
				count++;
			}
		}
		int rating = total / count;

		String fullStar = "./gui/images/star.png";
		String halfStar = "./gui/images/star_half.png";
		String noStar = "./gui/images/star_empty.png";


		// Calculate the stars shown:
		System.out.println("Total score: " + rating);
		int imgIndex = 0;
		if (rating <= 10) {
			((ImageView) this.rating.getChildren().get(0)).setImage(new Image(noStar));
			imgIndex = 1;
		} else if (rating <= 18) {
			((ImageView) this.rating.getChildren().get(0)).setImage(new Image(halfStar));
			imgIndex = 1;
		} else if (rating >= 26) {
			if (rating >= 26) {
				((ImageView) this.rating.getChildren().get(0)).setImage(new Image(fullStar));
				rating -= 26;
				imgIndex = 1;	
			}
			while (rating >= 16 && imgIndex < 5) {
				((ImageView) this.rating.getChildren().get(imgIndex)).setImage(new Image(fullStar));
				rating -= 16;
				imgIndex++;
			}
		}
		if (imgIndex < 5) {
			if (rating >= 8) {
				((ImageView) this.rating.getChildren().get(imgIndex)).setImage(new Image(halfStar));
				rating -= 8;
				imgIndex++;
			}
			while (imgIndex < 5) {
				((ImageView) this.rating.getChildren().get(imgIndex)).setImage(new Image(noStar));
				imgIndex++;
			}
		}
		
		
		// load image from url in new thread, otherwise UI hangs *forever*
		(new Thread() {
			public void run() {
				Image im = new Image(m.getPosterURL());
				cover.setImage(im);
				// set smoothing to true, some covers are insanely huge...
				cover.setSmooth(true);
			}
		}).start();
	}
}