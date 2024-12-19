package br.com.pointel.jarch.flow;

public class Pair<T, S> implements FixVals {

    public T head;
    public S tail;

    public Pair() {}

    public Pair(T head, S tail) {
        this.head = head;
        this.tail = tail;
    }

}
