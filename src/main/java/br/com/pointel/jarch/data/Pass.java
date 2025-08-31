package br.com.pointel.jarch.data;

public class Pass implements Data {
    
    public byte[] data;

    public Pass() {
        this.data = null;
    }

    public Pass(byte[] data) {
        this.data = data;
    }

    public Pass(String pass) {
        this.data = pass.getBytes();
    }

    public String getPass() {
        return this.data != null ? new String(this.data) : null;
    }

    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Pass fromChars(String chars) {
        return Data.fromChars(chars, Pass.class);
    }
    
}
