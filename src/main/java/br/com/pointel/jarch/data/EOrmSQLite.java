package br.com.pointel.jarch.data;

import java.sql.Connection;

public class EOrmSQLite extends EOrmBase {

    public EOrmSQLite(Connection link) {
        super(link);
    }

    protected String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case Bool, Bit, Byte, Tiny, Small, Int, Serial, Long, BigSerial:
                builder.append(" INTEGER");
                break;
            case Float, Real, Double:
                builder.append(" REAL");
                break;    
            case Numeric, BigNumeric:
                builder.append(" NUMERIC");
                break;
            case Char, Chars, Date, Time, DateTime, Timestamp, Text:
                builder.append(" TEXT");
                break;
            case Bytes, Blob, Object:
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
    public String makeCondition(FilterLikes likes, String with) {
        switch (likes) {
            case Equals: return " = " + with + " ";
            case Bigger: return " > " + with + " ";
            case Lesser: return " < " + with + " ";
            case BiggerOrEquals: return " >= " + with + " ";
            case LesserOrEquals: return " <= " + with + " ";
            case StartsWith: return " LIKE " + with + " || '%' ";
            case EndsWith: return " LIKE '%' || " + with + " ";
            case Contains: return " LIKE '%' || " + with + " || '%' ";
            default: throw new UnsupportedOperationException();
        }
    }

}
