package br.com.pointel.jarch.data;

public class Pair<K, V> implements Data {
    
    private K key;
    private V val;

    public Pair() {
    }

    public Pair(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    public K key() {
        return key;
    }

    public Pair<K, V> key(K key) {
        this.key = key;
        return this;
    }

    public V val() {
        return val;
    }

    public Pair<K, V> val(V val) {
        this.val = val;
        return this;
    }

    public boolean hasKey() {
        return this.key != null;
    }

    public boolean hasVal() {
        return this.val != null;
    }

    public Pair<K, V> uponKey(K key) {
        var clone = this.clone();
        clone.key = key;
        return clone;
    }

    public Pair<K, V> uponNoKey() {
        var clone = this.clone();
        clone.key = null;
        return clone;
    }

    public Pair<K, V> uponVal(V val) {
        var clone = this.clone();
        clone.val = val;
        return clone;
    }

    public Pair<K, V> uponNoVal() {
        var clone = this.clone();
        clone.val = null;
        return clone;
    }

    @Override
    public Pair<K, V> clone() {
        return (Pair<K, V>) this.deepClone();
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
        return Base.fromChars(chars, Pair.class);
    }

    public static <K, V> Pair<K, V> of(K key, V val) {
        return new Pair<>(key, val);
    }

}
