package com.vidlus.jarch.mage;

import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.vidlus.jarch.data.Delete;
import com.vidlus.jarch.data.Field;
import com.vidlus.jarch.data.Insert;
import com.vidlus.jarch.data.KeyForeign;
import com.vidlus.jarch.data.KeyPrimary;
import com.vidlus.jarch.data.Match;
import com.vidlus.jarch.data.Nature;
import com.vidlus.jarch.data.OrdName;
import com.vidlus.jarch.data.Select;
import com.vidlus.jarch.data.Table;
import com.vidlus.jarch.data.TableHead;
import com.vidlus.jarch.data.Typed;
import com.vidlus.jarch.data.Update;

public class WizBased {

    private WizBased() {
    }

    /**
     * Creates an Insert statement from a Table.
     * Maps table head and field values from the table to an Insert object.
     *
     * @param fromTable the source table to create the insert from
     * @return an Insert object with table head and valued fields
     */
	public static Insert makeInsert(Table fromTable) {
	    var result = new Insert();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
	            result.valuedList = fromTable.fieldList.stream().map(Field::toValued).toList();
	        }
	    }
	    return result;
	}

    /**
     * Creates a Select statement from a Table.
     * Maps table head, typed fields, and primary key filters from the table to a Select object.
     *
     * @param fromTable the source table to create the select from
     * @return a Select object with table head, field list, and primary key filters
     */
	public static Select makeSelect(Table fromTable) {
	    var result = new Select();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
	            result.fieldList = fromTable.fieldList.stream().map(Field::toTyped).toList();
				var keyFields = fromTable.fieldList.stream().filter(Field::isKeyPrimary).toList();
				if (!keyFields.isEmpty()) {
					result.filterList = keyFields.stream().map(Field::toFilter).toList();
				}
	        }
	    }
	    return result;
	}

    /**
     * Creates an Update statement from a Table.
     * Uses non-primary key fields as values and primary key fields as filters.
     *
     * @param fromTable the source table to create the update from
     * @return an Update object with valued fields (non-primary) and filter fields (primary)
     * @throws IllegalArgumentException if the table has no non-primary key fields or no primary key fields
     */
	public static Update makeUpdate(Table fromTable) {
	    var result = new Update();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
	            var normalFields = fromTable.fieldList.stream().filter(Field::isNotKeyPrimary).toList();
				if (normalFields.isEmpty()) {
					throw new IllegalArgumentException("Could not create an Update from Table because it has no non-primary key fields.");
				}
				result.valuedList = normalFields.stream().map(Field::toValued).toList();
				var keyFields = fromTable.fieldList.stream().filter(Field::isKeyPrimary).toList();
				if (keyFields.isEmpty()) {
					throw new IllegalArgumentException("Could not create an Update from Table because it has no primary key fields.");
				}
				result.filterList = keyFields.stream().map(Field::toFilter).toList();
	        }
	    }
	    return result;
	}

    /**
     * Creates a Delete statement from a Table.
     * Uses primary key fields as filters for the deletion.
     *
     * @param fromTable the source table to create the delete from
     * @return a Delete object with primary key filters
     * @throws IllegalArgumentException if the table has no primary key fields
     */
	public static Delete makeDelete(Table fromTable) {
	    var result = new Delete();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
				var keyFields = fromTable.fieldList.stream().filter(Field::isKeyPrimary).toList();
				if (keyFields.isEmpty()) {
					throw new IllegalArgumentException("Could not create a Delete from Table because it has no primary key fields.");
				}
				result.filterList = keyFields.stream().map(Field::toFilter).toList();
	        }
	    }
	    return result;
	}

    /**
     * Maps a ResultSet to a list of objects of the specified type.
     * Uses column metadata to determine constructor and field mappings.
     *
     * @param <T> the target class type
     * @param result the ResultSet to map
     * @param onClass the class to map each row to
     * @return a list of mapped objects, one per row
     * @throws Exception if mapping or reflection operations fail
     */
    public static <T> List<T> mapResults(ResultSet result, Class<T> onClass) throws Exception {
        return WizBased.mapResults(result, null, onClass);
    }

    /**
     * Maps a ResultSet to a list of objects with explicit field type information.
     * Uses provided field list to determine type information for mapping.
     *
     * @param <T> the target class type
     * @param result the ResultSet to map
     * @param fieldList the list of typed fields to use for mapping (can be null)
     * @param onClass the class to map each row to
     * @return a list of mapped objects, one per row
     * @throws Exception if mapping or reflection operations fail
     */
    public static <T> List<T> mapResults(ResultSet result, List<Typed> fieldList, Class<T> onClass) throws Exception {
        var results = new ArrayList<T>();
        while (result.next()) {
            var mapped = WizBased.mapResult(result, fieldList, onClass);
            results.add(mapped);
        }
        return results;
    }

    /**
     * Maps a single row from a ResultSet to an object of the specified type.
     * Automatically determines constructor and field mappings from column metadata.
     *
     * @param <T> the target class type
     * @param result the ResultSet positioned at the desired row
     * @param onClass the class to map the row to
     * @return a mapped object instance
     * @throws Exception if mapping or reflection operations fail
     */
    public static <T> T mapResult(ResultSet result, Class<T> onClass) throws Exception {
        return WizBased.mapResult(result, null, onClass);
    }

    /**
     * Maps a single row from a ResultSet to an object with explicit field type information.
     * Tries standard constructor first, then looks for best matching constructor.
     * Sets remaining columns as instance fields if needed.
     *
     * @param <T> the target class type
     * @param result the ResultSet positioned at the desired row
     * @param fieldList the list of typed fields to use for mapping (can be null)
     * @param onClass the class to map the row to
     * @return a mapped object instance
     * @throws Exception if mapping or reflection operations fail
     */
    public static <T> T mapResult(ResultSet result, List<Typed> fieldList, Class<T> onClass) throws Exception {
        if (WizData.isNatureData(onClass)) {
			return WizData.getOn(result.getObject(1), onClass);
		}
		var colsClasses = WizBased.getColumnsClasses(result);
        var colsValues = WizBased.getColumnsValues(result);
        try {
            var standardOne = onClass.getConstructor(colsClasses);
            return standardOne.newInstance(colsValues);
        } catch (NoSuchMethodException e) {
            Nature[] colsNatures;
            if (fieldList == null || fieldList.isEmpty()) {
                colsNatures = WizBased.getNaturesFrom(result);
            } else {
                colsNatures = fieldList.stream().map(f -> f.type).toArray(Nature[]::new);
            }
            var colsNames = WizBased.getColumnsNames(result);
            var bestOne = WizLang.getBestConstructor(onClass, colsNatures, colsNames);
            if (bestOne == null) {
                bestOne = onClass.getConstructor();
            }
            Object[] argsValues = new Object[bestOne.getParameterCount()];
            Class<?>[] argsTypes = bestOne.getParameterTypes();
            for (int i = 0; i < argsValues.length; i++) {
                argsValues[i] = WizData.getOn(colsValues[i], argsTypes[i]);
            }
            var instance = onClass.cast(bestOne.newInstance(argsValues));
            if (argsValues.length < colsValues.length) {
                var columnsNames = WizBased.getColumnsNames(result);
                for (int i = argsValues.length; i < colsValues.length; i++) {
                    var columnName = columnsNames[i];
                    var field = onClass.getDeclaredField(columnName);
                    var onValue = WizData.getOn(colsValues[i], field.getType());
                    WizLang.forceSetField(field, instance, onValue);
                }
            }
            return instance;
        }
    }

	public static Table getTable(TableHead tableHead, Connection connection) throws Exception {
	    var fieldList = new ArrayList<com.vidlus.jarch.data.Field>();
	    var keyPrimaryList = new ArrayList<KeyPrimary>();
	    var keyForeignList = new ArrayList<KeyForeign>();
	    var table = new Table(tableHead, fieldList, keyPrimaryList, keyForeignList);
	    var meta = connection.getMetaData();
	    var primaryRS = meta.getPrimaryKeys(tableHead.catalog, tableHead.schema, tableHead.name);
	    while (primaryRS.next()) {
	        var keyName = primaryRS.getString("PK_NAME");
	        var keyPrimary = keyPrimaryList.stream()
	                .filter(p -> Objects.equals(p.name, keyName))
	                .findFirst().orElse(null);
	        if (keyPrimary == null) {
	            keyPrimary = new KeyPrimary(keyName, new ArrayList<>());
	            keyPrimaryList.add(keyPrimary);
	        }
	        var sequence = primaryRS.getInt("KEY_SEQ");
	        var columnName = primaryRS.getString("COLUMN_NAME");
	        var keyColumn = new OrdName(sequence, columnName);
	        keyPrimary.columnList.add(keyColumn);
	    }
	    var columnsRS = meta.getColumns(tableHead.catalog, tableHead.schema, tableHead.name, "%");
	    while (columnsRS.next()) {
	        final var fieldOrd = columnsRS.getInt("ORDINAL_POSITION");
	        final var fieldName = columnsRS.getString("COLUMN_NAME");
	        final var fieldNature = WizBased.getNatureOfSQL(columnsRS.getInt("DATA_TYPE"));
	        final var fieldSize = columnsRS.getInt("COLUMN_SIZE");
	        final var fieldPrecision = columnsRS.getInt("DECIMAL_DIGITS");
	        final var fieldNotNull = "NO".equals(columnsRS.getString("IS_NULLABLE"));
	        final var fieldAutoIncrement = !"NO".equals(columnsRS.getString("IS_AUTOINCREMENT"));
	        final var fieldAutoGenerated = !"NO".equals(columnsRS.getString("IS_GENERATEDCOLUMN"));
	        final var fieldKeyPrimary = keyPrimaryList.stream().anyMatch(k -> k.columnList.stream().anyMatch(c -> Objects.equals(c.name, fieldName)));
	        final var field = new com.vidlus.jarch.data.Field(fieldOrd, fieldName, fieldNature, fieldSize, fieldPrecision, fieldNotNull, fieldAutoIncrement, fieldAutoGenerated, fieldKeyPrimary);
	        field.setTable(table);
	        fieldList.add(field);
	    }
	    var foreignRS = meta.getImportedKeys(tableHead.catalog, tableHead.schema, tableHead.name);
	    while (foreignRS.next()) {
	        var inName = foreignRS.getString("FK_NAME");
	        var outName = foreignRS.getString("PK_NAME");
	        var keyForeign = keyForeignList.stream()
	                .filter(f -> Objects.equals(f.inName, inName) && Objects.equals(f.outName, outName))
	                .findFirst().orElse(null);
	        if (keyForeign == null) {
	            var outCatalog = foreignRS.getString("PKTABLE_CAT");
	            var outSchema = foreignRS.getString("PKTABLE_SCHEM");
	            var outTable = foreignRS.getString("PKTABLE_NAME");
	            var outTableHead = new TableHead(outCatalog, outSchema, outTable);
	            keyForeign = new KeyForeign(inName, outName, outTableHead, new ArrayList<>());
	            keyForeignList.add(keyForeign);
	        }
	        var sequence = foreignRS.getInt("KEY_SEQ");
	        var inColumn = foreignRS.getString("FKCOLUMN_NAME");
	        var outColumn = foreignRS.getString("PKCOLUMN_NAME");
	        var match = new Match(sequence, inColumn, outColumn);
	        keyForeign.matchList.add(match);
	    }
	    return table;
	}

    /**
     * Extracts all column names from a ResultSet.
     *
     * @param results the ResultSet to extract column names from
     * @return an array of column names
     * @throws Exception if metadata extraction fails
     */
	public static String[] getColumnsNames(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var names = new String[meta.getColumnCount()];
	    for (int i = 1; i <= names.length; i++) {
	        names[(i - 1)] = meta.getColumnName(i);
	    }
	    return names;
	}

    /**
     * Extracts the Java class types of all columns from a ResultSet.
     *
     * @param results the ResultSet to extract column types from
     * @return an array of column classes
     * @throws Exception if metadata extraction or class loading fails
     */
	public static Class<?>[] getColumnsClasses(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var classes = new Class<?>[meta.getColumnCount()];
	    for (int i = 1; i <= classes.length; i++) {
	        var className = meta.getColumnClassName(i);
	        classes[(i - 1)] = Class.forName(className);
	    }
	    return classes;
	}

    /**
     * Extracts all column values from the current row of a ResultSet.
     *
     * @param results the ResultSet positioned at a row
     * @return an array of column values
     * @throws Exception if value extraction fails
     */
	public static Object[] getColumnsValues(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var values = new Object[meta.getColumnCount()];
	    for (int i = 1; i <= values.length; i++) {
	        values[(i - 1)] = results.getObject(i);
	    }
	    return values;
	}

    /**
     * Extracts the Nature types of all columns from a ResultSet.
     * Converts SQL types to internal Nature enums.
     *
     * @param results the ResultSet to extract nature types from
     * @return an array of Nature types for each column
     * @throws Exception if metadata extraction fails
     */
	public static Nature[] getNaturesFrom(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var natures = new Nature[meta.getColumnCount()];
	    for (int i = 1; i <= natures.length; i++) {
	        natures[(i - 1)] = WizBased.getNatureOfSQL(meta.getColumnType(i));
	    }
	    return natures;
	}

    /**
     * Converts a JDBC type code to a Nature enum.
     * Maps SQL types to internal type representations.
     *
     * @param jdbcType the JDBC type code from Types
     * @return the corresponding Nature enum value
     * @throws UnsupportedOperationException if the JDBC type is not recognized
     */
	public static Nature getNatureOfSQL(int jdbcType) {
	    switch (jdbcType) {
	        case 16: // BOOLEAN
	            return Nature.Bool;
	        case -7: // BIT
	            return Nature.Bit;
	        case -6: // TINYINT
	            return Nature.Byte;
	        case 5: // SMALLINT
	            return Nature.Small;
	        case 4: // INTEGER
	            return Nature.Int;
	        case -8: // ROWID
	            return Nature.Serial;
	        case -5: // BIGINT
	            return Nature.Long;
	        case 6: // FLOAT
	            return Nature.Float;
	        case 7: // REAL
	            return Nature.Real;
	        case 8: // DOUBLE
	            return Nature.Double;
	        case 2: // NUMERIC
	            return Nature.Numeric;
	        case 3: // DECIMAL
	            return Nature.BigNumeric;
	        case 1: // CHAR
	        case -15: // NCHAR
	        case -11: // NCHAR
	            return Nature.Char;
	        case 12: // VARCHAR
	        case -1: // LONGVARCHAR
	        case -9: // NVARCHAR
	            return Nature.Chars;
	        case 91: // DATE
	            return Nature.Date;
	        case 92: // TIME
	            return Nature.Time;
	        case 93: // TIMESTAMP
	            return Nature.Timestamp;
	        case 2013: // TIME_WITH_TIMEZONE
	        case 2014: // TIMESTAMP_WITH_TIMEZONE
	            return Nature.ZoneTime;
	        case -2: // BINARY
	        case -3: // VARBINARY
	            return Nature.Bytes;
	        case -4: // LONGVARBINARY
	        case 2004: // BLOB
	            return Nature.Blob;
	        case -16: // LONGNVARCHAR
	        case 2005: // CLOB
	            return Nature.Text;
	        case 1111: // OTHER
	        case 2000: // OBJECT
	            return Nature.Object;
	        default:
	            throw new UnsupportedOperationException("Could not identify the value nature of jdbc type: " + jdbcType);
	    }
	}

    /**
     * Sets parameters in a PreparedStatement.
     * Binds an array of parameter values to the statement positions.
     *
     * @param statement the PreparedStatement to set parameters on
     * @param params the parameter values to bind
     * @throws Exception if parameter binding fails
     */
	public static void setParams(PreparedStatement statement, Object[] params) throws Exception {
	    for (int i = 0; i < params.length; i++) {
	        statement.setObject(i + 1, params[i]);
	    }
	}

    /**
     * Prints all rows and columns from a ResultSet to standard output.
     * Each row is printed on a new line with columns separated by " | ".
     *
     * @param results the ResultSet to print
     * @throws Exception if reading from the ResultSet fails
     */
	public static void printAllValues(ResultSet results) throws Exception {
	    WizBased.printAllValues(results, System.out);
	}

    /**
     * Prints all rows and columns from a ResultSet to the specified PrintStream.
     * Each row is printed on a new line with columns separated by " | ".
     *
     * @param results the ResultSet to print
     * @param out the PrintStream to write output to
     * @throws Exception if reading from the ResultSet fails
     */
	public static void printAllValues(ResultSet results, PrintStream out) throws Exception {
	    var count = results.getMetaData().getColumnCount();
	    while (results.next()) {
	        var line = new StringBuilder();
	        for (int i = 1; i <= count; i++) {
	            if (i > 1) {
	                line.append(" | ");
	            }
	            line.append(String.valueOf(results.getObject(i)));
	        }    
	        out.println(line.toString());
	    }
	}

    /**
     * Prints column names and their Nature types from a ResultSet to standard output.
     * Format: [index] columnName : nature
     *
     * @param results the ResultSet to extract column information from
     * @throws Exception if reading from the ResultSet fails
     */
	public static void printColumnsNamesAndNatures(ResultSet results) throws Exception {
	    WizBased.printColumnsNamesAndNatures(results, System.out);
	}

    /**
     * Prints column names and their Nature types to the specified PrintStream.
     * Format: [index] columnName : nature
     *
     * @param results the ResultSet to extract column information from
     * @param out the PrintStream to write output to
     * @throws Exception if reading from the ResultSet fails
     */
	public static void printColumnsNamesAndNatures(ResultSet results, PrintStream out) throws Exception {
	    var meta = results.getMetaData();
	    for (int i = 1; i <= meta.getColumnCount(); i++) {
	        out.println(String.format("[%d] %s : %s",
	                i, meta.getColumnName(i), String.valueOf(getNatureOfSQL(meta.getColumnType(i)))));
	    }
	}

    /**
     * Closes an AutoCloseable resource, suppressing any exceptions that occur.
     * Safely closes connections, statements, result sets, and other resources.
     *
     * @param closeable the resource to close (can be null)
     */
	public static void closeQuietly(AutoCloseable closeable) {
	    if (closeable != null) {
	        try {
	            closeable.close();
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	}

    /**
     * Closes a connection, statement, and result set in order, suppressing any exceptions.
     * Useful for cleanup in finally blocks or try-with-resources alternatives.
     *
     * @param conn the connection to close
     * @param stmt the statement to close
     * @param rs the result set to close
     */
	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
	    closeQuietly(rs);
	    closeQuietly(stmt);
	    closeQuietly(conn);
	}

    /**
     * Rolls back a database transaction, suppressing any exceptions that occur.
     * Useful for error handling in database operations.
     *
     * @param conn the connection to rollback (can be null)
     */
	public static void rollbackQuietly(Connection conn) {
	    if (conn != null) {
	        try {
	            conn.rollback();
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	}

    /**
     * Commits a database transaction, suppressing any exceptions that occur.
     * Useful for error handling in database operations.
     *
     * @param conn the connection to commit (can be null)
     */
	public static void commitQuietly(Connection conn) {
	    if (conn != null) {
	        try {
	            conn.commit();
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	}

    /**
     * Checks if a table exists in the database.
     *
     * @param conn the database connection
     * @param catalog the catalog name (can be null)
     * @param schema the schema name (can be null)
     * @param tableName the table name
     * @return true if the table exists; false otherwise
     * @throws Exception if database metadata queries fail
     */
	public static boolean tableExists(Connection conn, String catalog, String schema, String tableName) throws Exception {
	    var meta = conn.getMetaData();
	    try (ResultSet rs = meta.getTables(catalog, schema, tableName, null)) {
	        return rs.next();
	    }
	}

    /**
     * Executes an update, insert, or delete SQL statement.
     * Binds parameters and executes the prepared statement.
     *
     * @param conn the database connection
     * @param sql the SQL statement to execute
     * @param params the parameter values to bind (can be null)
     * @return the number of rows affected
     * @throws Exception if database operations fail
     */
	public static int executeUpdate(Connection conn, String sql, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        return stmt.executeUpdate();
	    }
	}

    /**
     * Executes an insert statement and retrieves the auto-generated key.
     * Useful for getting primary keys after insertion.
     *
     * @param conn the database connection
     * @param sql the SQL insert statement
     * @param params the parameter values to bind (can be null)
     * @return the generated key value, or null if no key was generated
     * @throws Exception if database operations fail
     */
	public static Object executeInsertAndGetGeneratedKey(Connection conn, String sql, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        int affected = stmt.executeUpdate();
	        if (affected > 0) {
	            try (ResultSet rs = stmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    return rs.getObject(1);
	                }
	            }
	        }
	        return null;
	    }
	}

    /**
     * Executes a batch of parameterized SQL statements.
     * More efficient than executing statements individually.
     *
     * @param conn the database connection
     * @param sql the SQL statement template
     * @param paramsList a list of parameter arrays, one per statement execution
     * @return an array of update counts for each statement
     * @throws Exception if database operations fail
     */
	public static int[] executeBatch(Connection conn, String sql, List<Object[]> paramsList) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (paramsList != null) {
	            for (Object[] params : paramsList) {
	                if (params != null) {
	                    setParams(stmt, params);
	                }
	                stmt.addBatch();
	            }
	        }
	        return stmt.executeBatch();
	    }
	}

    /**
     * Queries the database and maps all result rows to a list of objects.
     * Uses reflection and constructors to map ResultSet rows to the target class.
     *
     * @param <T> the target class type
     * @param conn the database connection
     * @param sql the SQL query statement
     * @param onClass the class to map each row to
     * @param params the query parameter values (can be null)
     * @return a list of mapped objects, one per result row
     * @throws Exception if database operations or mapping fails
     */
	public static <T> List<T> queryForList(Connection conn, String sql, Class<T> onClass, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        try (ResultSet rs = stmt.executeQuery()) {
	            return mapResults(rs, onClass);
	        }
	    }
	}

    /**
     * Queries the database and maps the first result row to an object.
     * Returns null if no rows are found.
     *
     * @param <T> the target class type
     * @param conn the database connection
     * @param sql the SQL query statement
     * @param onClass the class to map the first row to
     * @param params the query parameter values (can be null)
     * @return the mapped object from the first result row, or null if no results
     * @throws Exception if database operations or mapping fails
     */
	public static <T> T queryForObject(Connection conn, String sql, Class<T> onClass, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return mapResult(rs, onClass);
	            }
	            return null;
	        }
	    }
	}

}
