package data.store;

import data.common.Col;
import data.common.Entity;
import exception.IdNotUniqueException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

/**
 * BaseDao provides basic DB access including all basic methods
 * like INSERT, UPDATE, SELECT *
 * This class can be extended to create a specific DAO with
 * a defined DTO. Additionally the table name must be provided in the constructor.
 * The DB Connection is defined in DataAccess.java
 * @param <T> T is the DTO which represents the entity of the DB
 */
public class BaseDao<T extends Entity> {

    private static final Logger LOGGER = Logger.getLogger(BaseDao.class.getName());
    private DataAccess dataAccess;

    private String table;
    private Class entityClass;

    private static final String INSERT_ENTITY = "INSERT INTO ${table} ( ${names} ) VALUES ( ${values} )";
    private static final String UPDATE_ENTITY = "UPDATE ${table} SET ${values} WHERE id=?";
    private static final String SELECT_ENTITY = "SELECT * FROM ${table} WHERE ${params} ORDER BY ${sortOrder}";
    private static final String GET_BY_ID = "SELECT * FROM ${table} WHERE id=?";
    private static final String GET_ALL_SORTED = "SELECT * FROM ${table} ORDER BY ${sortOrder} LIMIT ?,?";


    private final String DEFAULT_SEARCH_ORDER = "id ASC";

    public BaseDao(Class<T> clazz, String table) throws SQLException, ClassNotFoundException {
        this.table = table;
        this.entityClass = clazz;
        dataAccess = DataAccess.instance();
    }

