package data.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to map column names of a database to an entity
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Col {
    String name();
}
