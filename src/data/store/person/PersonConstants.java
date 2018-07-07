package data.store.person;

public class PersonConstants {
    public static final String UPSERT_PERSON = "INSERT IGNORE INTO persons (name, rtPath) VALUES (?, ?)";
    public static final String SEARCH_PERSON_RT_PATH = "SELECT * from persons where rtPath = ?";
}
