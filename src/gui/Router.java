package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import data.*;
import gui.login.*;

public class Router {
	private static Router instance;

	private Stage stage;
	private Node curr;
	
	private Router(Stage s) {
		this.stage = s;
	}
	
	public static Router init(Stage s) {
		if (instance == null) {
			instance = new Router(s);
		} else {
			System.out.println("[Router] instance isn't null!");
			throw new RuntimeException("Router is already initialized!");
		}
		return instance;
	}
	
	public static Router instance() {
		if (instance == null) {
			System.out.println("[Router] instance is null!");
			throw new RuntimeException("Router isn't initialized yet!");
		}
		return instance;
	}
	
	public void render(Renderable r) {
		Node next = r.getView();
		if (next == null) {
			LayoutController.error("Can't render nothing!");
			return;
		}
		// add a css class to make the "inserted element" styleable
		next.getStyleClass().add("yielded");
		LayoutController.display(next, curr);
		
	}
}