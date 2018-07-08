package gui.addMovie;

import java.io.IOException;

import data.Movie;
import data.User;
import data.WatchListItem;
import exception.APIRequestException;
import gui.LayoutController;
import gui.Renderable;
import gui.Router;
import gui.components.Loader;
import gui.home.HomeView;
import gui.movie.MovieView;
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
import store.api.ImdbMovieDTO;
import store.api.MovieInfoApi;
import store.api.RtMovieDTO;
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
			Router.instance().render(new HomeView());
		});
		searchbtn.setOnMouseClicked((e) -> {
			search(null);
		});
		
		searchBar.requestFocus();
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
			
			root.setOnMouseClicked((e) -> {
				loader(true);
				gatherMovieInformation(item);
			});
			
			searchResults.getChildren().add(root);
		}
	}
	
	private void gatherMovieInformation(RtQueryMovieItem dto) {
		Movie movie = new Movie(dto.getUrl());
		movie.setDescription("");
		movie.setTitle(dto.getName());
		movie.setYear(dto.getYear());

		// spin up thread that spins up two new threads and waits for them to finish. these two threads will race each other to get Movie Information via api
		(new Thread() {
			public void run() {
				Thread rtThread = new Thread() {
					public void run() {
						MovieInfoApi api = new MovieInfoApi();
						try {
							RtMovieDTO info = api.rtInfo(dto.getUrl());
							movie.setDirector(info.getDirector());
							movie.setRtRating(info.getScore());
							movie.setRtaRating(info.getAudienceScore());
							movie.setPosterURL(info.getImageURL());
							// only set longer description
							if (movie.getDescription().length() <  info.getDescription().length()) {
								movie.setDescription(info.getDescription());
							}
						} catch (APIRequestException e) {
							Platform.runLater(() -> {
								LayoutController.error(e.getMessage());
								e.printStackTrace();
							});
						}
					}
				};
				
				Thread imdbThread = new Thread() {
					public void run() {
						MovieInfoApi api = new MovieInfoApi();
						try {
							ImdbMovieDTO info = api.imdbInfo(dto.getName() + " " + dto.getYear());
							movie.setImdbRating(info.getImdbRating());
							movie.setMcRating(info.getMcRating());
							// get the longer description text
							String newDescr = info.getDescription().length() > info.getStoryline().length() ? info.getDescription() : info.getStoryline();
							
							// only set new description, if it's longer
							if (movie.getDescription().length() < newDescr.length()) {
								movie.setDescription(newDescr);
							}
						} catch (APIRequestException e) {
							Platform.runLater(() -> {
								LayoutController.error("Couldn't find information about this video on IMDB!");
								e.printStackTrace();
							});
						}
					}
				};
				
				rtThread.start();
				imdbThread.start();
				
				try {
					rtThread.join();
					imdbThread.join();
				} catch (InterruptedException e) {
					// Nothing to do here
				}
				
				System.out.println(movie.toString());
				
				Platform.runLater(() -> {
					user.linkMovie(movie);
					WatchListItem wli = new WatchListItem(movie);
					Router.instance().render(new MovieView(wli));
				});
			}
		}).start();		
	}
}