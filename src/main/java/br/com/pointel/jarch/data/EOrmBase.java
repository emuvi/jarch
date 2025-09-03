package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.pointel.jarch.flow.Base36;
import br.com.pointel.jarch.mage.WizString;
import br.com.pointel.jarch.mage.WizData;

public class EOrmBase extends EOrm {

    private static final Logger log = LoggerFactory.getLogger(EOrmBase.class);

    public EOrmBase(Connection link) {
        super(link);
    }

    @Override
    public Heads getHeads() throws Exception {
        var meta = getLink().getMetaData();
        var set = meta.getTables(null, null, "%", new String[] {"TABLE"});
        var result = new Heads();
        while (set.next()) {
            result.add(new TableHead(set.getString(1), set.getString(2), set.getString(3)));
        }
        return result;
    }

    @Override
    public void create(Table table, boolean ifNotExists) throws Exception {
        var builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        if (ifNotExists) {
            builder.append("IF NOT EXISTS ");
        }
        builder.append(table.getCatalogSchemaName());
        builder.append(" (");
        var primaryKeyFromFields = new ArrayList<String>();
        for (var i = 0; i < table.fieldList.size(); i++) {
            var field = table.fieldList.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(makeNature(field));
            if (Boolean.TRUE.equals(field.keyPrimary)) {
                primaryKeyFromFields.add(field.name);
            }
        }
        if (table.keyPrimaryList != null) {
            for (var primaryKey : table.keyPrimaryList) {
                builder.append(", PRIMARY KEY ");
                if (primaryKey.name != null && !primaryKey.name.isEmpty()) {
                    builder.append(primaryKey.name);
                }
                builder.append(" ( ");
                var equalsToPrimaryKeyOnFields = true;
                for (var i = 0; i < primaryKey.columnList.size(); i++) {
                    var column = primaryKey.columnList.get(i);
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(column.name);
                    if (equalsToPrimaryKeyOnFields && i < primaryKeyFromFields.size()) {
                        if (!Objects.equals(column.name, primaryKeyFromFields.get(i))) {
                            equalsToPrimaryKeyOnFields = false;
                        }
                    } else {
                        equalsToPrimaryKeyOnFields = false;
                    }
                }
                if (equalsToPrimaryKeyOnFields && primaryKeyFromFields.size() == primaryKey.columnList.size()) {
                    primaryKeyFromFields.clear();
                }
                builder.append(" ) ");
            }
        }
        if (!primaryKeyFromFields.isEmpty()) {
            builder.append(", PRIMARY KEY (");
            for (int i = 0; i < primaryKeyFromFields.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(primaryKeyFromFields.get(i));
            }
            builder.append(")");
        }
        if (table.keyForeignList != null) {
            for (var foreignKey : table.keyForeignList) {
                builder.append(", FOREIGN KEY ");
                if (foreignKey.inName != null && !foreignKey.inName.isEmpty()) {
                    builder.append(foreignKey.inName);
                }
                builder.append(" ( ");
                for (var i = 0; i < foreignKey.matchList.size(); i++) {
                    var match = foreignKey.matchList.get(i);
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(match.inColumn);
                }
                builder.append(" ) ");
                builder.append(" REFERENCES ");
                builder.append(foreignKey.outTableHead.getCatalogSchemaName());
                builder.append(" ( ");
                for (var i = 0; i < foreignKey.matchList.size(); i++) {
                    var match = foreignKey.matchList.get(i);
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(match.outColumn);
                }
                builder.append(" ) ");
            }
        }
        builder.append(")");
        final String sql = builder.toString();
        log.debug("Creating table with SQL: {}", sql);
        try (var stmt = getLink().createStatement()) {
            stmt.execute(sql);
            log.info("Table created successfully: {}", table.tableHead.getCatalogSchemaName());
        }
    }

