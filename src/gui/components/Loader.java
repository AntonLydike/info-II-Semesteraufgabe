package gui.components;

import gui.Renderable;
import javafx.scene.Node;
import javafx.scene.image.*;

public class Loader implements Renderable {
	@Override
	public Node getView() {
		ImageView im = new ImageView(Loader.getImage());
		return im;
	}
	
	public static Image getImage() {
		return new Image("file:./../images/loader.dark.gif");
	}
}