    /**
     * Get an entity by the id
     * @param id identifier of the entity (must be 'id')
     * @return Object of T
     */
    public T getById(String id) {
        LOGGER.info("Get entity by id, parameters: {id: "+id+", table: "+ table+ " }");
        try {
            ArrayList<T> results = executeQuery(entityClass, getSQLStatement(GET_BY_ID), new ArrayList<String>() {{
                add(id);
            }});
            switch (Long.signum((long)results.size() - 1)) {
                case -1:
                    return null;
                case 0:
                    return results.get(0);
                case 1:
                    throw new IdNotUniqueException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Searches for all entities without any restriction, sorted
     * @param start start value for pagination
     * @param limit limit value
     * @param sortOrder Map of <code>Map&lt;column, ASC/DESC&gt;</code>, the order of the map represents the order of the sql statement
     * @return A <code>List&lt;T&gt;</code> for the entity, max count is limited by the limit input parameter
     * @throws SQLException if a database access error occurs
     */
    public List<T> searchAll(int start, int limit, Map<String, String> sortOrder) throws SQLException {
        LOGGER.info("Search for all entities, parameters: {start: "+start+", limit: "+ limit+ ", sortOrders: "+ sortOrder + ", table: "+ table+ " }");

        ArrayList<T> results = executeQuery(entityClass, getSQLStatement(GET_ALL_SORTED, sortOrder), new ArrayList<Object>() {{
            add(start);
            add(limit);
        }});

        return results;
    }

    public List<T> search(int start, int limit, Map<String, String> params, Map<String, String> sortOrder) throws SQLException {
        LOGGER.info("Search for all entities, parameters: {start: "+start+", limit: "+ limit+ ", sortOrders: "+ sortOrder + ", table: "+ table+ " }");

        ArrayList<T> results = executeQuery(entityClass, getSQLStatement(SELECT_ENTITY, params, sortOrder), new ArrayList<Object>() {{
            for(Map.Entry<String, String> param : params.entrySet()) {
                add(param.getValue());
            }
        }});

        return results;
    }

    /**
     * Inserts a entity into the selected table
     * @param params Map which contains all values which should be updated,
     *               the format is: <code>Map&lt;columnName, value&gt;</code>
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if the first result is an update
     *         count or there is no result
     * @throws SQLException if a database access error occurs
     */
    public boolean insert(Map<String, String> params) throws SQLException {
        LOGGER.info("Insert entity, parameters: {params: " + params + ", table: " + table + " }");

        Map<Integer, Object> values = new HashMap<>();
        String nameString = "";
        String valueString = "";
        int i = 1;
        for (Map.Entry<String, String> param : params.entrySet()) {
            nameString = i == params.size() ? nameString.concat(param.getKey()) : nameString.concat(param.getKey()).concat(",");
            valueString = i == params.size() ? valueString.concat("?") : valueString.concat("?,");
            values.put(i, param.getValue());
            i++;
        }
        String sqlStatement = getSQLStatement(INSERT_ENTITY).replace("${names}", nameString).replace("${values}", valueString);

        return executeSQL(sqlStatement, values);

    }

    /**
     * Updates an entity by id
     * @param id identifier for updating entity
     * @param params Map which contains all values which should be updated,
     *               the format is: <code>Map&lt;columnName, value&gt;</code>
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if the first result is an update
     *         count or there is no result
     * @throws SQLException if a database access error occurs
     */
    public boolean update(String id, Map<String, String> params) throws SQLException {
        LOGGER.info("Update entity, parameters: {id: " + id + ", params: " + params + ", table: " + table + " }");

        Map<Integer, Object> values = new HashMap<>();
        String valueString = "";
        int i = 1;
        for (Map.Entry<String, String> param : params.entrySet()) {
            String pair = param.getKey() + "=?";
            valueString = i == params.size() ? valueString.concat(pair) : valueString.concat(pair + ", ");
            values.put(i, param.getValue());
            i++;
        }
        values.put((params.size() + 1), id);
        String sqlStatement = getSQLStatement(UPDATE_ENTITY).replace("${values}", valueString);


        return executeSQL(sqlStatement, values);

    }


    /**
     * Executes any prepared SQL statement with provided parameters
     * @param clazz identifies the class which will be returned
     * @param sqlStatementant should be a prepared statement
     * @param params contains all parameters for the prepared statement, the key represents the index for the statement
     * @throws SQLException if a database access error occurs
     * @return List of entites of class <code>clazz</code>
     */
    protected ArrayList<T> executeQuery(Class<T> clazz, String sqlStatementant, List params) throws SQLException {
        LOGGER.info("Execute SQL Query, parameters: {query: \"" + sqlStatementant + "\", params: " + params + ", table: " + table + " }");
        PreparedStatement s = dataAccess.getConnection().prepareStatement(sqlStatementant);
        int i = 1;
        for (Object param : params ) {
            s.setObject(i, param);
            i++;
        }
        ResultSet results = s.executeQuery();

        ArrayList<T> list = new ArrayList<>();

        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        for(Field field: fields) {
            field.setAccessible(true);
        }

        while (results.next()){

            try {
                T dto = clazz.getConstructor().newInstance();

                for(Field field: fields) {
                    Col col = field.getAnnotation(Col.class);
                    if(col!=null) {
                        try {
                            String value = results.getString(col.name());
                            field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                list.add(dto);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    protected ArrayList<T> executeQuery(Class<T> clazz, String sqlStatementant, List params, Map<String, String> sortOrders) throws SQLException {
        return executeQuery(clazz, getSQLStatement(sqlStatementant, sortOrders), params);
    }

    /**
     * Executes any prepared SQL statement with provided parameters
     * @param sql sql statement to execute
     * @param params contains all parameters for the prepared statement, the key represents the index for the statement
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if the first result is an update
     *         count or there is no result
     * @throws SQLException if a database access error occurs
     */
    protected boolean executeSQL(String sql, Map<Integer, Object> params) throws SQLException {
        LOGGER.info("Execute SQL Statement, parameters: {statement: \"" + sql + "\", params: " + params + ", table: " + table + " }");
        PreparedStatement s = dataAccess.getConnection().prepareStatement(sql);
        for (Map.Entry<Integer, Object> param : params.entrySet() ) {
            s.setObject(param.getKey(), param.getValue());
        }

        return s.execute();
    }

    /**
     * prepares the sql statement with dynamic table name
     * @param sql sql Statement (i.e. BaseDao.INSERT_ENTITY)
     * @return full sql statement with the correct table name
     */
    private String getSQLStatement(String sql){
        return sql.replace("${table}", this.table);
    }

    /**
     * prepares the sql statement with order by statement, default: ORDER BY id, ASC
     * @param sql SQL statement
     * @param sortOrder Map of <code>Map&lt;column, ASC/DESC&gt;</code>, the order of the map represents the order of the sql statement
     * @return full sql statement with correct ORDER BY, default: ORDER BY id, ASC
     */
    private String getSQLStatement(String sql, Map<String, String> sortOrder){
        String ordering = "";
        if(sortOrder != null) {
            for (Map.Entry<String, String> order : sortOrder.entrySet()) {
                ordering = ordering.concat(order.getKey() + " " + order.getValue() + " ");
            }
            return getSQLStatement(sql).replace("${sortOrder}", ordering);
        }
        return getSQLStatement(sql).replace("${sortOrder}", DEFAULT_SEARCH_ORDER);
    }

    private String getSQLStatement(String sql, Map<String, String> params,  Map<String, String> sortOrder){
        String ordering = "";
        if(sortOrder != null) {
            for (Map.Entry<String, String> order : sortOrder.entrySet()) {
                ordering = ordering.concat(order.getKey() + " " + order.getValue() + " ");
            }
        } else {
            ordering = DEFAULT_SEARCH_ORDER;
        }
        String paramValues = "";

        if(params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                paramValues = paramValues.concat(param.getKey() + "= ?  AND ");
            }
            paramValues = paramValues.substring(0, paramValues.lastIndexOf(" AND "));
            sql = getSQLStatement(sql).replace("${params}", paramValues);
        }
        return getSQLStatement(sql).replace("${sortOrder}", ordering);
    }
}
