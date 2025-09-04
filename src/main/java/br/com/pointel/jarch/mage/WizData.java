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
import br.com.pointel.jarch.data.DataClazz;
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

    public static <T> T mapResult(ResultSet result, List<Typed> fieldList, Class<T> onClazz) throws Exception {
        var classes = WizData.getColumnsClasses(result);
        var values = WizData.getColumnsValues(result);
        try {
            var standardOne = onClazz.getConstructor(classes);
            return standardOne.newInstance(values);
        } catch (NoSuchMethodException e) {
            Nature[] natures;
            if (fieldList == null || fieldList.isEmpty()) {
                natures = WizData.getNaturesFrom(result);
            } else {
                natures = fieldList.stream().map(f -> f.type).toArray(Nature[]::new);
            }
            var names = WizData.getColumnsNames(result);
            var bestOne = WizLang.getBestConstructor(onClazz, natures, names);
            if (bestOne == null) {
                bestOne = onClazz.getConstructor();
            }
            Object[] argsValues = new Object[bestOne.getParameterCount()];
            Class<?>[] argsTypes = bestOne.getParameterTypes();
            for (int i = 0; i < argsValues.length; i++) {
                argsValues[i] = WizData.getOn(values[i], argsTypes[i]);
            }
            var instance = onClazz.cast(bestOne.newInstance(argsValues));
            if (argsValues.length < values.length) {
                var columnsNames = WizData.getColumnsNames(result);
                for (int i = argsValues.length; i < values.length; i++) {
                    var columnName = columnsNames[i];
                    var field = onClazz.getDeclaredField(columnName);
                    var mappedField = WizData.getOn(values[i], field.getType());
                    WizLang.forceSetField(field, instance, mappedField);
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

    public static <T> T getOn(Object value, Class<T> onClass) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(onClass, Boolean.class)) {
            return onClass.cast(getOnBoolean(value));
        }
        if (WizLang.isChildOf(onClass, Byte.class)) {
            return onClass.cast(getOnByte(value));
        }
        if (WizLang.isChildOf(onClass, Short.class)) {
            return onClass.cast(getOnShort(value));
        }
        if (WizLang.isChildOf(onClass, Integer.class)) {
            return onClass.cast(getOnInteger(value));
        }
        if (WizLang.isChildOf(onClass, Long.class)) {
            return onClass.cast(getOnLong(value));
        }
        if (WizLang.isChildOf(onClass, Float.class)) {
            return onClass.cast(getOnFloat(value));
        }
        if (WizLang.isChildOf(onClass, Double.class)) {
            return onClass.cast(getOnDouble(value));
        }
        if (WizLang.isChildOf(onClass, BigInteger.class)) {
            return onClass.cast(getOnBigInteger(value));
        }
        if (WizLang.isChildOf(onClass, BigDecimal.class)) {
            return onClass.cast(getOnBigDecimal(value));
        }
        if (WizLang.isChildOf(onClass, Character.class)) {
            return onClass.cast(getOnCharacter(value));
        }
        if (WizLang.isChildOf(onClass, String.class)) {
            return onClass.cast(getOnString(value));
        }
        if (WizLang.isChildOf(onClass, Instant.class)) {
            return onClass.cast(getOnInstant(value));
        }
        if (WizLang.isChildOf(onClass, Long.class)) {
            return onClass.cast(getOnLong(value));
        }
        if (WizLang.isChildOf(onClass, ZonedDateTime.class)) {
            return onClass.cast(getOnZonedDateTime(value));
        }
        if (WizLang.isChildOf(onClass, OffsetDateTime.class)) {
            return onClass.cast(getOnOffsetDateTime(value));
        }
        if (WizLang.isChildOf(onClass, OffsetTime.class)) {
            return onClass.cast(getOnOffsetTime(value));
        }
        if (WizLang.isChildOf(onClass, LocalDateTime.class)) {
            return onClass.cast(getOnLocalDateTime(value));
        }
        if (WizLang.isChildOf(onClass, LocalDate.class)) {
            return onClass.cast(getOnLocalDate(value));
        }
        if (WizLang.isChildOf(onClass, LocalTime.class)) {
            return onClass.cast(getOnLocalTime(value));
        }
        if (WizLang.isChildOf(onClass, java.util.Date.class)) {
            return onClass.cast(getOnUtilDate(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Date.class)) {
            return onClass.cast(getOnSqlDate(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Time.class)) {
            return onClass.cast(getOnSqlTime(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Timestamp.class)) {
            return onClass.cast(getOnSqlTimestamp(value));
        }
        if (WizLang.isChildOf(onClass, byte[].class)) {
            return onClass.cast(getOnBytes(value));
        }
        if (WizLang.isChildOf(onClass, Blob.class)) {
            return onClass.cast(getOnBlob(value));
        }
        if (WizLang.isChildOf(onClass, Clob.class)) {
            return onClass.cast(getOnClob(value));
        }
        throw new UnsupportedOperationException("Could not convert to " + onClass.getCanonicalName() + " from class: " + value.getClass().getName());
    }

    public static Boolean getOnBoolean(Object value) throws Exception {
        return WizBoolean.get(value);
    }

    public static Byte getOnByte(Object value) throws Exception {
        return WizByte.get(value);
    }

    public static Short getOnShort(Object value) throws Exception {
        return WizShort.get(value);
    }

    public static Integer getOnInteger(Object value) throws Exception {
        return WizInteger.get(value);
    }

    public static Long getOnLong(Object value) throws Exception {
        return WizLong.get(value);
    }

    public static Float getOnFloat(Object value) throws Exception {
        return WizFloat.get(value);
    }

    public static Double getOnDouble(Object value) throws Exception {
        return WizDouble.get(value);
    }

    public static BigInteger getOnBigInteger(Object value) throws Exception {
        return WizBigInteger.get(value);
    }

    public static BigDecimal getOnBigDecimal(Object value) throws Exception {
        return WizBigDecimal.get(value);
    }

    public static Character getOnCharacter(Object value) throws Exception {
        return WizCharacter.get(value);
    }

    public static String getOnString(Object value) throws Exception {
        return WizString.get(value);
    }

    public static Instant getOnInstant(Object value) throws Exception {
        return WizInstant.get(value);
    }

    public static ZonedDateTime getOnZonedDateTime(Object value) throws Exception {
        return WizZonedDateTime.get(value);
    }

    public static OffsetDateTime getOnOffsetDateTime(Object value) throws Exception {
        return WizOffsetDateTime.get(value);
    }

    public static OffsetTime getOnOffsetTime(Object value) throws Exception {
        return WizOffsetTime.get(value);
    }

    public static LocalDateTime getOnLocalDateTime(Object value) throws Exception {
        return WizLocalDateTime.get(value);
    }

    public static LocalDate getOnLocalDate(Object value) throws Exception {
        return WizLocalDate.get(value);
    }

    public static LocalTime getOnLocalTime(Object value) throws Exception {
        return WizLocalTime.get(value);
    }

    public static java.util.Date getOnUtilDate(Object value) throws Exception {
        return WizUtilDate.get(value);
    }

    public static java.sql.Date getOnSqlDate(Object value) throws Exception {
        return WizSqlDate.get(value);
    }

    public static java.sql.Time getOnSqlTime(Object value) throws Exception {
        return WizSqlTime.get(value);
    }

    public static java.sql.Timestamp getOnSqlTimestamp(Object value) throws Exception {
        return WizSqlTimestamp.get(value);
    }

    public static byte[] getOnBytes(Object value) throws Exception {
        return WizBytes.get(value);
    }

    public static Blob getOnBlob(Object value) throws Exception {
        return WizBlob.get(value);
    }

    public static Clob getOnClob(Object value) throws Exception {
        return WizClob.get(value);
    }

    public static <T> T getDataFormatted(Nature nature, String formatted, Class<T> castTo) throws Exception {
        return castTo.cast(getDataFormatted(nature, formatted));
    }

    public static Object getDataFormatted(Nature nature, String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        switch (nature) {
            case Bool:
                return Boolean.parseBoolean(formatted);
            case Bit, Byte, Tiny:
                return Byte.parseByte(formatted);
            case Small:
                return Short.parseShort(formatted);
            case Int, Serial:
                return Integer.parseInt(formatted);
            case Long:
                return Long.parseLong(formatted);
            case Float:
            case Real:
                return Float.parseFloat(formatted);
            case Double:
                return Double.parseDouble(formatted);
            case BigInt, BigSerial:
                return new BigInteger(formatted);
            case Numeric, BigNumeric:
                return new BigDecimal(formatted);
            case Char:
                return formatted.charAt(0);
            case Chars, Text:
                return formatted;
            case Date:
                return WizUtilDate.parseDateMach(formatted);
            case Time:
                return WizUtilDate.parseTimeMach(formatted);
            case Timestamp:
                return WizUtilDate.parseTimestampMach(formatted);
            case Bytes:
            case Blob:
                return WizBytes.decodeFromBase64(formatted);
            case Object:
                return DataClazz.fromChars(formatted).getValue();
            default:
                throw new Exception("DataType Not Supported.");
        }
    }

    public static String formatData(Nature nature, Object value) throws Exception {
        if (value == null) {
            return "";
        }
        switch (nature) {
            case Bool:
            case Bit:
            case Byte:
            case Tiny:
            case Small:
            case Int:
            case Long:
            case BigInt:
            case Serial:
            case BigSerial:
            case Float:
            case Real:
            case Double:
            case Numeric:
            case BigNumeric:
            case Char:
            case Chars:
            case Text:
                return String.valueOf(value);
            case Date:
                return WizUtilDate.formatDateMach(WizUtilDate.get(value));
            case Time:
                return WizUtilDate.formatTimeMach(WizUtilDate.get(value));
            case DateTime:
                return WizUtilDate.formatDateTimeMach(WizUtilDate.get(value));
            case Timestamp:
                return WizUtilDate.formatTimestampMach(WizUtilDate.get(value));
            case Bytes:
            case Blob:
                return WizBytes.encodeToBase64(WizBytes.get(value));
            case Object:
                return new DataClazz(value).toChars();
            default:
                throw new Exception("DataType Not Supported.");
        }
    }

}
