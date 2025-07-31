package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;
import br.com.pointel.jarch.flow.Base36;
import br.com.pointel.jarch.mage.WizChars;
import br.com.pointel.jarch.mage.WizData;

public class EOrmAll extends EOrm {

    public EOrmAll(Connection link) {
        super(link);
    }

    @Override
    public List<TableHead> getHeads() throws Exception {
        var meta = link.getMetaData();
        var set = meta.getTables(null, null, "%", new String[] {"TABLE"});
        var result = new ArrayList<TableHead>();
        while (set.next()) {
            result.add(new TableHead(set.getString(1), set.getString(2), set.getString(3)));
        }
        return result;
    }

    @Override
    public void create(Table table) throws Exception {
        this.create(table, false);
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
        for (var i = 0; i < table.fieldList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(this.makeNature(table.fieldList.get(i)));
        }
        builder.append(")");
        this.link.createStatement().execute(builder.toString());
    }

    @Override
    public ResultSet select(Select select) throws Exception {
        return this.select(select, null);
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
                if (join.joinTie != null) {
                    builder.append(" ");
                    builder.append(join.joinTie.toString());
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
                    builder.append(this.makeClauses(join.filterList, dataSource, withAlias));
                }
            }
        }
        if (select.hasFilters()) {
            builder.append(" WHERE ");
            builder.append(this.makeClauses(select.filterList, dataSource, null));
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
        var build = builder.toString();
        System.out.println("SELECT: " + build);
        var prepared = this.link.prepareStatement(build);
        var param_index = 1;
        if (select.hasJoins()) {
            for (var join : select.joinList) {
                if (join.hasFilters()) {
                    for (var clause : join.filterList) {
                        if (clause.valued != null && clause.valued.data != null) {
                            this.setParameter(prepared, param_index, clause.valued);
                            param_index++;
                        }
                    }
                }
            }
        }
        if (select.hasFilters()) {
            for (var clause : select.filterList) {
                if (clause.valued != null && clause.valued.data != null) {
                    this.setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }

        return prepared.executeQuery();
    }

    @Override
    public String insert(Insert insert) throws Exception {
        return this.insert(insert, null);
    }

    @Override
    public String insert(Insert insert, Strain strain) throws Exception {
        var ID = getID(this.link, insert);
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
            if (valued.data != null) {
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
        var build = builder.toString();
        System.out.println("INSERT: " + build);
        var prepared = this.link.prepareStatement(build);
        var param_index = 1;
        for (var valued : insert.valuedList) {
            if (valued.data != null) {
                this.setParameter(prepared, param_index, valued);
                param_index++;
            }
        }
        if (!strained.isEmpty()) {
            for (var toStrain : strained) {
                if (!toStrain.getRight().isEmpty()) {
                    this.setParameter(prepared, param_index, new Valued(toStrain.getLeft(), toStrain.getRight()));
                    param_index++;
                }
            }
        }
        prepared.executeUpdate();
        return ID;
    }

    @Override
    public Integer update(Update update) throws Exception {
        return this.update(update, null);
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
            if (update.valuedList.get(i).data == null) {
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
        builder.append(this.makeClauses(update.filterList, null, null));
        if (update.limit != null) {
            builder.append(" LIMIT ");
            builder.append(update.limit);
        }
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(" AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        var build = builder.toString();
        System.out.println("UPDATE: " + build);
        var prepared = this.link.prepareStatement(build);
        var param_index = 1;
        for (var valued : update.valuedList) {
            if (valued != null) {
                this.setParameter(prepared, param_index, valued);
                param_index++;
            }
        }
        if (update.filterList != null && !update.filterList.isEmpty()) {
            for (var clause : update.filterList) {
                if (clause.valued != null) {
                    this.setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }
        return prepared.executeUpdate();
    }

    @Override
    public Integer delete(Delete delete) throws Exception {
        return this.delete(delete, null);
    }

    @Override
    public Integer delete(Delete delete, Strain strain) throws Exception {
        var builder = new StringBuilder("DELETE FROM ");
        var dataSource = delete.tableHead.getCatalogSchemaName();
        builder.append(dataSource);
        builder.append(" WHERE ");
        builder.append(this.makeClauses(delete.filterList, null, null));
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(" AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        var build = builder.toString();
        System.out.println("DELETE: " + build);
        var prepared = this.link.prepareStatement(build);
        var param_index = 1;
        if (delete.filterList != null && !delete.filterList.isEmpty()) {
            for (var clause : delete.filterList) {
                if (clause.valued.data != null) {
                    this.setParameter(prepared, param_index, clause.valued);
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
                valued.data = next;
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
                                        + insert.toGetID.filter.data.toString() + "'");
        String last = null;
        if (rst.next()) {
            last = rst.getString(1);
        }
        if (last == null || last.isEmpty()) {
            last = WizChars.fillAtStart("", '0', formatSize);
        }
        String next = WizChars.getNext(last, true);
        putID(insert, next);
        return next;
    }

    protected String getIDCX(Connection link, Insert insert, int formatSize)
                    throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT MAX(" + insert.toGetID.name + ") FROM "
                                        + insert.tableHead.name + " WHERE "
                                        + insert.toGetID.filter.name + " = '"
                                        + insert.toGetID.filter.data.toString() + "'");
        String last = null;
        if (rst.next()) {
            last = rst.getString(1);
        }
        if (last == null || last.isEmpty()) {
            last = WizChars.fillAtStart("", '0', formatSize);
        }
        String next = WizChars.getNext(last, false);
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
        next = WizChars.fillAtStart(next, '0', formatSize);
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
        next = WizChars.fillAtStart(next, '0', formatSize);
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
            case BOOL:
                builder.append(" BOOLEAN");
                break;
            case BIT:
                builder.append(" BIT");
                break;
            case TINY:
                builder.append(" TINYINT");
                break;
            case SMALL:
                builder.append(" SMALLINT");
                break;
            case INT:
            case SERIAL:
                builder.append(" INTEGER");
                break;
            case LONG:
            case BIG_SERIAL:
                builder.append(" BIGINT");
                break;
            case FLOAT:
                builder.append(" FLOAT");
                break;
            case REAL:
                builder.append(" REAL");
                break;
            case DOUBLE:
                builder.append(" DOUBLE");
                break;
            case NUMERIC:
            case BIG_NUMERIC:
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
            case CHAR:
                builder.append(" CHAR(1)");
                break;
            case CHARS:
                builder.append(" VARCHAR");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case DATE:
                builder.append(" DATE");
                break;
            case TIME:
                builder.append(" TIME");
                break;
            case DATE_TIME:
            case TIMESTAMP:
                builder.append(" TIMESTAMP");
                break;
            case BYTES:
            case BLOB:
                builder.append(" BLOB");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case TEXT:
                builder.append(" TEXT");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (Objects.equals(field.notNull, true)) {
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
            if (clause.seems == FilterSeems.NOT) {
                builder.append(" NOT ");
            }
            if (clause.valued != null) {
                if (fromSource != null && !fromSource.isEmpty() && !clause.valued.name.contains(".")) {
                    builder.append(fromSource);
                    builder.append(".");
                }
                builder.append(clause.valued.name);
                if (clause.valued.data == null) {
                    builder.append(" IS NULL ");
                } else {
                    builder.append(this.makeCondition(clause.likes, "?"));
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
                    builder.append(this.makeCondition(clause.likes, formWith.toString()));
                }
            }
            nextIsOr = clause.ties == FilterTies.OR;
        }
        return builder.toString();
    }

    protected String makeCondition(FilterLikes likes, String upon) {
        switch (likes) {
            case EQUALS:
                return " = " + upon + " ";
            case BIGGER:
                return " > " + upon + " ";
            case LESSER:
                return " < " + upon + " ";
            case BIGGER_EQUALS:
                return " >= " + upon + " ";
            case LESSER_EQUALS:
                return " <= " + upon + " ";
            case STARTS_WITH:
                return " STARTS WITH " + upon + " ";
            case ENDS_WITH:
                return " ENDS WITH " + upon + " ";
            case CONTAINS:
                return " CONTAINS " + upon + " ";
            default:
                throw new UnsupportedOperationException();
        }
    }

    protected void setParameter(PreparedStatement prepared, int index, Valued valued)
                    throws Exception {
        if (valued.type == null) {
            prepared.setObject(index, valued.data);
        } else {
            switch (valued.type) {
                case BOOL:
                    prepared.setBoolean(index, WizData.getBoolean(valued.data));
                    break;
                case BIT:
                case BYTE:
                    prepared.setByte(index, WizData.getByte(valued.data));
                    break;
                case TINY:
                case SMALL:
                    prepared.setShort(index, WizData.getShort(valued.data));
                    break;
                case INT:
                case SERIAL:
                    prepared.setInt(index, WizData.getInteger(valued.data));
                    break;
                case LONG:
                case BIG_SERIAL:
                    prepared.setLong(index, WizData.getLong(valued.data));
                    break;
                case FLOAT:
                case REAL:
                    prepared.setFloat(index, WizData.getFloat(valued.data));
                    break;
                case DOUBLE:
                case NUMERIC:
                    prepared.setDouble(index, WizData.getDouble(valued.data));
                    break;
                case BIG_NUMERIC:
                    prepared.setBigDecimal(index, WizData.getBigDecimal(valued.data));
                    break;
                case CHAR:
                case CHARS:
                    prepared.setString(index, WizData.getString(valued.data));
                    break;
                case DATE:
                    prepared.setDate(index, WizData.getDate(valued.data));
                    break;
                case TIME:
                    prepared.setTime(index, WizData.getTime(valued.data));
                    break;
                case DATE_TIME:
                case TIMESTAMP:
                    prepared.setTimestamp(index, WizData.getTimestamp(valued.data));
                    break;
                case BYTES:
                    prepared.setBytes(index, WizData.getBytes(valued.data));
                    break;
                case BLOB:
                case TEXT:
                    prepared.setBlob(index, WizData.getBlob(valued.data));
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

}
