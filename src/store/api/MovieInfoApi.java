package store.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import exception.APIRequestException;

/**
 * Api to connect to an online scraper of rotten tomatoes and IMDB. Written by myslef, using only the php library Simple HTML Dom: http://simplehtmldom.sourceforge.net/
 * @author anton
 *
 */
public class MovieInfoApi {

	// The api requires an access token. This one is hardcoded
	private final String accessToken = "e9b5jaxsqetwdk2iasd1nrr9x5nxii8r";
	// url to scraper
	private final String baseUrl = "https://antonlydike.de/scraper/";
	
	/**
	 * 
	 * Build (not validate) the query String for a service (either rt or imdb), type (depending on service, e.g. find, person) and query (e.g. "q=James Bond")
	 * @return A String representing the complete query string with auth token, etc... 
	 */
	private String buildQueryString(String service, String type, String query) {
		return baseUrl + service + "/" + type + ".php?auth=" + accessToken + "&" + query;
	}
	
	/**
	 * Takes a Reader and reads all the available input.
	 * @return The whole content
	 */
	private String readerToString(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	/**
	 * Reads JSON from URL
	 * @param url The url to read from
	 * @param descriptor gets prepended to the error message. E.g "[rt/find?q=James Bond]"
	 * @return the JSONObject containing the URLs JSON
	 * @throws An Exception when the request failed, or when the received JSON was invalid
	 */
	private JSONObject getJsonAt(String url, String descriptor) throws APIRequestException {
		try (InputStream is = new URL(url).openStream()) {
			String  json = readerToString(new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))));
			return new JSONObject(json);
		} catch (IOException e) {
			throw new APIRequestException(descriptor + " Couldn't fullfill request: " + e.getMessage(), e);
		} catch (JSONException e) {
			throw new APIRequestException(descriptor + " Invalid JSON: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Encodes all potentially harmful characters as URI components
	 * @param c The URI Component
	 * @return an escaped String or c, if escaping failed
	 */
	private String encodeURIcomponent(String c) {
		try {
			return URLEncoder.encode(c, "UTF-8")
				.replaceAll("\\+", "%20")
			    .replaceAll("\\%21", "!")
			    .replaceAll("\\%27", "'")
			    .replaceAll("\\%28", "(")
			    .replaceAll("\\%29", ")")
			    .replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Couldn't encode URI Component: " + c);
			return c;
		}
	}
	
	/**
	 * Uses RottenTomatoes Search api to look for a certain query
	 * @param query: The string to look for on rotten tomatoes
	 * @return a RtQueryDTO containing movies and series found for that query
	 * @throws APIRequestException
	 */
	public RtQueryDTO rtQuery(String query) throws APIRequestException {
		return new RtQueryDTO(getJsonAt(buildQueryString("rt", "find", "q=" + encodeURIcomponent(query)), "[rt/find?q=" + query + "]"));
	}

	/**
	 * Scrapes RT at path and returns info about that movie
	 * @param rtPath: The path on rotten tomatoes to scrape
	 * @return a RtMovieDTO object containing that movies data
	 * @throws APIRequestException
	 */
	public RtMovieDTO rtInfo(String rtPath) throws APIRequestException {
		return new RtMovieDTO(getJsonAt(buildQueryString("rt", "info", "url=" + encodeURIcomponent(rtPath)),  "[rt/info?url=" + rtPath + "]"), rtPath);
	}

	/**
	 * Scrapes RT at path and returns info about that person
	 * @param rtPath: The path on rotten tomatoes to scrape
	 * @return a JSONObject containing that persons data
	 * @throws APIRequestException
	 */
	public JSONObject rtPerson(String rtPath) throws APIRequestException {
		return getJsonAt(buildQueryString("rt", "person", "url=" + encodeURIcomponent(rtPath)),  "[rt/person?url=" + rtPath + "]");
	}
	
	/**
	 * Scrapes IMDB and looks for the movie with that name
	 * @param name The name of the movie
	 * @return Return a JSONObject containing information about that Movie
	 * @throws APIRequestException
	 */
	public JSONObject imdbInfo(String name) throws APIRequestException {
		return getJsonAt(buildQueryString("imdb", "info", "q=" + encodeURIcomponent(name)), "[imdb/info?q=" + name + "]");
	}
	
}
