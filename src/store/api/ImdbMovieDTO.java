package store.api;

import org.json.JSONObject;

import exception.APIRequestException;

/**
 * A DataTransferObject to transfer movie information from IMDB
 * @author anton
 *
 */
public class ImdbMovieDTO {
	private String imdbId;
	private String title;
	private int year;
	private byte imdbRating;
	private byte mcRating;
	private String description;
	private String storyline;
	
	/**
	 * Converts a JSON response to a DTO
	 * @param obj The response from the server
	 * @throws APIRequestException If any errors were encountered while reading the response
	 */
	public ImdbMovieDTO(JSONObject obj) throws APIRequestException {
		try {
			imdbId = getString(obj, "id");
			title = getString(obj, title);
			year = getInt(obj, title);
			imdbRating = (byte) getInt(obj, "imdb_rating");
			mcRating = (byte) getInt(obj, "metacritic_rating");
			description = getString(obj, "description");
			storyline = getString(obj, "storyline");
			
		} catch(Exception e) {
			throw new APIRequestException("[ImdbMovieDTO - construction] Couldn't read JSON: " + e.getMessage(), e);
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

	public String getImdbId() {
		return imdbId;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public byte getImdbRating() {
		return imdbRating;
	}

	public byte getMcRating() {
		return mcRating;
	}

	public String getDescription() {
		return description;
	}

	public String getStoryline() {
		return storyline;
	}
}
