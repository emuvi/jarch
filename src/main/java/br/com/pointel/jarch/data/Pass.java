package br.com.pointel.jarch.data;

import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;
import java.util.Objects;

public class Pass implements FixVals {
    
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
        return Objects.equals(data, pass.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Pass fromString(String json) {
        return new Gson().fromJson(json, Pass.class);
    }
    
}
