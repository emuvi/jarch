package br.com.pointel.jarch.data;

import java.sql.Connection;

public class EOrmSQLite extends EOrmBase {

    public EOrmSQLite(Connection link) {
        super(link);
    }

    protected String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case BOOL, BIT, BYTE, TINY, SMALL, INT, SERIAL, LONG, BIG_SERIAL:
                builder.append(" INTEGER");
                break;
            case FLOAT, REAL, DOUBLE:
                builder.append(" REAL");
                break;    
            case NUMERIC, BIG_NUMERIC:
                builder.append(" NUMERIC");
                break;
            case CHAR, CHARS, DATE, TIME, DATE_TIME, TIMESTAMP, TEXT:
                builder.append(" TEXT");
                break;
            case BYTES, BLOB, OBJECT:
                builder.append(" BLOB");
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (Boolean.TRUE.equals(field.notNull)) {
            builder.append(" NOT NULL");
        }
        return builder.toString();
    }
    
    @Override
    public String makeCondition(FilterLikes condition, String with) {
        switch (condition) {
            case EQUALS: return " = " + with + " ";
            case BIGGER: return " > " + with + " ";
            case LESSER: return " < " + with + " ";
            case BIGGER_EQUALS: return " >= " + with + " ";
            case LESSER_EQUALS: return " <= " + with + " ";
            case STARTS_WITH: return " LIKE " + with + " || '%' ";
            case ENDS_WITH: return " LIKE '%' || " + with + " ";
            case CONTAINS: return " LIKE '%' || " + with + " || '%' ";
            default: throw new UnsupportedOperationException();
        }
    }

}
