package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class LayoutController {
	private static LayoutController instance = null;
	
	public StackPane yield;
	public Label error;
	
	public LayoutController() {
		if (instance != null) {
			System.out.println("[LayoutController] instance is not null!");
			throw new RuntimeException("There can only be one layout controller");
		}
		instance = this;
	}
	
	public static LayoutController instance() {
		return instance;
	}
	
	public static void error(String text) {
		instance.error.setText(text);
	}
	
	public static void display(Node newNode, Node oldNode) {
		if (instance == null) {
			System.out.println("[LayoutController] instance is null!");
		}
		
		if (oldNode != null) {
			instance.yield.getChildren().remove(oldNode);
		}
		instance.yield.getChildren().add(newNode);
	}
}
