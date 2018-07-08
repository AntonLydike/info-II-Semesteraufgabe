package gui;

import javafx.scene.Node;

/**
 * Signals the Router, that this is an Object that can be rendered inside a Layout
 * @author anton
 *
 */
public interface Renderable {
	Node getView();
}
