package gui.addMovie;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;
import data.Movie;
import data.Person;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import store.api.ImdbMovieDTO;
import store.api.MovieInfoApi;
import store.api.RtMovieDTO;
import store.api.RtQueryDTO;
import store.api.RtQueryMovieItem;

/**
 * Creates the AddMovie Scene. It will ask for the movie Title and crawl IMDB and rotten tomatoes for information about it. 
 * @author anton
 *
 */
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
	@FXML Button addCustom;
	
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
		addCustom.setVisible(false);
	}
	
	private void loader(boolean display) {
		if (display) {
			searchResults.getChildren().clear();
			searchResults.getChildren().add(loader);
			loader.setVisible(true);
			addCustom.setVisible(false);
		} else {
			searchResults.getChildren().remove(loader);
			addCustom.setVisible(true);
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
	
	/**
	 * Display the search results in the searchResults VBox
	 * @param results
	 */
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
	
	/**
	 * Gathers movie Information about a given RtQueryMovieItem, when done it will load a MovieView for that Movie and add it to the users watchlist
	 * @param dto The Movie in question
	 */
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
					if (user.linkMovie(movie)) {
						WatchListItem wli = new WatchListItem(movie);
						Router.instance().render(new MovieView(wli));	
					} else {
						LayoutController.error("Couldn't add Movie!");
						Router.instance().render(new HomeView());
					}
				});
			}
		}).start();		
	}
	
	@FXML void addCustomMovie(ActionEvent e) {
		// default poster on rotten tomatoes
		String noPosterUrl = "https://staticv2-4.rottentomatoes.com/static/images/redesign/poster_default_redesign.gif";
		
		ButtonType submit = new ButtonType("Submit", ButtonData.OK_DONE);
		
		Dialog<Movie> dialog = new Dialog<>();
		TextField title = new TextField(),
				  director = new TextField(),
				  year = new TextField(),
				  poster = new TextField();
		TextArea description = new TextArea();
		
		dialog.setTitle("Add a new custom movie");
		dialog.setHeaderText(null);
		
		GridPane grid = new GridPane();
		grid.setHgap(16);
		grid.setVgap(16);
		grid.add(new Label("Title:"), 0, 0);
		grid.add(title, 1, 0);
		grid.add(new Label("Year:"), 0, 1);
		grid.add(year, 1, 1);
		grid.add(new Label("Poster Image URL:"), 0, 2);
		grid.add(poster, 1, 2);
		grid.add(new Label("Director:"), 0, 3);
		grid.add(director, 1, 3);
		grid.add(new Label("Description:"), 0, 4);
		grid.add(description, 1, 4);
		
		dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);
		
		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == submit) {
		    	if (title.getText().trim() == "") return null;
		    	String posterUrl = poster.getText().trim();
		    	if (posterUrl.length() == 0) posterUrl = noPosterUrl;
		    	// test values
		    	try {
		    		new java.net.URL(posterUrl);
		    		Integer.parseInt(year.getText());
		    	} catch (NumberFormatException | MalformedURLException err) {
		    		return null;
		    	}
		        Movie mov = new Movie("/custom/" + user.getId() + "/" + title.getText().trim());
		        mov.setTitle(title.getText());
		    	mov.setDescription(description.getText().trim());
		    	mov.setYear(Integer.parseInt(year.getText().trim()));
		    	mov.setPosterURL(posterUrl);
		    	mov.setDirector(new Person(director.getText().trim(), Person.NO_PERSON_IMAGE, "/custom/" + user.getId() + "/" + director.getText().trim()));
		    	return mov;
		    }
		    return null;
		});
		
		Optional<Movie> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			Movie movie = result.get();
			if (user.linkMovie(movie)) {
				WatchListItem wli = new WatchListItem(movie);
				Router.instance().render(new MovieView(wli));	
			} else {
				LayoutController.error("Couldn't add Movie!");
			}
		} else {
			LayoutController.error("Couldn't add Movie!");
		}
	}
}