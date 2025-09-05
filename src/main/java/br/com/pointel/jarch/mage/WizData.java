package br.com.pointel.jarch.mage;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.data.Field;
import br.com.pointel.jarch.data.KeyForeign;
import br.com.pointel.jarch.data.KeyPrimary;
import br.com.pointel.jarch.data.Match;
import br.com.pointel.jarch.data.Nature;
import br.com.pointel.jarch.data.OrdName;
import br.com.pointel.jarch.data.Table;
import br.com.pointel.jarch.data.TableHead;
import br.com.pointel.jarch.data.Typed;

public class WizData {

    private static final Gson gson = new Gson();

    private WizData() {
    }

    public static String toChars(Object value) {
        return gson.toJson(value);
    }

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return gson.fromJson(chars, clazz);
    }

    public static <T> List<T> mapResults(ResultSet result, List<Typed> fieldList, Class<T> onClazz) throws Exception {
        var results = new ArrayList<T>();
        while (result.next()) {
            var mapped = mapResult(result, fieldList, onClazz);
            results.add(mapped);
        }
        return results;
    }

    public static <T> T mapResult(ResultSet result, List<Typed> fieldList, Class<T> onClazz) throws Exception {
        var colsClasses = WizData.getColumnsClasses(result);
        var colsValues = WizData.getColumnsValues(result);
        try {
            var standardOne = onClazz.getConstructor(colsClasses);
            return standardOne.newInstance(colsValues);
        } catch (NoSuchMethodException e) {
            Nature[] colsNatures;
            if (fieldList == null || fieldList.isEmpty()) {
                colsNatures = WizData.getNaturesFrom(result);
            } else {
                colsNatures = fieldList.stream().map(f -> f.type).toArray(Nature[]::new);
            }
            var colsNames = WizData.getColumnsNames(result);
            var bestOne = WizLang.getBestConstructor(onClazz, colsNatures, colsNames);
            if (bestOne == null) {
                bestOne = onClazz.getConstructor();
            }
            Object[] argsValues = new Object[bestOne.getParameterCount()];
            Class<?>[] argsTypes = bestOne.getParameterTypes();
            for (int i = 0; i < argsValues.length; i++) {
                argsValues[i] = WizData.getOn(colsValues[i], argsTypes[i]);
            }
            var instance = onClazz.cast(bestOne.newInstance(argsValues));
            if (argsValues.length < colsValues.length) {
                var columnsNames = WizData.getColumnsNames(result);
                for (int i = argsValues.length; i < colsValues.length; i++) {
                    var columnName = columnsNames[i];
                    var field = onClazz.getDeclaredField(columnName);
                    var onValue = WizData.getOn(colsValues[i], field.getType());
                    WizLang.forceSetField(field, instance, onValue);
                }
            }
            return instance;
        }
    }

    public static Table getTable(TableHead tableHead, Connection connection) throws Exception {
        var fieldList = new ArrayList<Field>();
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
            final var fieldNature = WizData.getNatureOfSQL(columnsRS.getInt("DATA_TYPE"));
            final var fieldSize = columnsRS.getInt("COLUMN_SIZE");
            final var fieldPrecision = columnsRS.getInt("DECIMAL_DIGITS");
            final var fieldNotNull = "NO".equals(columnsRS.getString("IS_NULLABLE"));
            final var fieldAutoIncrement = !"NO".equals(columnsRS.getString("IS_AUTOINCREMENT"));
            final var fieldAutoGenerated = !"NO".equals(columnsRS.getString("IS_GENERATEDCOLUMN"));
            final var fieldKeyPrimary = keyPrimaryList.stream().anyMatch(k -> k.columnList.stream().anyMatch(c -> Objects.equals(c.name, fieldName)));
            final var field = new Field(fieldOrd, fieldName, fieldNature, fieldSize, fieldPrecision, fieldNotNull, fieldAutoIncrement, fieldAutoGenerated, fieldKeyPrimary);
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

    public static void printColumnsNamesAndNatures(ResultSet results) throws Exception {
        printColumnsNamesAndNatures(results, System.out);
    }

    public static void printColumnsNamesAndNatures(ResultSet results, PrintStream out) throws Exception {
        var meta = results.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            out.println(String.format("[%d] %s : %s",
                    i, meta.getColumnName(i), String.valueOf(WizData.getNatureOfSQL(meta.getColumnType(i)))));
        }
    }

    public static void printAllValues(ResultSet results) throws Exception {
        printAllValues(results, System.out);
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

    public static void setParams(PreparedStatement statement, Object[] params) throws Exception {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    public static Nature[] getNaturesFrom(ResultSet results) throws Exception {
        var meta = results.getMetaData();
        var natures = new Nature[meta.getColumnCount()];
        for (int i = 1; i <= natures.length; i++) {
            natures[(i - 1)] = WizData.getNatureOfSQL(meta.getColumnType(i));
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

    public static String toFormatted(Nature nature, Object value) throws Exception {
        if (value == null) return "";
        switch (nature) {
            case Bool: return WizBoolean.format(WizBoolean.get(value));
            case Bit: return WizByte.format(WizByte.get(value));
            case Byte: return WizByte.format(WizByte.get(value));
            case Tiny: return WizShort.format(WizShort.get(value));
            case Small: return WizShort.format(WizShort.get(value));
            case Int: return WizInteger.format(WizInteger.get(value));
            case Long: return WizLong.format(WizLong.get(value));
            case BigInt: return WizBigInteger.format(WizBigInteger.get(value));
            case Serial: return WizInteger.format(WizInteger.get(value));
            case BigSerial: return WizBigInteger.format(WizBigInteger.get(value));
            case Float: return WizFloat.format(WizFloat.get(value));
            case Real: return WizDouble.format(WizDouble.get(value));
            case Double: return WizDouble.format(WizDouble.get(value));
            case Numeric: return WizBigDecimal.format(WizBigDecimal.get(value));
            case BigNumeric: return WizBigDecimal.format(WizBigDecimal.get(value));
            case Char: return WizCharacter.format(WizCharacter.get(value));
            case Chars: return WizString.get(value);
            case Date: return WizLocalDate.format(WizLocalDate.get(value));
            case Time: return WizLocalTime.format(WizLocalTime.get(value));
            case DateTime: return WizLocalDateTime.format(WizLocalDateTime.get(value));
            case ZoneTime: return WizZonedDateTime.format(WizZonedDateTime.get(value));
            case Timestamp: return WizInstant.format(WizInstant.get(value));
            case Bytes: return WizBytes.format(WizBytes.get(value));
            case Blob: return WizBytes.format(WizBytes.get(value));
            case Text: return WizString.get(value);
            case Object: return WizObject.format(value);
        }
        throw new Exception("Nature " + nature + " not supported for value class: " + value.getClass().getName());
    }

    public static <T> T fromFormatted(Nature nature, String formatted, Class<T> onClass) throws Exception {
        return getOn(fromFormatted(nature, formatted), onClass);
    }

    public static Object fromFormatted(Nature nature, String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        switch (nature) {
            case Bool: return WizBoolean.get(formatted);
            case Bit: return WizByte.get(formatted);
            case Byte: return WizByte.get(formatted);
            case Tiny: return WizShort.get(formatted);
            case Small: return WizShort.get(formatted);
            case Int: return WizInteger.get(formatted);
            case Long: return WizLong.get(formatted);
            case BigInt: return WizBigInteger.get(formatted);
            case Serial: return WizInteger.get(formatted);
            case BigSerial: return WizBigInteger.get(formatted);
            case Float: return WizFloat.get(formatted);
            case Real: return WizDouble.get(formatted);
            case Double: return WizDouble.get(formatted);
            case Numeric: return WizBigDecimal.get(formatted);
            case BigNumeric: return WizBigDecimal.get(formatted);
            case Char: return WizCharacter.get(formatted);
            case Chars: return WizString.get(formatted);
            case Date: return WizLocalDate.get(formatted);
            case Time: return WizLocalTime.get(formatted);
            case DateTime: return WizLocalDateTime.get(formatted);
            case ZoneTime: return WizZonedDateTime.get(formatted);
            case Timestamp: return WizInstant.get(formatted);
            case Bytes: return WizBytes.get(formatted);
            case Blob: return WizBytes.get(formatted);
            case Text: return WizString.get(formatted);
            case Object: return WizObject.get(formatted);
        }
        throw new Exception("Nature " + nature + " not supported for the formatted: " + formatted);
    }

    public static <T> T getOn(Object value, Class<T> onClass) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(onClass, Boolean.class)) {
            return onClass.cast(WizBoolean.get(value));
        }
        if (WizLang.isChildOf(onClass, Byte.class)) {
            return onClass.cast(WizByte.get(value));
        }
        if (WizLang.isChildOf(onClass, Short.class)) {
            return onClass.cast(WizShort.get(value));
        }
        if (WizLang.isChildOf(onClass, Integer.class)) {
            return onClass.cast(WizInteger.get(value));
        }
        if (WizLang.isChildOf(onClass, Long.class)) {
            return onClass.cast(WizLong.get(value));
        }
        if (WizLang.isChildOf(onClass, Float.class)) {
            return onClass.cast(WizFloat.get(value));
        }
        if (WizLang.isChildOf(onClass, Double.class)) {
            return onClass.cast(WizDouble.get(value));
        }
        if (WizLang.isChildOf(onClass, BigInteger.class)) {
            return onClass.cast(WizBigInteger.get(value));
        }
        if (WizLang.isChildOf(onClass, BigDecimal.class)) {
            return onClass.cast(WizBigDecimal.get(value));
        }
        if (WizLang.isChildOf(onClass, Character.class)) {
            return onClass.cast(WizCharacter.get(value));
        }
        if (WizLang.isChildOf(onClass, String.class)) {
            return onClass.cast(WizString.get(value));
        }
        if (WizLang.isChildOf(onClass, Instant.class)) {
            return onClass.cast(WizInstant.get(value));
        }
        if (WizLang.isChildOf(onClass, ZonedDateTime.class)) {
            return onClass.cast(WizZonedDateTime.get(value));
        }
        if (WizLang.isChildOf(onClass, OffsetDateTime.class)) {
            return onClass.cast(WizOffsetDateTime.get(value));
        }
        if (WizLang.isChildOf(onClass, OffsetTime.class)) {
            return onClass.cast(WizOffsetTime.get(value));
        }
        if (WizLang.isChildOf(onClass, LocalDateTime.class)) {
            return onClass.cast(WizLocalDateTime.get(value));
        }
        if (WizLang.isChildOf(onClass, LocalDate.class)) {
            return onClass.cast(WizLocalDate.get(value));
        }
        if (WizLang.isChildOf(onClass, LocalTime.class)) {
            return onClass.cast(WizLocalTime.get(value));
        }
        if (WizLang.isChildOf(onClass, java.util.Date.class)) {
            return onClass.cast(WizUtilDate.get(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Date.class)) {
            return onClass.cast(WizSqlDate.get(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Time.class)) {
            return onClass.cast(WizSqlTime.get(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Timestamp.class)) {
            return onClass.cast(WizSqlTimestamp.get(value));
        }
        if (WizLang.isChildOf(onClass, byte[].class)) {
            return onClass.cast(WizBytes.get(value));
        }
        if (WizLang.isChildOf(onClass, Blob.class)) {
            return onClass.cast(WizBlob.get(value));
        }
        if (WizLang.isChildOf(onClass, Clob.class)) {
            return onClass.cast(WizClob.get(value));
        }
        throw new UnsupportedOperationException("Could not convert to " + onClass.getCanonicalName() + " from class: " + value.getClass().getName());
    }

}
