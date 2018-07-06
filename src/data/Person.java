package data;

/*
 * A Person is someone who is in movies or directs them, voices them, what ever...
 * 
 */
public class Person implements Comparable<Person>{

	private String name;
	private String rtPath; // path on rotten tomatoes, primary ID
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(String name, String rtPath) {
		super();
		this.name = name;
		this.rtPath = rtPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRtPath() {
		return rtPath;
	}

	public void setRtPath(String rtPath) {
		this.rtPath = rtPath;
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
