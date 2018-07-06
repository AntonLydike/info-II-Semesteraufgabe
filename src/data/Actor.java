package data;

// this is not a real class, just for items of movie actor lists
//an actor is just a person with a role
//no inheriting, since a person can be an actor and a director, no duplicate data here pls
public class Actor implements Comparable<Actor>{
	public Person person;
	public String role;
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
}