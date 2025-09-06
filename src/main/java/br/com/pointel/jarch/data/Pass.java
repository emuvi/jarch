package br.com.pointel.jarch.data;

import java.nio.charset.StandardCharsets;

public class Pass implements Data {
    
    public byte[] data;

    public Pass() {
        this.data = null;
    }

    public Pass(byte[] data) {
        this.data = data;
    }

    public Pass(String pass) {
        this.data = pass.getBytes(StandardCharsets.UTF_8);
    }

    public boolean hasData() {
        return this.data != null && this.data.length > 0;
    }

    public Pass withData(byte[] data) {
        this.data = data;
        return this;
    }

    public Pass withData(String pass) {
        this.data = pass != null ? pass.getBytes(StandardCharsets.UTF_8) : null;
        return this;
    }

    public Pass withNoData() {
        this.data = null;
        return this;
    }

    public Pass uponData(byte[] data) {
        Pass clone = this.clone();
        clone.data = data;
        return clone;
    }

    public Pass uponData(String pass) {
        Pass clone = this.clone();
        clone.data = pass != null ? pass.getBytes(StandardCharsets.UTF_8) : null;
        return clone;
    }

    public Pass uponNoData() {
        Pass clone = this.clone();
        clone.data = null;
        return clone;
    }

    @Override
    public Pass clone() {
        return (Pass) this.deepClone();
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
        return Base.fromChars(chars, Pass.class);
    }

    public String getPass() {
        return this.data != null ? new String(this.data, StandardCharsets.UTF_8) : null;
    }
    
}
