package br.com.pointel.jarch.data;

public class Pair<K, V> implements Data {
    
    public K key;
    public V val;

    public Pair() {
    }

    public Pair(K key, V val) {
        this.key = key;
        this.val = val;
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

    public static Pair fromChars(String chars) {
        return Data.fromChars(chars, Pair.class);
    }

}
