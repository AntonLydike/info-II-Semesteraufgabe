package store.api;

import java.util.ArrayList;
import org.json.JSONObject;
import data.Actor;
import data.Person;
import exception.APIRequestException;

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
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private String path;

	/**
	 * Converts a JSON response to a DTO
	 * @param obj The response from the server
	 * @param path The movie path on rotten tomatoes (not included in the JSON response)
	 * @throws APIRequestException If any errors were encountered while reading the response
	 */
	public RtMovieDTO(JSONObject obj, String path) throws APIRequestException {
		try {
			this.path = path;
			if (obj.has("director")) {
				director = new Person(obj.getJSONObject("director").getString("name"), "", obj.getJSONObject("director").getString("url"));
			} else {
				director = new Person("??", "https://staticv2-4.rottentomatoes.com/static/images/redesign/actor.default.tmb.gif","/nobody");
			}
			title = getString(obj, "title");
			description = getString(obj, "description");
			imageURL = getString(obj, "image");
			
			score = (byte) getInt(obj.getJSONObject("score"), "critic");
			audienceScore = (byte) getInt(obj.getJSONObject("score"), "audience");
			
			actors = new ArrayList<Actor>();
			
			for (Object o: obj.getJSONArray("cast")) {
				JSONObject j = (JSONObject) o;
				actors.add(new Actor(new Person(
							getString(j, "name"),
							getString(j, "picture"),
							getString(j, "url")
						), getString(j, "role")));
			}	
		} catch(Exception e) {
			System.err.println(obj.toString(2));
			throw new APIRequestException("[RtMovieDTO construction] Error while constructing: " + e.getMessage(), e);
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

	public String getTitle() {
		return title;
	}

	public String getImageURL() {
		return imageURL;
	}

	public byte getScore() {
		return score;
	}

	public byte getAudienceScore() {
		return audienceScore;
	}

	public Person getDirector() {
		return director;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public String getPath() {
		return path;
	}
}
