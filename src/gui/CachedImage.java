package gui;

import java.util.HashMap;
import javafx.scene.image.*;

public class CachedImage {
	
	private static HashMap<String, Image> cache = new HashMap<String, Image>();
	
	public static Image get(String url) {
		if (cache.containsKey(url)) {
			return cache.get(url);
		} 
		
		Image ret = new Image(url);
		cache.put(url, ret);
		return ret;
	}
}
