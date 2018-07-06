package store.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import data.Actor;
import data.Movie;
import data.Person;

/**
 * Data Transfer Object for Movie Data on rotten tomatoes
 * @author anton
 *
 */
public class RtMovieDTO {
	private String title;
	private String imageURL;
	private byte score;
	private byte audienceScore;
	private Person director;
	private String description;
	private ArrayList<Actor> actors;
	private String path;
	
	public RtMovieDTO(JSONObject obj, String path) {
		this.path = path;
		director = new Person(obj.getJSONObject("director").getString("name"), "", obj.getJSONObject("director").getString("url"));
		title = obj.getString("title");
		description = obj.getString("description");
		imageURL = obj.getString("image");
		
		score = (byte) obj.getJSONObject("score").getInt("critic");
		audienceScore = (byte) obj.getJSONObject("score").getInt("audience");
		
		actors = new ArrayList<Actor>();
		
		for (Object o: obj.getJSONArray("cast")) {
			JSONObject j = (JSONObject) o;
			actors.add(new Actor(new Person(
						j.getString("name"),
						j.getString("picture"),
						j.getString("url")
					), j.getString("role")));
		}
	}
}
