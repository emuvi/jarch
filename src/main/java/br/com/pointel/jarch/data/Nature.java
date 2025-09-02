package br.com.pointel.jarch.data;

public enum Nature {

    Bool(Boolean.class, Number.class),
    
    Bit(Byte.class, Number.class, Boolean.class),

    Byte(Byte.class, Number.class),

    Tiny(Byte.class, Short.class, Number.class),

    Small(Short.class, Integer.class, Number.class),

    Int(Integer.class, Number.class),

    Long(Long.class, Number.class),

    Serial(Integer.class, Long.class, Number.class),

    BigSerial(Long.class, java.math.BigInteger.class, Number.class),

    Float(Float.class, Number.class),

    Real(Float.class, Double.class, Number.class),

    Double(Double.class, Number.class),

    Numeric(java.math.BigDecimal.class, Double.class, Number.class),

    BigNumeric(java.math.BigDecimal.class, java.math.BigInteger.class, Number.class),

    Char(Character.class, String.class, Number.class),

    Chars(String.class, java.sql.Blob.class, Byte[].class),

    Date(java.time.LocalDate.class, java.sql.Date.class, java.util.Date.class),

    Time(java.time.LocalTime.class, java.util.Date.class, java.sql.Time.class),

    DateTime(java.time.Instant.class, java.sql.Timestamp.class, java.util.Date.class),

    Timestamp(java.time.Instant.class, java.sql.Timestamp.class, java.util.Date.class),

    Bytes(Byte[].class, String.class, java.sql.Blob.class),

    Blob(java.sql.Blob.class, Byte[].class, String.class),

    Text(String.class, java.sql.Blob.class, Byte[].class),

    Object(String.class, java.sql.Blob.class, Byte[].class, Object.class);

    private final Class<?>[] types;

    Nature(Class<?>... types) {
        this.types = types;
    }

    public Class<?>[] getTypes() {
        return types;
    }

}