    @Override
    public void create(Index index, boolean ifNotExists) throws Exception {
        if (index.tableHead == null || index.tableHead.name == null || index.tableHead.name.isEmpty()) {
            throw new Exception("Could not create index because: tableHead not defined");
        }
        if (index.fieldList == null || index.fieldList.isEmpty()) {
            throw new Exception("Could not create index because: fieldList not defined");
        }
        var builder = new StringBuilder();
        builder.append("CREATE INDEX ");
        if (ifNotExists) {
            builder.append("IF NOT EXISTS ");
        }
        if (index.name != null && !index.name.isEmpty()) {
            builder.append(index.name);
        } else {
            builder.append("idx_").append(index.tableHead.name);
            for (var field : index.fieldList) {
                builder.append("_").append(field.name);
            }
        }
        builder.append(" ON ");
        builder.append(index.tableHead.getCatalogSchemaName());
        builder.append(" (");
        for (var i = 0; i < index.fieldList.size(); i++) {
            var field = index.fieldList.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(field.name);
        }
        builder.append(")");
        final String sql = builder.toString();
        log.debug("Creating index with SQL: {}", sql);
        try (var stmt = getLink().createStatement()) {
            stmt.execute(sql);
            log.info("Index created successfully: {}", index.name);
        }
    }

