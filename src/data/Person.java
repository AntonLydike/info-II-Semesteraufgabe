package data;

/*
 * A Person is someone who is in movies or directs them, voices them, what ever...
 * 
 */
public class Person implements Comparable<Person>{

	private String name;
	private String imageURL;
	private String rtPath; // path on rotten tomatoes, primary ID
	private String bio;
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(String name, String imageURL, String rtPath, String bio) {
		super();
		this.name = name;
		this.imageURL = imageURL;
		this.rtPath = rtPath;
		this.bio = bio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getRtPath() {
		return rtPath;
	}

	public void setRtPath(String rtPath) {
		this.rtPath = rtPath;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	// compare only by rotten tomatoes path
	@Override
	public int compareTo(Person o) {
		return o.getRtPath().compareTo(rtPath);
	}
	
	public boolean equals(Person a) {
		return a.compareTo(this) == 0;
	}
}
