package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Controls the whole apps layout, singleton
 * @author anton
 *
 */
public class LayoutController {
	private static LayoutController instance = null;
	
	@FXML
	public StackPane yield;
	@FXML
	public Label error;
	@FXML
	public AnchorPane root;
	
	public LayoutController() {
		if (instance != null) {
			System.out.println("[LayoutController] instance is not null!");
			throw new RuntimeException("There can only be one layout controller");
		}
		instance = this;
	}
	
	/**
	 * Gets the current instance of the LayoutController
	 * @return
	 */
	public static LayoutController instance() {
		return instance;
	}
	
	/**
	 * Displays the given String in the Error textbox at the bottom of the app
	 * @param text
	 */
	public static void error(String text) {
		instance.error.setText(text);
	}
	
	/**
	 * Render a node inside the app, removing the old one
	 * @param newNode the new Node to be displayed (can be null, to only remove a node)
	 * @param oldNode the old node to be removed (can be null, to only add a node) 
	 */
	public static void display(Node newNode, Node oldNode) {
		if (instance == null) {
			System.out.println("[LayoutController] instance is null!");
		}
		
		if (oldNode != null) {
			instance.yield.getChildren().remove(oldNode);
		}
		if (newNode != null) {
			instance.yield.getChildren().add(newNode);	
		}
	}
}
