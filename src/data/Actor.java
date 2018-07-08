package data;

/**
 * Actors are Persons that have a certain role in a movie
 * @author anton
 *
 */
public class Actor implements Comparable<Actor>{
	private Person person;
	private String role;
	
	public Actor(Person p, String r) {
		person = p;
		role = r;
	}
	
	// enables ArrayList.contains, remove, etc.. to only compare persons rtPath, which is a unique property
	@Override
	public int compareTo(Actor a) {
		return a.person.compareTo(person);
	}
	
	public boolean equals(Actor a) {
		return a.compareTo(this) == 0;
	}

	public Person getPerson() {
		return person;
	}

	public String getRole() {
		return role;
	}
	
	
}