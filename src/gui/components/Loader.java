package gui.components;

import gui.Renderable;
import javafx.scene.Node;
import javafx.scene.image.*;

/**
 * A quick way to get a loader anywhere
 * @author anton
 *
 */
public class Loader implements Renderable {
	@Override
	public Node getView() {
		ImageView im = new ImageView(Loader.getImage());
		return im;
	}
	
	/**
	 * Returns an Image with the dark loading icon
	 * @return Dark loader gif Image object
	 */
	public static Image getImage() {
		return new Image("/gui/images/loader.dark.gif");
	}
}
