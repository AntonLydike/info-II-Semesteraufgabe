package store.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import exception.APIRequestException;

public class RtQueryDTO {
	private ArrayList<RtQueryMovieItem> movies;
	private ArrayList<RtQuerySeriesItem> series;
	
	public RtQueryDTO (JSONObject obj) throws APIRequestException {
		movies = new ArrayList<RtQueryMovieItem>();
		series = new ArrayList<RtQuerySeriesItem>();
		
		try {
			for(Object o: obj.getJSONArray("movies")) {
				JSONObject mov = (JSONObject) o;
				movies.add(new RtQueryMovieItem(
						getString(mov, "name"), 
						getInt(mov, "year"), 
						getString(mov, "url"),
						getString(mov, "image"),
						getCastItems(mov)
						));
			}
			
			for (Object o: obj.getJSONArray("series")) {
				JSONObject ser = (JSONObject) o;
				series.add(new RtQuerySeriesItem(
						getString(ser, "title"), 
						getInt(ser, "startYear"), 
						getInt(ser, "endYear"), 
						getString(ser, "url"), 
						getString(ser, "image")));
			}	
		} catch (Exception e) {
			throw new APIRequestException("[RtQueryDTO construction] Error while constructing DTO: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Gets Integer at field from object or -1 if non existent
	 * @param obj JSONObject source
	 * @param field The field to look at
	 * @return integer at field or -1
	 */
	private int getInt(JSONObject obj, String field) {
		if (obj.has(field) && !obj.isNull(field)) {
			return obj.getInt(field);
		} else {
			return -1;
		}
	}
	
	/**
	 * Gets String at field from object or -1 if non existent
	 * @param obj JSONObject source
	 * @param field The field to look at
	 * @return integer at field or -1
	 */
	private String getString(JSONObject obj, String field) {
		if (obj.has(field) && !obj.isNull(field)) {
			return obj.getString(field);
		} else {
			return "";
		}
	}
	
	private String[] getCastItems(JSONObject obj) {
		JSONArray actors = obj.getJSONArray("castItems");
		String ret[] = new String[actors.length()];
		int i = 0;
		for (Object o: actors) {
			ret[i++] = ((JSONObject) o).getString("name");
		}
		return ret;
		
	}

	public ArrayList<RtQueryMovieItem> getMovies() {
		return movies;
	}

	public ArrayList<RtQuerySeriesItem> getSeries() {
		return series;
	}

}
