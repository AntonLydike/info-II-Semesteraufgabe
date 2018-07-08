package gui;

import data.User;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Handles which view will be displayed at any given time. Singleton.
 * @author anton
 *
 */
public class Router {
	private static Router instance;

	private Stage stage;
	private Node curr;
	private User currentUser = null;
	
	private Router(Stage s) {
		this.stage = s;
	}
	
	/**
	 * Initializes the Router with the stage, can only be called once
	 * @param s The Apps Stage
	 * @return Returns a reference to the router
	 */
	public static Router init(Stage s) {
		if (instance == null) {
			instance = new Router(s);
		} else {
			System.out.println("[Router] instance isn't null!");
			throw new RuntimeException("Router is already initialized!");
		}
		return instance;
	}
	
	/**
	 * gets the current router instance, must be initialized first
	 * @return
	 */
	public static Router instance() {
		if (instance == null) {
			System.out.println("[Router] instance is null!");
			throw new RuntimeException("Router isn't initialized yet!");
		}
		return instance;
	}
	
	public void setCurrentUser(User u) {
		currentUser = u;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Render a Renderable inside the layout
	 * @param r The View to display
	 */
	public void render(Renderable r) {
		Node next = r.getView();
		if (next == null) {
			LayoutController.error("Can't render nothing!");
			return;
		}
		// add a css class to make the "inserted element" styleable
		next.getStyleClass().add("yielded");
		LayoutController.display(next, curr);
		curr = next;
	}
	
	public Stage getStage() {
		return stage;
	}
}