package br.com.pointel.jarch.data;

import java.sql.Connection;

public class EOrmPostgre extends EOrmBase {

    public EOrmPostgre(Connection link) {
        super(link);
    }

    @Override
    public String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case BOOL: builder.append(" BOOLEAN"); break;
            case BIT: builder.append(" BIT"); break;
            case BYTE, TINY: builder.append(" SMALLINT"); break;
            case SMALL: builder.append(" SMALLINT"); break;
            case INT: builder.append(" INTEGER"); break;
            case SERIAL: builder.append(" SERIAL"); break;
            case LONG: builder.append(" BIGINT"); break;
            case BIG_SERIAL: builder.append(" BIGSERIAL"); break;
            case FLOAT, REAL: builder.append(" REAL"); break;
            case DOUBLE: builder.append(" DOUBLE PRECISION"); break;
            case NUMERIC, BIG_NUMERIC: 
                builder.append(" NUMERIC");
                if (field.size != null) {
                    builder.append("(").append(field.size);
                    if (field.precision != null) {
                    builder.append(",").append(field.precision);
                    }
                    builder.append(")");
                }
                break;
            case CHAR: builder.append(" CHAR(1)"); break;
            case CHARS: 
                builder.append(" VARCHAR");
                if (field.size != null) {
                    builder.append("(").append(field.size).append(")");
                }
                break;
            case DATE: builder.append(" DATE"); break;
            case TIME: builder.append(" TIME"); break;
            case DATE_TIME, TIMESTAMP: builder.append(" TIMESTAMP"); break;
            case BYTES, BLOB: builder.append(" BYTEA"); break;
            case TEXT: builder.append(" TEXT"); break;
            case OBJECT: builder.append(" JSONB"); break;
            default: throw new UnsupportedOperationException();
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
