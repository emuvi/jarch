package br.com.pointel.jarch.data;

import br.com.pointel.jarch.flow.FixVals;
import java.io.Serializable;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pass)) {
            return false;
        }
        Pass pass = (Pass) o;
        return Objects.deepEquals(data, pass.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Pass fromChars(String chars) {
        return Data.fromChars(chars, Pass.class);
    }
    
}
