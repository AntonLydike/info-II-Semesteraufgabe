package store.api;

/**
 * DTO for series found inside a RTQueryDTO
 * @author anton
 *
 */
public class RtQuerySeriesItem {
	private String title;
	private int yearStart;
	private int yearStop;
	private String url;
	private String image;
	
	public RtQuerySeriesItem(String title, int yearStart, int yearStop, String url, String image) {
		this.title = title;
		this.yearStart = yearStart;
		this.yearStop = yearStop;
		this.url = url;
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public int getYearStart() {
		return yearStart;
	}
	public int getYearStop() {
		return yearStop;
	}
	public String getUrl() {
		return url;
	}
	public String getImage() {
		return image;
	}
}