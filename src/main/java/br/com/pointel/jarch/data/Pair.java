package br.com.pointel.jarch.data;

// [ EVAL ] This class very like be replaced by specific classes when refactor the Helper class
public class Pair<T, S> {

    public T head;
    public S tail;

    public Pair() {}

    public Pair(T head) {
        this.head = head;
    }

    public Pair(T head, S tail) {
        this.head = head;
        this.tail = tail;
    }

}