    @Override
    public ResultSet select(Select select, Strain strain) throws Exception {
        var builder = new StringBuilder("SELECT ");
        var fromSource = select.tableHead.getCatalogSchemaName();
        var dataSource = select.tableHead.alias != null
                        && !select.tableHead.alias.isEmpty()
                            ? select.tableHead.alias
                            : fromSource;
        if (select.fieldList == null || select.fieldList.isEmpty()) {
            builder.append("*");
        } else {
            for (var i = 0; i < select.fieldList.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                if (!select.fieldList.get(i).name.contains(".")) {
                    builder.append(dataSource);
                    builder.append(".");
                }
                builder.append(select.fieldList.get(i).name);
            }
        }
        builder.append(" FROM ");
        builder.append(fromSource);
        if (select.tableHead.alias != null && !select.tableHead.alias
                        .isEmpty()) {
            builder.append(" AS ");
            builder.append(select.tableHead.alias);
        }
        if (select.hasJoins()) {
            for (var join : select.joinList) {
                if (join.ties != null) {
                    builder.append(" ");
                    builder.append(join.ties.toString());
                    builder.append(" ");
                }
                builder.append(" JOIN ");
                var withSource = join.tableHead.getCatalogSchemaName();
                var withAlias = withSource;
                builder.append(withSource);
                if (join.alias != null) {
                    builder.append(" AS ");
                    withAlias = join.alias;
                    builder.append(withAlias);
                } else if (join.tableHead.alias != null) {
                    builder.append(" AS ");
                    withAlias = join.tableHead.alias;
                    builder.append(withAlias);
                }
                if (join.hasFilters()) {
                    builder.append(" ON ");
                    builder.append(makeClauses(join.filterList, dataSource, withAlias));
                }
            }
        }
        if (select.hasFilters()) {
            builder.append(" WHERE ");
            builder.append(makeClauses(select.filterList, dataSource, null));
        }
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(!select.hasFilters() ? " WHERE " : " AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        if (select.orderList != null && !select.orderList.isEmpty()) {
            builder.append(" ORDER BY ");
            for (var i = 0; i < select.orderList.size(); i++) {
                if (i > 0) {
                    builder.append(" , ");
                }
                var order = select.orderList.get(i);
                builder.append(order.name);
                if (order.desc != null && order.desc) {
                    builder.append(" DESC");
                }
            }
        }
        if (select.limit != null) {
            builder.append(" LIMIT ");
            builder.append(select.limit);
        }
        if (select.offset != null) {
            builder.append(" OFFSET ");
            builder.append(select.offset);
        }
        var selectSQL = builder.toString();
        log.debug("Selecting with SQL: {}", selectSQL);
        var prepared = getLink().prepareStatement(selectSQL);
        var param_index = 1;
        if (select.hasJoins()) {
            for (var join : select.joinList) {
                if (join.hasFilters()) {
                    for (var clause : join.filterList) {
                        if (clause.valued != null && clause.valued.value != null) {
                            setParameter(prepared, param_index, clause.valued);
                            param_index++;
                        }
                    }
                }
            }
        }
        if (select.hasFilters()) {
            for (var clause : select.filterList) {
                if (clause.valued != null && clause.valued.value != null) {
                    setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }
        return prepared.executeQuery();
    }

    @Override
    public String insert(Insert insert, Strain strain) throws Exception {
        var ID = getID(getLink(), insert);
        var strained = new ArrayList<Pair<String, String>>();
        if (strain != null && strain.include != null && !strain.include.isEmpty()) {
            var includes = strain.include.split("\\|");
            for (var element : includes) {
                if (!element.isEmpty() && element.contains("=")) {
                    var parts = element.split("\\=");
                    strained.add(Pair.of(parts[0].trim(), parts[1].trim()));
                }
            }
        }
        var builder = new StringBuilder("INSERT INTO ");
        builder.append(insert.tableHead.getCatalogSchemaName());
        builder.append(" (");
        for (var i = 0; i < insert.valuedList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(insert.valuedList.get(i).name);
        }
        if (!strained.isEmpty()) {
            for (var toStrain : strained) {
                builder.append(", ");
                builder.append(toStrain.getLeft());
            }
        }
        builder.append(") VALUES (");
        for (var i = 0; i < insert.valuedList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            final var valued = insert.valuedList.get(i);
            if (valued.value != null) {
                builder.append("?");
            } else {
                builder.append("NULL");
            }
        }
        if (!strained.isEmpty()) {
            builder.append(", ");
            for (var toStrain : strained) {
                if (!toStrain.getRight().isEmpty()) {
                    builder.append("?");
                } else {
                    builder.append("NULL");
                }
            }
        }
        builder.append(")");
        var insertSQL = builder.toString();
        log.debug("Inserting with SQL: {}", insertSQL);
        var prepared = getLink().prepareStatement(insertSQL);
        var param_index = 1;
        for (var valued : insert.valuedList) {
            if (valued.value != null) {
                setParameter(prepared, param_index, valued);
                param_index++;
            }
        }
        if (!strained.isEmpty()) {
            for (var toStrain : strained) {
                if (!toStrain.getRight().isEmpty()) {
                    setParameter(prepared, param_index, new Valued(toStrain.getLeft(), toStrain.getRight()));
                    param_index++;
                }
            }
        }
        prepared.executeUpdate();
        return ID;
    }

    @Override
    public Integer update(Update update, Strain strain) throws Exception {
        var builder = new StringBuilder("UPDATE ");
        var dataSource = update.tableHead.getCatalogSchemaName();
        builder.append(dataSource);
        builder.append(" SET ");
        for (var i = 0; i < update.valuedList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(update.valuedList.get(i).name);
            builder.append(" = ");
            if (update.valuedList.get(i).value == null) {
                builder.append("NULL");
            } else {
                builder.append("?");
            }
        }
        if (strain != null && strain.modify != null && !strain.modify.isEmpty()) {
            builder.append(", ");
            builder.append(strain.modify);
        }
        builder.append(" WHERE ");
        builder.append(makeClauses(update.filterList, null, null));
        if (update.limit != null) {
            builder.append(" LIMIT ");
            builder.append(update.limit);
        }
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(" AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        var updateSQL = builder.toString();
        log.debug("Updating with SQL: {}", updateSQL);
        var prepared = getLink().prepareStatement(updateSQL);
        var param_index = 1;
        for (var valued : update.valuedList) {
            if (valued != null) {
                setParameter(prepared, param_index, valued);
                param_index++;
            }
        }
        if (update.filterList != null && !update.filterList.isEmpty()) {
            for (var clause : update.filterList) {
                if (clause.valued != null) {
                    setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }
        return prepared.executeUpdate();
    }

    @Override
    public Integer delete(Delete delete, Strain strain) throws Exception {
        var builder = new StringBuilder("DELETE FROM ");
        var dataSource = delete.tableHead.getCatalogSchemaName();
        builder.append(dataSource);
        builder.append(" WHERE ");
        builder.append(makeClauses(delete.filterList, null, null));
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(" AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        var deleteSQL = builder.toString();
        log.debug("Deleting with SQL: {}", deleteSQL);
        var prepared = getLink().prepareStatement(deleteSQL);
        var param_index = 1;
        if (delete.filterList != null && !delete.filterList.isEmpty()) {
            for (var clause : delete.filterList) {
                if (clause.valued.value != null) {
                    setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }
        return prepared.executeUpdate();
    }

    @Override
    public boolean isPrimaryKeyError(Exception error) {
        return error.getMessage().contains("unique constraint");
    }

    protected String replaceVariables(String onSource, String dataSource) {
        if (onSource == null) {
            return null;
        }
        return onSource.replace("${dataSource}", dataSource);
    }

    protected void putID(Insert insert, Object next) {
        for (var valued : insert.valuedList) {
            if (Objects.equals(insert.toGetID.name, valued.name)) {
                valued.value = next;
                break;
            }
        }
    }

    protected String getID(Connection link, Insert insert) throws Exception {
        if (insert.toGetID == null || insert.toGetID.name == null || insert.toGetID.name
                        .isEmpty()) {
            return "";
        }
        var format = getIDFormat(link, insert);
        if (format == null || format.isEmpty()) {
            throw new Exception(
                            "Could not get the ID because: format not found for the table "
                                            + insert.tableHead.name);
        }
        var formatParts = format.split(";");
        if (formatParts.length < 2) {
            throw new Exception("Could not get the ID because: format mal formed");
        }
        var formatType = formatParts[0];
        var formatSize = Integer.parseInt(formatParts[1]);
        switch (formatType) {
            case "MX":
                return getIDMX(link, insert, formatSize);
            case "CX":
                return getIDCX(link, insert, formatSize);
            case "NS":
                return getIDNS(link, insert, formatSize);
            case "CS":
                return getIDCS(link, insert, formatSize);
            default:
                throw new Exception(
                                "Could not get the ID because: could not identify the format type");
        }
    }

    protected String getIDFormat(Connection link, Insert insert) throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT formato FROM codigos WHERE tabela = '"
                                        + insert.tableHead.name + "'");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    protected String getIDMX(Connection link, Insert insert, int formatSize)
                    throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT MAX(" + insert.toGetID.name + ") FROM "
                                        + insert.tableHead.name + " WHERE "
                                        + insert.toGetID.filter.name + " = '"
                                        + insert.toGetID.filter.value.toString() + "'");
        String last = null;
        if (rst.next()) {
            last = rst.getString(1);
        }
        if (last == null || last.isEmpty()) {
            last = WizString.fillAtStart("", '0', formatSize);
        }
        String next = WizString.getNext(last, true);
        putID(insert, next);
        return next;
    }

    protected String getIDCX(Connection link, Insert insert, int formatSize)
                    throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT MAX(" + insert.toGetID.name + ") FROM "
                                        + insert.tableHead.name + " WHERE "
                                        + insert.toGetID.filter.name + " = '"
                                        + insert.toGetID.filter.value.toString() + "'");
        String last = null;
        if (rst.next()) {
            last = rst.getString(1);
        }
        if (last == null || last.isEmpty()) {
            last = WizString.fillAtStart("", '0', formatSize);
        }
        String next = WizString.getNext(last, false);
        putID(insert, next);
        return next;
    }

    protected String getIDNS(Connection link, Insert insert, int formatSize)
                    throws Exception {
        String sequence = getIDSequence(link, insert);
        var rst = link.createStatement().executeQuery("SELECT nextval('" + sequence
                        + "')");
        Long nextVal = null;
        if (rst.next()) {
            nextVal = rst.getLong(1);
        }
        if (nextVal == null) {
            nextVal = 1l;
        }
        var next = nextVal.toString();
        next = WizString.fillAtStart(next, '0', formatSize);
        putID(insert, next);
        return next;
    }

    protected String getIDCS(Connection link, Insert insert, int formatSize)
                    throws Exception {
        String sequence = getIDSequence(link, insert);
        var rst = link.createStatement().executeQuery("SELECT nextval('" + sequence
                        + "')");
        Long nextVal = null;
        if (rst.next()) {
            nextVal = rst.getLong(1);
        }
        if (nextVal == null) {
            nextVal = 1l;
        }
        var next = Base36.fromBase10(nextVal);
        next = WizString.fillAtStart(next, '0', formatSize);
        putID(insert, next);
        return next;
    }

    protected String getIDSequence(Connection link, Insert insert) throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT sequencia FROM codigos WHERE tabela = '"
                                        + insert.tableHead.name + "'");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    protected String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case Bool:
                builder.append(" BOOLEAN");
                break;
            case Bit:
                builder.append(" BIT");
                break;
            case Byte:
                builder.append(" BYTE");
                break;
            case Tiny:
                builder.append(" TINY");
                break;
            case Small:
                builder.append(" SMALLINT");
                break;
            case Int:
                builder.append(" INTEGER");
                break;
            case Serial:
                builder.append(" SERIAL");
                break;
            case Long:
                builder.append(" LONG");
                break;
            case BigSerial:
                builder.append(" BIG_SERIAL");
                break;
            case Float:
                builder.append(" FLOAT");
                break;
            case Real:
                builder.append(" REAL");
                break;
            case Double:
                builder.append(" DOUBLE");
                break;
            case Numeric:
                builder.append(" NUMERIC");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    if (field.precision != null) {
                        builder.append(",");
                        builder.append(field.precision);
                    }
                    builder.append(")");
                }
                break;
            case BigNumeric:
                builder.append(" BIG_NUMERIC");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    if (field.precision != null) {
                        builder.append(",");
                        builder.append(field.precision);
                    }
                    builder.append(")");
                }
                break;
            case Char:
                builder.append(" CHAR(1)");
                break;
            case Chars:
                builder.append(" VARCHAR");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case Date:
                builder.append(" DATE");
                break;
            case Time:
                builder.append(" TIME");
                break;
            case DateTime:
                builder.append(" DATETIME");
                break;
            case Timestamp:
                builder.append(" TIMESTAMP");
                break;
            case Bytes, Blob:
                builder.append(" BLOB");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case Text:
                builder.append(" TEXT");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case Object:
                builder.append(" OBJECT");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (Boolean.TRUE.equals(field.notNull)) {
            builder.append(" NOT NULL");
        }
        return builder.toString();
    }

    protected String makeClauses(List<Filter> filterList, String fromSource, String withAlias) {
        if ((filterList == null) || filterList.isEmpty()) {
            return "";
        }
        var builder = new StringBuilder();
        var nextIsOr = false;
        for (var i = 0; i < filterList.size(); i++) {
            var clause = filterList.get(i);
            if (clause.valued == null && clause.linked == null) {
                continue;
            }
            if (i > 0) {
                builder.append(nextIsOr ? " OR " : " AND ");
            }
            if (clause.seems == FilterSeems.IsNot) {
                builder.append(" NOT ");
            }
            if (clause.valued != null) {
                if (fromSource != null && !fromSource.isEmpty() && !clause.valued.name.contains(".")) {
                    builder.append(fromSource);
                    builder.append(".");
                }
                builder.append(clause.valued.name);
                if (clause.valued.value == null) {
                    builder.append(" IS NULL ");
                } else {
                    builder.append(makeCondition(clause.likes, "?"));
                }
            } else if (clause.linked != null) {
                if (fromSource != null && !fromSource.isEmpty()) {
                    builder.append(fromSource);
                    builder.append(".");
                }
                builder.append(clause.linked.name);
                if (clause.linked.upon == null) {
                    builder.append(" IS NULL ");
                } else {
                    var formWith = new StringBuilder();
                    if (withAlias != null && !withAlias.isEmpty()) {
                        formWith.append(withAlias);
                        formWith.append(".");
                    }
                    formWith.append(clause.linked.upon);
                    builder.append(makeCondition(clause.likes, formWith.toString()));
                }
            }
            nextIsOr = clause.ties == FilterTies.Or;
        }
        return builder.toString();
    }

    protected String makeCondition(FilterLikes likes, String upon) {
        switch (likes) {
            case Equals: return " = " + upon + " ";
            case Bigger: return " > " + upon + " ";
            case Lesser: return " < " + upon + " ";
            case BiggerOrEquals: return " >= " + upon + " ";
            case LesserOrEquals: return " <= " + upon + " ";
            case StartsWith: return " STARTS WITH " + upon + " ";
            case EndsWith: return " ENDS WITH " + upon + " ";
            case Contains: return " CONTAINS " + upon + " ";
            default: throw new UnsupportedOperationException();
        }
    }

    protected void setParameter(PreparedStatement prepared, int index, Valued valued)
                    throws Exception {
        if (valued.type == null) {
            prepared.setObject(index, valued.value);
        } else {
            switch (valued.type) {
                case Bool:
                    prepared.setBoolean(index, WizData.getOnBoolean(valued.value));
                    break;
                case Bit:
                    prepared.setByte(index, WizData.getOnByte(valued.value));
                    break;
                case Byte:
                    prepared.setByte(index, WizData.getOnByte(valued.value));
                    break;
                case Tiny:
                    prepared.setShort(index, WizData.getOnShort(valued.value));
                    break;
                case Small:
                    prepared.setShort(index, WizData.getOnShort(valued.value));
                    break;
                case Int:
                    prepared.setInt(index, WizData.getOnInteger(valued.value));
                    break;
                case Long:
                    prepared.setLong(index, WizData.getOnLong(valued.value));
                    break;
                case BigInt:
                    prepared.setObject(index, WizData.getOnBigInteger(valued.value));
                    break;
                case Serial:
                    prepared.setInt(index, WizData.getOnInteger(valued.value));
                    break;
                case BigSerial:
                    prepared.setObject(index, WizData.getOnBigInteger(valued.value));
                    break;
                case Float:
                    prepared.setFloat(index, WizData.getOnFloat(valued.value));
                    break;
                case Real:
                    prepared.setDouble(index, WizData.getOnDouble(valued.value));
                    break;
                case Double:
                    prepared.setDouble(index, WizData.getOnDouble(valued.value));
                    break;
                case Numeric:
                    prepared.setBigDecimal(index, WizData.getOnBigDecimal(valued.value));
                    break;
                case BigNumeric:
                    prepared.setBigDecimal(index, WizData.getOnBigDecimal(valued.value));
                    break;
                case Char:
                    prepared.setString(index, String.valueOf(WizData.getOnCharacter(valued.value)));
                    break;
                case Chars:
                    prepared.setString(index, WizData.getOnString(valued.value));
                    break;
                case Date:
                    prepared.setDate(index, WizData.getOnSqlDate(valued.value));
                    break;
                case Time:
                    prepared.setTime(index, WizData.getOnSqlTime(valued.value));
                    break;
                case DateTime:
                    prepared.setTimestamp(index, WizData.getOnSqlTimestamp(valued.value));
                    break;
                case ZoneTime:
                    prepared.setTimestamp(index, WizData.getOnSqlTimestamp(valued.value));
                    break;
                case Timestamp:
                    prepared.setTimestamp(index, WizData.getOnSqlTimestamp(valued.value));
                    break;
                case Bytes:
                    prepared.setBytes(index, WizData.getOnBytes(valued.value));
                    break;
                case Blob:
                    prepared.setBlob(index, WizData.getOnBlob(valued.value));
                    break;
                case Text:
                    prepared.setString(index, WizData.getOnString(valued.value));
                    break;
                case Object:
                    prepared.setObject(index, valued.value);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

}
