package br.com.pointel.jarch.data;

public class PairEnd<K, V> implements Data {
    
    private final K key;
    private final V val;

    public PairEnd(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public V getVal() {
        return val;
    }

    public K key() {
        return key;
    }

    public V val() {
        return val;
    }

    public boolean hasKey() {
        return this.key != null;
    }

    public boolean hasVal() {
        return this.val != null;
    }

    public PairEnd<K, V> uponKey(K key) {
        return of(key, val);
    }

    public PairEnd<K, V> uponNoKey() {
        return of(null, val);
    }

    public PairEnd<K, V> uponVal(V val) {
        return of(key, val);
    }

    public PairEnd<K, V> uponNoVal() {
        return of(key, null);
    }

    @Override
    public PairEnd<K, V> clone() {
        return (PairEnd<K, V>) this.deepClone();
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

    public static PairEnd fromChars(String chars) {
        return Base.fromChars(chars, PairEnd.class);
    }

    public static <K, V> PairEnd<K, V> of(K key, V val) {
        return new PairEnd<>(key, val);
    }

}
