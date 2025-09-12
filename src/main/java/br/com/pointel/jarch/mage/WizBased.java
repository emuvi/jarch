package br.com.pointel.jarch.mage;

import java.io.PrintStream;
import java.nio.file.DirectoryStream.Filter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import br.com.pointel.jarch.data.Delete;
import br.com.pointel.jarch.data.Field;
import br.com.pointel.jarch.data.Insert;
import br.com.pointel.jarch.data.KeyForeign;
import br.com.pointel.jarch.data.KeyPrimary;
import br.com.pointel.jarch.data.Match;
import br.com.pointel.jarch.data.Nature;
import br.com.pointel.jarch.data.OrdName;
import br.com.pointel.jarch.data.Select;
import br.com.pointel.jarch.data.Table;
import br.com.pointel.jarch.data.TableHead;
import br.com.pointel.jarch.data.Typed;
import br.com.pointel.jarch.data.Update;

public class WizBased {

    private WizBased() {
    }

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

    public static <T> List<T> mapResults(ResultSet result, Class<T> onClass) throws Exception {
        return WizBased.mapResults(result, null, onClass);
    }

    public static <T> List<T> mapResults(ResultSet result, List<Typed> fieldList, Class<T> onClass) throws Exception {
        var results = new ArrayList<T>();
        while (result.next()) {
            var mapped = WizBased.mapResult(result, fieldList, onClass);
            results.add(mapped);
        }
        return results;
    }

    public static <T> T mapResult(ResultSet result, Class<T> onClass) throws Exception {
        return WizBased.mapResult(result, null, onClass);
    }

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
	    var fieldList = new ArrayList<br.com.pointel.jarch.data.Field>();
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
	        final var field = new br.com.pointel.jarch.data.Field(fieldOrd, fieldName, fieldNature, fieldSize, fieldPrecision, fieldNotNull, fieldAutoIncrement, fieldAutoGenerated, fieldKeyPrimary);
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

	public static String[] getColumnsNames(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var names = new String[meta.getColumnCount()];
	    for (int i = 1; i <= names.length; i++) {
	        names[(i - 1)] = meta.getColumnName(i);
	    }
	    return names;
	}

	public static Class<?>[] getColumnsClasses(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var classes = new Class<?>[meta.getColumnCount()];
	    for (int i = 1; i <= classes.length; i++) {
	        var className = meta.getColumnClassName(i);
	        classes[(i - 1)] = Class.forName(className);
	    }
	    return classes;
	}

	public static Object[] getColumnsValues(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var values = new Object[meta.getColumnCount()];
	    for (int i = 1; i <= values.length; i++) {
	        values[(i - 1)] = results.getObject(i);
	    }
	    return values;
	}

	public static Nature[] getNaturesFrom(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var natures = new Nature[meta.getColumnCount()];
	    for (int i = 1; i <= natures.length; i++) {
	        natures[(i - 1)] = WizBased.getNatureOfSQL(meta.getColumnType(i));
	    }
	    return natures;
	}

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

	public static void setParams(PreparedStatement statement, Object[] params) throws Exception {
	    for (int i = 0; i < params.length; i++) {
	        statement.setObject(i + 1, params[i]);
	    }
	}

	public static void printAllValues(ResultSet results) throws Exception {
	    WizBased.printAllValues(results, System.out);
	}

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

	public static void printColumnsNamesAndNatures(ResultSet results) throws Exception {
	    WizBased.printColumnsNamesAndNatures(results, System.out);
	}

	public static void printColumnsNamesAndNatures(ResultSet results, PrintStream out) throws Exception {
	    var meta = results.getMetaData();
	    for (int i = 1; i <= meta.getColumnCount(); i++) {
	        out.println(String.format("[%d] %s : %s",
	                i, meta.getColumnName(i), String.valueOf(getNatureOfSQL(meta.getColumnType(i)))));
	    }
	}

}
