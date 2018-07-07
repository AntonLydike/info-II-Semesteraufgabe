package data.store.person;

import data.Person;
import data.dto.PersonDTO;
import data.store.common.BaseDao;
import exception.IdNotUniqueException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PersonDao extends BaseDao<PersonDTO> {

    private static PersonDao unique = null;


    private PersonDao() throws SQLException, ClassNotFoundException {
        super(PersonDTO.class, "persons");
    }

    public static PersonDao instance() throws SQLException, ClassNotFoundException{
        if(unique == null)
            unique = new PersonDao();
        return unique;
    }


    public PersonDTO upsert(Person person) throws SQLException{
        executeSQL(PersonConstants.UPSERT_PERSON, Arrays.asList(
               person.getName(),
               person.getRtPath()
        ));
        return searchByRtPath(person.getRtPath());
    }

    public PersonDTO searchByRtPath(String rtPath) throws SQLException {
        List<PersonDTO> results = executeQuery(PersonDTO.class, PersonConstants.SEARCH_PERSON_RT_PATH, Arrays.asList(rtPath));
        if (results.size() == 1) {
            return results.get(0);
        } else if (results.size() == 0) {
            return null;
        } else {
            throw new IdNotUniqueException();
        }
    }
}
