package br.com.pointel.jarch.mage;

import java.util.ArrayList;
import java.util.List;

public class WizList {

    private WizList() {}

    public static <T> List<T> insert(int index, T value) {
        return insert(index, value, null, null);
    }

    public static <T> List<T> insert(int index, T value, List<T> onList) {
        return insert(index, value, onList, null);
    }
    
    public static <T> List<T> insert(int index, T value, List<T> onList, T withDefault) {
        if (onList == null) {
            onList = new ArrayList<>();
        }
        while (onList.size() <= index) {
            onList.add(withDefault);
        }
        onList.set(index, value);
        return onList;
    }
    
}
