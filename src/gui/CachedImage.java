package gui;

import java.util.HashMap;
import javafx.scene.image.*;

/**
 * Used to cache images loaded from the web
 * @author anton
 *
 */
public class CachedImage {
	
	private static HashMap<String, Image> cache = new HashMap<String, Image>();
	
	/**
	 * Returns either an already loaded image, or loads it (blocking) and then returns it. 
	 * @param url URL from which the Image should be loaded
	 * @return a cached Image for the URL
	 */
	public static Image get(String url) {
		if (cache.containsKey(url)) {
			return cache.get(url);
		} 
		
		Image ret = new Image(url);
		cache.put(url, ret);
		return ret;
	}
}
