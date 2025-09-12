package br.com.pointel.jarch.data;

import java.util.List;

public class Table implements Data {

    public TableHead tableHead;
    public List<Field> fieldList;
    public List<KeyPrimary> keyPrimaryList;
    public List<KeyForeign> keyForeignList;

    public Table() {
    }

    public Table(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Table(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Table(TableHead tableHead, List<Field> fieldList, List<KeyPrimary> keyPrimaryList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyPrimaryList = keyPrimaryList;
    }

    public Table(TableHead tableHead, List<Field> fieldList, List<KeyPrimary> keyPrimaryList, List<KeyForeign> keyForeignList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyPrimaryList = keyPrimaryList;
        this.keyForeignList = keyForeignList;
    }

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasFieldList() {
        return this.fieldList != null && !this.fieldList.isEmpty();
    }

    public boolean hasKeyPrimaryList() {
        return this.keyPrimaryList != null && !this.keyPrimaryList.isEmpty();
    }

    public boolean hasKeyForeignList() {
        return this.keyForeignList != null && !this.keyForeignList.isEmpty();
    }

    public Table withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Table withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Table withFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    public Table withFieldList(Field... fieldArgs) {
        this.fieldList = List.of(fieldArgs);
        return this;
    }

    public Table withNoFieldList() {
        this.fieldList = null;
        return this;
    }

    public Table withKeyPrimaryList(List<KeyPrimary> keyPrimaryList) {
        this.keyPrimaryList = keyPrimaryList;
        return this;
    }

    public Table withKeyPrimaryList(KeyPrimary... keyPrimaryArgs) {
        this.keyPrimaryList = List.of(keyPrimaryArgs);
        return this;
    }

    public Table withNoKeyPrimaryList() {
        this.keyPrimaryList = null;
        return this;
    }

    public Table withKeyForeignList(List<KeyForeign> keyForeignList) {
        this.keyForeignList = keyForeignList;
        return this;
    }

    public Table withKeyForeignList(KeyForeign... keyForeignArgs) {
        this.keyForeignList = List.of(keyForeignArgs);
        return this;
    }

    public Table withNoKeyForeignList() {
        this.keyForeignList = null;
        return this;
    }

    public Table uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Table uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Table uponFieldList(List<Field> fieldList) {
        var clone = this.clone();
        clone.fieldList = fieldList;
        return clone;
    }

    public Table uponFieldList(Field... fieldArgs) {
        var clone = this.clone();
        clone.fieldList = List.of(fieldArgs);
        return clone;
    }

    public Table uponNoFieldList() {
        var clone = this.clone();
        clone.fieldList = null;
        return clone;
    }

    public Table uponKeyPrimaryList(List<KeyPrimary> keyPrimaryList) {
        var clone = this.clone();
        clone.keyPrimaryList = keyPrimaryList;
        return clone;
    }

    public Table uponKeyPrimaryList(KeyPrimary... keyPrimaryArgs) {
        var clone = this.clone();
        clone.keyPrimaryList = List.of(keyPrimaryArgs);
        return clone;
    }

    public Table uponNoKeyPrimaryList() {
        var clone = this.clone();
        clone.keyPrimaryList = null;
        return clone;
    }

    public Table uponKeyForeignList(List<KeyForeign> keyForeignList) {
        var clone = this.clone();
        clone.keyForeignList = keyForeignList;
        return clone;
    }

    public Table uponKeyForeignList(KeyForeign... keyForeignArgs) {
        var clone = this.clone();
        clone.keyForeignList = List.of(keyForeignArgs);
        return clone;
    }

    public Table uponNoKeyForeignList() {
        var clone = this.clone();
        clone.keyForeignList = null;
        return clone;
    }

    @Override
    public Table clone() {
        return (Table) this.deepClone();
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

    public static Table fromChars(String chars) {
        return Base.fromChars(chars, Table.class);
    }

    public String getReferenceName() {
        return this.tableHead.getReferenceName();
    }

    public String getSchemaName() {
        return this.tableHead.getSchemaName();
    }

    public String getCatalogSchemaName() {
        return this.tableHead.getCatalogSchemaName();
    }

}
