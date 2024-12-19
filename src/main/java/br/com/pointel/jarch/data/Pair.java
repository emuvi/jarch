package br.com.pointel.jarch.data;

// [ EVAL ] This class very like be replaced by specific classes when refactor the Helper class
public class Pair<T, S> {

    public T tableHead;
    public S tail;

    public Pair() {}

    public Pair(T tableHead) {
        this.tableHead = tableHead;
    }

    public Pair(T tableHead, S tail) {
        this.tableHead = tableHead;
        this.tail = tail;
    }

}
